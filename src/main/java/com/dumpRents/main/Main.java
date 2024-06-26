package com.dumpRents.main;

import com.dumpRents.model.entities.*;
import com.dumpRents.model.entities.valueObjects.*;
import com.dumpRents.model.useCases.client.FindClientUseCase;
import com.dumpRents.model.useCases.client.InsertClientUseCase;
import com.dumpRents.model.useCases.client.UpdateClientUseCase;
import com.dumpRents.model.useCases.report.EntryExitReportUseCase;
import com.dumpRents.model.useCases.report.ExportCSVUseCase;
import com.dumpRents.model.useCases.rental.*;
import com.dumpRents.model.useCases.report.IncomeReportUseCase;
import com.dumpRents.model.useCases.rubbleDumpster.*;
import com.dumpRents.persistence.dao.ClientDAO;
import com.dumpRents.persistence.dao.RentalDAO;
import com.dumpRents.persistence.dao.RubbleDumpsterDAO;
import com.dumpRents.persistence.utils.DatabaseBuilder;
import com.dumpRents.repository.InMemoryClientDAO;
import com.dumpRents.repository.InMemoryRentalDAO;
import com.dumpRents.repository.InMemoryRubbleDumpsterDAO;
import com.dumpRents.view.WindowLoader;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.dumpRents.model.entities.RubbleDumpsterStatus.*;


public class Main {

    public static ActivateRubbleDumpsterUseCase activateRubbleDumpsterUseCase;
    public static FindRubbleDumpsterUseCase findRubbleDumpsterUseCase;
    public static InactivateRubbleDumpsterUseCase inactivateRubbleDumpsterUseCase;
    public static InsertRubbleDumpsterUseCase insertRubbleDumpsterUseCase;
    public static UpdateRubbleDumpsterRentalPriceUseCase updateRubbleDumpsterRentalPriceUseCase;

    public static FindClientUseCase findClientUseCase;
    public static InsertClientUseCase insertClientUseCase;
    public static UpdateClientUseCase updateClientUseCase;

    public static InsertRentalUseCase insertRentalUseCase;
    public static EndRentalUseCase endRentalUseCase;
    public static FindRentalUseCase findRentalUseCase;
    public static WithdrawalRequestUseCase withdrawalRequestUseCase;

    public static ExportCSVUseCase exportCSVUseCase;
    public static IncomeReportUseCase incomeReportUseCase;
    public static EntryExitReportUseCase entryExitReportUseCase;

    public static void main(String[] args) {
        configureInjection();
        populateFakeDatabase();
        WindowLoader.main(args);
    }

    private static void populateFakeDatabase() {
        Cep cep = new Cep("12345-673");

        Cpf cpf1 = new Cpf("960.647.320-16");
        Cpf cpf2 = new Cpf("605.647.070-90");
        Cpf cpf3 = new Cpf("960.647.320-16");

        Phone phone1 = new Phone("16994580485");
        Phone phone2 = new Phone("16994580485");
        Phone phone3 = new Phone("16994580485");
        Phone phone4 = new Phone("16994580485");

        List<Email> emails = new ArrayList<>();
        Email email1 = new Email("neguito.juvenal@gmail.com");
        emails.add(email1);

        Address address = new Address("Rua Exemplo", "Bairro Exemplo", "123", "Cidade Exemplo", cep);
        Client client1 = new Client("Caboquinho",address, cpf1, phone1, phone2, emails,1);
        Client client2 = new Client("Caboco",address, cpf2, phone3, phone4, emails,2);

        RubbleDumpster rubbleDumpster1 = new RubbleDumpster(1,50.0, 300.0, AVAILABLE);
        RubbleDumpster rubbleDumpster2 = new RubbleDumpster(2,60.0, 200.0, AVAILABLE);
        RubbleDumpster rubbleDumpster3 = new RubbleDumpster(3,70.0, 300.0, AVAILABLE);


        insertRubbleDumpsterUseCase.insert(rubbleDumpster1);
        insertRubbleDumpsterUseCase.insert(rubbleDumpster2);
        insertRubbleDumpsterUseCase.insert(rubbleDumpster3);

        insertClientUseCase.insert(client1);
        insertClientUseCase.insert(client2);

        insertRentalUseCase.insertRental(client1.getId(),address);
        insertRentalUseCase.insertRental(client2.getId(),address);
        insertRentalUseCase.insertRental(client2.getId(),address);
    }

    private static void setupDatabase() {
        DatabaseBuilder  dbBuilder = new DatabaseBuilder();
//        dbBuilder.buildDatabaseIfMissing();
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