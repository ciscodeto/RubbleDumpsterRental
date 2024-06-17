package com.main;


import com.model.entities.Client;
import com.model.entities.Rental;
import com.model.entities.RubbleDumpster;
import com.model.entities.valueObjects.*;
import com.model.useCases.rubbleDumpster.*;
import com.persistence.dao.RubbleDumpsterDAO;
import com.repository.inMemoryRubbleDumpsterDAO;

import javax.crypto.Cipher;
import javax.sound.midi.Soundbank;
import javax.xml.xpath.XPathFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.model.entities.RubbleDumpsterStatus.*;


public class Main {

    private static ActivateRubbleDumpsterUseCase activateRubbleDumpsterUseCase;
    private static FindRubbleDumpsterUseCase findRubbleDumpsterUseCase;
    private static InactivateRubbleDumpsterUseCase inactivateRubbleDumpsterUseCase;
    private static InsertRubbleDumpsterUseCase insertRubbleDumpsterUseCase;
    private static UpdateRubbleDumpsterRentalPriceUseCase updateRubbleDumpsterRentalPriceUseCase;

    public static void main(String[] args) {
        Cep cep = new Cep("12345-678");
        Address address = new Address("Rua Exemplo", "Bairro Exemplo", "123", "Cidade Exemplo", cep);

        if (address.isValid()) {
            System.out.println("O endereço é válido.");
        } else {
            System.out.println("O endereço é inválido.");
        }
        Cpf cpf = new Cpf("440.040.708-05");
        Phone phone1 = new Phone("4444-4444");
        Phone phone2 = new Phone("5555-5555");
        Phone phone3 = new Phone("6666-6666");
        Phone phone4 = new Phone("7777-7777");

        List<Email> emails = new ArrayList<>();
        Email email = new Email("neguito.juvenal@gmail.com");
        emails.add(email);
        Client client = new Client("Kayky",address,cpf, phone2, phone1, emails,1 );
        Client client2 = new Client("Pedro",address,cpf, phone3, phone4, emails,2 );

        RubbleDumpster rubbleDumpster= new RubbleDumpster(1,50.0, 300.0, DISABLED);
        RubbleDumpster rubbleDumpster1= new RubbleDumpster(2,60.0, 200.0, DISABLED);
        Rental rental = new Rental(rubbleDumpster,client, LocalDate.now());

        rubbleDumpster.setRental(rental);
        rubbleDumpster1.setRental(rental);
        rental.setFinalAmount(200.0);

        configureInjection();

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
        rental.calculateFinalAmount();
        rubbleDumpster.activateRubbleDumpster();
        System.out.println(rubbleDumpster);

        rubbleDumpster.withdrawalRequest(120);

    }

    private static void configureInjection() {
        RubbleDumpsterDAO rubbleDumpsterDAO = new inMemoryRubbleDumpsterDAO();
        activateRubbleDumpsterUseCase = new ActivateRubbleDumpsterUseCase(rubbleDumpsterDAO);
        findRubbleDumpsterUseCase = new FindRubbleDumpsterUseCase(rubbleDumpsterDAO);
        inactivateRubbleDumpsterUseCase = new InactivateRubbleDumpsterUseCase(rubbleDumpsterDAO);
        insertRubbleDumpsterUseCase = new InsertRubbleDumpsterUseCase(rubbleDumpsterDAO);
        updateRubbleDumpsterRentalPriceUseCase = new UpdateRubbleDumpsterRentalPriceUseCase(rubbleDumpsterDAO);



    }
}