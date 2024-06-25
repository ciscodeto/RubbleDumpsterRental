package com.dumpRents.model.entities;

import com.dumpRents.model.entities.valueObjects.Address;
import com.dumpRents.model.entities.valueObjects.Cpf;
import com.dumpRents.model.entities.valueObjects.Email;
import com.dumpRents.model.entities.valueObjects.Phone;

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


    public Client(String name, Address address, Cpf cpf, Phone phone2, Phone phone1, List<Email> emailList, Integer id) {
        this.name = name;
        this.address = address;
        this.cpf = cpf;
        this.phone2 = phone2;
        this.phone1 = phone1;
        this.emailList = emailList;
        this.id = id;
    }

    public Client() {}

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public Cpf getCpf() { return cpf; }

    public Phone getPhone1() {
        return phone1;
    }

    public Phone getPhone2() {
        return phone2;
    }

    public List<Email> getEmailList() {
        return emailList;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setCpf(Cpf cpf) {
        this.cpf = cpf;
    }

    public void setPhone1(Phone phone1) {
        this.phone1 = phone1;
    }

    public void setPhone2(Phone phone2) {
        this.phone2 = phone2;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmailList(List<Email> emailList) {
        this.emailList = emailList;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address.toString() +
                ", cpf=" + cpf.toString() +
                ", phone1=" + phone1.toString() +
                ", phone2=" + phone2.toString() +
                ", emailList=" + emailList.toString() +
                '}';
    }
}
