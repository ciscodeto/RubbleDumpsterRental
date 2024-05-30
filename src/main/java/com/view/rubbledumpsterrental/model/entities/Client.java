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

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public void setCpf(Cpf cpf) {
        this.cpf = cpf;
    }

    public Phone getPhone1() {
        return phone1;
    }

    public void setPhone1(Phone phone1) {
        this.phone1 = phone1;
    }

    public Phone getPhone2() {
        return phone2;
    }

    public void setPhone2(Phone phone2) {
        this.phone2 = phone2;
    }

    public List<Email> getEmailList() {
        return emailList;
    }




}
