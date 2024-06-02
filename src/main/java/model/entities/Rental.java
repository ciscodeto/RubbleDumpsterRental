package model.entities;

import model.entities.valueObjects.Address;

import java.time.LocalDate;

public class Rental {
    private Integer id;
    private LocalDate initialDate;
    private LocalDate withdrawalRequestDate;
    private LocalDate withdrawalDate;
    private LocalDate endDate;
    private Double finalAmount;
    private Enum<RentalStatus> rentalStatus;
    private Address address;
    private Client client;
    private RubbleDumpster rubbleDumpster;
    private RentalStatus status;

    public Rental(RubbleDumpster dumpster, Client client, LocalDate initialDate) {
        this.client = client;
        this.rubbleDumpster = dumpster;
        this.initialDate = initialDate;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }
    public void withdrawalRequest() {}
    public void endRental() {}
    public void calculateFinalAmount() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDate initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDate getWithdrawalRequestDate() {
        return withdrawalRequestDate;
    }

    public void setWithdrawalRequestDate(LocalDate withdrawalRequestDate) {
        this.withdrawalRequestDate = withdrawalRequestDate;
    }

    public LocalDate getWithdrawalDate() {
        return withdrawalDate;
    }

    public void setWithdrawalDate(LocalDate withdrawalDate) {
        this.withdrawalDate = withdrawalDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(Double finalAmount) {
        this.finalAmount = finalAmount;
    }

    public Enum<RentalStatus> getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(Enum<RentalStatus> rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public RubbleDumpster getRubbleDumpster() {
        return rubbleDumpster;
    }

    public void setRubbleDumpster(RubbleDumpster rubbleDumpster) {
        this.rubbleDumpster = rubbleDumpster;
    }
}
