package main;


import model.entities.Client;
import model.entities.Rental;
import model.entities.RubbleDumpster;
import model.entities.valueObjects.*;
import model.useCases.client.*;
import model.useCases.export.ExportCSVUseCase;
import model.useCases.rental.*;
import model.useCases.rubbleDumpster.*;
import persistence.dao.ClientDAO;
import persistence.dao.RentalDAO;
import persistence.dao.RubbleDumpsterDAO;
import repository.InMemoryClientDAO;
import repository.InMemoryRentalDAO;
import repository.InMemoryRubbleDumpsterDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static model.entities.RubbleDumpsterStatus.*;


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
        Client client = new Client("Kayky",address,cpf, phone2, phone1, emails,1);
        Client client2 = new Client("Pedro",address,cpf, phone3, phone4, emails,2 );


        //Startando RUBLLEDUMPSTER
        RubbleDumpster rubbleDumpster= new RubbleDumpster(1,50.0, 300.0, DISABLED);
        RubbleDumpster rubbleDumpster1= new RubbleDumpster(2,60.0, 200.0, DISABLED);
        Rental rental = new Rental(rubbleDumpster,client, LocalDate.now());

        rubbleDumpster.setRental(rental);
        rubbleDumpster1.setRental(rental);
        rental.setFinalAmount(200.0);


        configureInjection();


        // TESTE RUBBLEDUMPSTER
        activateRubbleDumpsterUseCase.activate(rubbleDumpster);
        activateRubbleDumpsterUseCase.activate(rubbleDumpster1);

        insertRubbleDumpsterUseCase.insert(rubbleDumpster);
        insertRubbleDumpsterUseCase.insert(rubbleDumpster1);

        findRubbleDumpsterUseCase.findOne(1);
        findRubbleDumpsterUseCase.findOne(2);

        inactivateRubbleDumpsterUseCase.inactivate(rubbleDumpster);
        inactivateRubbleDumpsterUseCase.inactivate(rubbleDumpster1);

        updateRubbleDumpsterRentalPriceUseCase.update(rubbleDumpster,240.0);
        updateRubbleDumpsterRentalPriceUseCase.update(rubbleDumpster1, 280.0);

        System.out.println(rubbleDumpster);
        System.out.println(rubbleDumpster1);

        rubbleDumpster.setRental(rental);
        rubbleDumpster.activateRubbleDumpster();

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

        /

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

        RentalDAO rentalDAO = new InMemoryRentalDAO();
        insertRentalUseCase = new InsertRentalUseCase(rentalDAO,findRubbleDumpsterUseCase,findClientUseCase);
        findRentalUseCase =   new FindRentalUseCase(rentalDAO);
        endRentalUseCase =    new EndRentalUseCase(rentalDAO,rubbleDumpsterDAO,findRubbleDumpsterUseCase);

        entryExitReportUseCase = new EntryExitReportUseCase(rentalDAO);
        incomeReportUseCase =    new IncomeReportUseCase(rentalDAO);
        exportCSVUseCase =       new ExportCSVUseCase();
    }
}