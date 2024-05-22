package model.entities.valueObjects;

public class Address {
    private String street;
    private String district;
    private String number;
    private String city;
    private Cep cep;

    public Address(String street, String district, String number, String city, Cep cep) {
        this.street = street;
        this.district = district;
        this.number = number;
        this.city = city;
        this.cep = cep;
    }

    public void isValid() {}
}
