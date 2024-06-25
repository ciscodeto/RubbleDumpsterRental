package com.dumpRents.model.entities;

import com.dumpRents.model.entities.valueObjects.Address;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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

    public Rental(RubbleDumpster dumpster, Client client, LocalDate initialDate, Address address) {
        this.client = client;
        this.rubbleDumpster = dumpster;
        this.initialDate = initialDate;
        this.rentalStatus = RentalStatus.OPEN;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", rentalStatus=" + rentalStatus +
                ", client=" + client +
                ", rubbleDumpster=" + rubbleDumpster +
                ", initialDate=" + initialDate +
                ", withdrawalDate=" + withdrawalDate +
                ", endDate=" + endDate +
                ", finalAmount=" + finalAmount +
                '}';
    }

    public double calculateFinalAmount() {
        if (this.endDate == null) {
            throw new IllegalStateException("End date is not set");
        }
        double differenceInDays = ChronoUnit.DAYS.between(this.initialDate, this.endDate);
        double differenceInMonths = Math.ceil(differenceInDays/30.0);
        return differenceInMonths > 1 ?
                differenceInMonths * rubbleDumpster.getMonthlyAmount() :
                rubbleDumpster.getMinAmount();
    }

    public void endRental() {
        this.endDate = LocalDate.now();
        this.finalAmount = calculateFinalAmount();
        this.rentalStatus = RentalStatus.CLOSED;
        this.rubbleDumpster.activate();
    }

    public void requestWithdrawal(LocalDate localDate) {
        if (withdrawalDate == null || withdrawalDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid withdrawal date.");
        }
        this.withdrawalRequestDate = LocalDate.now();
        this.rentalStatus = RentalStatus.WITHDRAWAL_ORDER;
        this.rubbleDumpster.withdrawalRequest(1);
    }

    public RubbleDumpster getRubbleDumpster() {
        return this.rubbleDumpster;
    }

    public Integer getRubbleDumpsterSN() {
        return this.rubbleDumpster.getSerialNumber();
    }

    public Client getClient() {
        return this.client;
    }

    public String getClientName() {
        return this.client.getName();
    }

    public LocalDate getInitialDate() {
        return this.initialDate;
    }

    public LocalDate getWithdrawalDate() {
        return this.withdrawalDate;
    }

    public double getFinalAmount() {
        return this.finalAmount;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Enum<RentalStatus> getRentalStatus() {
        return this.rentalStatus;
    }

    public Integer getId() {
        return this.id;
    }

    public LocalDate getWithdrawalRequestDate() {
        return withdrawalRequestDate;
    }

    public String getAddress() {
        return address.toString();
    }

    public void setRentalStatus(RentalStatus rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    public void setWithdrawalRequestDate(LocalDate withdrawalRequestDateDate) {
        this.withdrawalRequestDate = withdrawalRequestDateDate;
    }

    public void setWithdrawalDate(LocalDate withdrawalDate) {
        this.withdrawalDate = withdrawalDate;
    }

    public void setId(int idCounter) {
        this.id = idCounter;
    }
}
