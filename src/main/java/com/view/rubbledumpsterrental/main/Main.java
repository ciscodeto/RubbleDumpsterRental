package com.view.rubbledumpsterrental.main;


import com.view.rubbledumpsterrental.model.entities.RubbleDumpster;
import com.view.rubbledumpsterrental.model.entities.valueObjects.Address;
import com.view.rubbledumpsterrental.model.entities.valueObjects.Cep;

import static com.view.rubbledumpsterrental.model.entities.RubbleDumpsterStatus.*;


public class Main {
    public static void main(String[] args) {

        RubbleDumpster rubbleDumpster = new RubbleDumpster(1,50.0, 100.0, AVAILABLE);
        System.out.println(rubbleDumpster.toString());


        Cep cep = new Cep("12345-678");
        Address address = new Address("Rua Exemplo", "Bairro Exemplo", "123", "Cidade Exemplo", cep);

        if (address.isValid()) {
            System.out.println("O endereço é válido.");
        } else {
            System.out.println("O endereço é inválido.");
        }




    }
}