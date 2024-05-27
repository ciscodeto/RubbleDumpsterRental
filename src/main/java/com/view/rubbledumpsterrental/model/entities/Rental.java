package com.view.rubbledumpsterrental.model.entities;

import model.entities.valueObjects.Address;

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

    public void withdrawalRequest() {}
    public void endRental() {}
    public void calculateFinalAmount() {}
}
