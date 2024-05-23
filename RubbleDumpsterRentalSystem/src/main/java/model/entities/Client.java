package model.entities;

import model.entities.valueObjects.Address;
import model.entities.valueObjects.Cpf;
import model.entities.valueObjects.Email;
import model.entities.valueObjects.Phone;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private Integer id;
    private String name;
    private Address address;
    private Cpf cpf;
    private List<Email> emailList = new ArrayList<Email>();
    private List<Phone> phoneList = new ArrayList<Phone>();
}
