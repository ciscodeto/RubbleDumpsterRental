package com.dumpRents.model.entities.valueObjects;

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

    public boolean isValid() {
        return isNotEmpty(street) &&
                isNotEmpty(district) &&
                isNotEmpty(number) &&
                isNotEmpty(city) &&
                cep != null && cep.isValid();
    }

    private boolean isNotEmpty(String field) {
        return field != null && !field.trim().isEmpty();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Cep getCep() {
        return cep;
    }

    public void setCep(Cep cep) {
        this.cep = cep;
    }

    @Override
    public String toString() {
        return "Rua: '" + street + '\'' +
                ", Bairro: '" + district + '\'' +
                ", Número: '" + number + '\'' +
                ", Cidade: '" + city + '\'' +
                ", CEP: " + cep;
    }
}
