package com.view.rubbledumpsterrental.model.entities;

import com.view.rubbledumpsterrental.model.entities.valueObjects.Address;

import java.time.LocalDate;

public class Rental {
    private Integer id;
    private LocalDate initialDate;
    private LocalDate dateRequestWithdrawal;
    private LocalDate dataWithdrawal;
    private LocalDate endDate;
    private Double finalAmount;
    private Enum<RentalStatus> rentalStatus;
    private Address address;
    private Client client;
    private RentalStatus status;

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

    public LocalDate getDateRequestWithdrawal() {
        return dateRequestWithdrawal;
    }

    public void setDateRequestWithdrawal(LocalDate dateRequestWithdrawal) {
        this.dateRequestWithdrawal = dateRequestWithdrawal;
    }

    public LocalDate getDataWithdrawal() {
        return dataWithdrawal;
    }

    public void setDataWithdrawal(LocalDate dataWithdrawal) {
        this.dataWithdrawal = dataWithdrawal;
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

    public void setStatus(RentalStatus status) {
        this.status = status;
    }

    public void withdrawalRequest() {}
    public void endRental() {}
    public void calculateFinalAmount() {}
}