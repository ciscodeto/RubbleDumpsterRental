package com.dumpRents.main;

import com.dumpRents.model.entities.Client;
import com.dumpRents.model.entities.Rental;
import com.dumpRents.model.entities.Report;
import com.dumpRents.model.entities.RubbleDumpster;
import com.dumpRents.model.entities.valueObjects.*;
import com.dumpRents.model.useCases.client.FindClientUseCase;
import com.dumpRents.model.useCases.client.InsertClientUseCase;
import com.dumpRents.model.useCases.client.UpdateClientUseCase;
import com.dumpRents.model.useCases.report.EntryExitReportUseCase;
import com.dumpRents.model.useCases.report.ExportCSVUseCase;
import com.dumpRents.model.useCases.rental.*;
import com.dumpRents.model.useCases.rubbleDumpster.*;
import com.dumpRents.persistence.dao.ClientDAO;
import com.dumpRents.persistence.dao.RentalDAO;
import com.dumpRents.persistence.dao.RubbleDumpsterDAO;
import com.dumpRents.repository.InMemoryClientDAO;
import com.dumpRents.repository.InMemoryRentalDAO;
import com.dumpRents.repository.InMemoryRubbleDumpsterDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Main {

    private static ActivateRubbleDumpsterUseCase activateRubbleDumpsterUseCase;
    private static FindRubbleDumpsterUseCase findRubbleDumpsterUseCase;
    private static InactivateRubbleDumpsterUseCase inactivateRubbleDumpsterUseCase;
    private static InsertRubbleDumpsterUseCase insertRubbleDumpsterUseCase;
    private static UpdateRubbleDumpsterRentalPriceUseCase updateRubbleDumpsterRentalPriceUseCase;

    private static FindClientUseCase findClientUseCase;
    private static InsertClientUseCase insertClientUseCase;
    private static UpdateClientUseCase updateClientUseCase;

    private static InsertRentalUseCase insertRentalUseCase;
    private static EndRentalUseCase endRentalUseCase;
    private static FindRentalUseCase findRentalUseCase;
    private static WithdrawalRequestUseCase withdrawalRequestUseCase;

    private static ExportCSVUseCase exportCSVUseCase;
    private static IncomeReportUseCase incomeReportUseCase;
    private static EntryExitReportUseCase entryExitReportUseCase;

    public static void main(String[] args) {
        // Validação de CEP
        Cep cep = new Cep("12345-678");
        Address address = new Address("Rua Exemplo", "Bairro Exemplo", "123", "Cidade Exemplo", cep);

        if (address.isValid()) {
            System.out.println("O endereço é válido.");
        } else {
            System.out.println("O endereço é inválido.");
        }



        //Startando CLIENT
        Cpf cpf = new Cpf("39501888860");
        Phone phone1 = new Phone("16994580485");
        Phone phone2 = new Phone("16994580485");
        Phone phone3 = new Phone("16994580485");
        Phone phone4 = new Phone("16994580485");
        List<Email> emails = new ArrayList<>();
        Email email = new Email("neguito.juvenal@gmail.com");
        emails.add(email);
        Client client = new Client("Kayky", address, cpf, phone2, phone1, emails, 1);
        Client client2 = new Client("Pedro", address, cpf, phone3, phone4, emails, 2);

        RubbleDumpster rubbleDumpster = new RubbleDumpster(1, 50.0, 300.0, RubbleDumpsterStatus.DISABLED);
        RubbleDumpster rubbleDumpster1 = new RubbleDumpster(2, 60.0, 200.0, RubbleDumpsterStatus.DISABLED);
        RubbleDumpster rubbleDumpster4 = new RubbleDumpster(3, 70.0, 300.0, RubbleDumpsterStatus.AVAILABLE);
        Rental rental = new Rental(rubbleDumpster, client, LocalDate.now());

        // DATABASE

        configureInjection();
        setupDatabase();
        //WindowLoader.main(args);

        activateRubbleDumpsterUseCase.activate(rubbleDumpster);
        //activateRubbleDumpsterUseCase.activate(rubbleDumpster1);
        System.out.println("Activates\n");
        System.out.println(rubbleDumpster);

        System.out.println("INSERTS\n");
        insertRubbleDumpsterUseCase.insert(rubbleDumpster);
        insertRubbleDumpsterUseCase.insert(rubbleDumpster1);

        System.out.println("FINDS\n");
        Optional<RubbleDumpster> rubbleDumpster2 = findRubbleDumpsterUseCase.findOne(1);
        Optional<RubbleDumpster> rubbleDumpster3 = findRubbleDumpsterUseCase.findOne(2);


        System.out.println(rubbleDumpster2);
        System.out.println(rubbleDumpster3);

        System.out.println("Disabled\n");


        //inactivateRubbleDumpsterUseCase.inactivate(rubbleDumpster);
        inactivateRubbleDumpsterUseCase.inactivate(rubbleDumpster1);

        updateRubbleDumpsterRentalPriceUseCase.update(rubbleDumpster, 240.0);
        updateRubbleDumpsterRentalPriceUseCase.update(rubbleDumpster1, 280.0);
        System.out.println(rubbleDumpster);
        System.out.println(rubbleDumpster1);

        //rubbleDumpster.activateRubbleDumpster();
        System.out.println(rubbleDumpster);

        rubbleDumpster.withdrawalRequest(120);


        System.out.println(client.toString());
        System.out.println(client2.toString());

        //TESTE CLIENT
        insertClientUseCase.insert(client);

        System.out.println(findClientUseCase.findOne(1).toString());

        client.setName("Caboquinho");
        updateClientUseCase.updateClient(client);
        System.out.println(client.toString());
        rubbleDumpster.setStatus(RubbleDumpsterStatus.DISABLED);

        //TESTE RENTAL
        activateRubbleDumpsterUseCase.activate(rubbleDumpster);
        Rental rental1 = insertRentalUseCase.insertRental(client.getId());
        System.out.println(findRentalUseCase.findRentalByClient(client).toString());
        System.out.println(findRentalUseCase.findOne(rental1.getId()).toString());
        withdrawalRequestUseCase.requestWithdrawal(rental1.getId(), LocalDate.now());
        System.out.println(findRentalUseCase.findOne(rental1.getId()).toString());
        endRentalUseCase.endRental(rental1.getId());
        System.out.println(findRentalUseCase.findOne(rental1.getId()).toString());

        EntryExitReportUseCase.EntryExitReport entryExitReport = entryExitReportUseCase.generateReport(LocalDate.MIN, LocalDate.MAX);
        for (Report report : entryExitReport.reports()) {
            System.out.println(report);
        }

        IncomeReportUseCase.IncomeReport incomeReport = incomeReportUseCase.generateReport(LocalDate.MIN, LocalDate.MAX);
        for (Report report : incomeReport.reports()) {
            System.out.println(report);
        }

        String entryExitCsvFileName = "entry_exit_report.csv";
        String[] entryExitHeaders = {"Serial Number", "Client Name", "Initial Date", "Withdrawal Date", "Final Amount"};
        List<String[]> entryExitData = entryExitReport.reports().stream()
                .map(report -> new String[]{
                        report.serialNumber(),
                        report.clientName() != null ? report.clientName() : "",
                        report.initialDate() != null ? report.initialDate().toString() : "",
                        report.withdrawalDate() != null ? report.withdrawalDate().toString() : "",
                        report.finalAmount() != null ? report.finalAmount().toString() : ""
                })
                .toList();
        exportCSVUseCase.export(entryExitCsvFileName, entryExitHeaders, entryExitData);

        String incomeCsvFileName = "income_report.csv";
        String[] incomeHeaders = {"Serial Number", "Client Name", "Initial Date", "Withdrawal Date", "Final Amount"};
        List<String[]> incomeData = incomeReport.reports().stream()
                .map(report -> new String[]{
                        report.serialNumber(),
                        report.initialDate() != null ? report.initialDate().toString() : "",
                        report.withdrawalDate() != null ? report.withdrawalDate().toString() : "",
                        report.finalAmount() != null ? report.finalAmount().toString() : ""
                })
                .toList();
        exportCSVUseCase.export(incomeCsvFileName, incomeHeaders, incomeData);
    }

    private static void setupDatabase() {
        DatabaseBuilder  dbBuilder = new DatabaseBuilder();
        dbBuilder.buildDatabaseIfMissing();
    }

    private static void configureInjection() {

        RubbleDumpsterDAO rubbleDumpsterDAO =    new InMemoryRubbleDumpsterDAO();
        activateRubbleDumpsterUseCase =          new ActivateRubbleDumpsterUseCase(rubbleDumpsterDAO);
        findRubbleDumpsterUseCase =              new FindRubbleDumpsterUseCase(rubbleDumpsterDAO);
        inactivateRubbleDumpsterUseCase =        new InactivateRubbleDumpsterUseCase(rubbleDumpsterDAO);
        insertRubbleDumpsterUseCase =            new InsertRubbleDumpsterUseCase(rubbleDumpsterDAO);
        updateRubbleDumpsterRentalPriceUseCase = new UpdateRubbleDumpsterRentalPriceUseCase(rubbleDumpsterDAO);

        ClientDAO clientDAO =   new InMemoryClientDAO();
        insertClientUseCase =   new InsertClientUseCase(clientDAO);
        findClientUseCase =     new FindClientUseCase(clientDAO);
        updateClientUseCase =   new UpdateClientUseCase(clientDAO);

        RentalDAO rentalDAO = (RentalDAO) new InMemoryRentalDAO();
        insertRentalUseCase = new InsertRentalUseCase(rentalDAO,findRubbleDumpsterUseCase,findClientUseCase,rubbleDumpsterDAO);
        findRentalUseCase =   new FindRentalUseCase(rentalDAO);
        endRentalUseCase =    new EndRentalUseCase(rentalDAO,rubbleDumpsterDAO,findRubbleDumpsterUseCase);
        withdrawalRequestUseCase = new WithdrawalRequestUseCase(rentalDAO,rubbleDumpsterDAO,findRentalUseCase);

        entryExitReportUseCase = new EntryExitReportUseCase(rentalDAO);
        incomeReportUseCase =    new IncomeReportUseCase(rentalDAO);
        exportCSVUseCase =       new ExportCSVUseCase();
    }
}