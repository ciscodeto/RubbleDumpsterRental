package com.view.rubbledumpsterrental.model.entities;

import com.view.rubbledumpsterrental.model.entities.valueObjects.Address;
import com.view.rubbledumpsterrental.model.entities.valueObjects.Cpf;
import com.view.rubbledumpsterrental.model.entities.valueObjects.Email;
import com.view.rubbledumpsterrental.model.entities.valueObjects.Phone;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private Integer id;
    private String name;
    private Address address;
    private Cpf cpf;
    private Phone phone1;
    private Phone phone2;
    private List<Email> emailList = new ArrayList<Email>();
}
