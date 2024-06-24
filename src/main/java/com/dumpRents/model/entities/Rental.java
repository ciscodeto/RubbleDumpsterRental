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
                '}';
    }

    public double calculateFinalAmount() {
        double differenceInDays = ChronoUnit.DAYS.between(this.initialDate, this.endDate);
        double differenceInMonths = Math.ceil(differenceInDays/30.0);
        return differenceInMonths > 1 ?
                differenceInMonths * rubbleDumpster.getMonthlyAmount() :
                rubbleDumpster.getMinAmount();
    }

    public void end() {
        this.rentalStatus = RentalStatus.CLOSED;
        this.finalAmount = this.calculateFinalAmount();
    }

    public RubbleDumpster getRubbleDumpster() {
        return this.rubbleDumpster;
    }

    public Client getClient() {
        return this.client;
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

    public void setRentalStatus(RentalStatus rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    public void setWithdrawalRequestDate(LocalDate withdrawalRequestDateDate) {
        this.withdrawalRequestDate = withdrawalRequestDateDate;
    }

    public void setWithdrawalDate(LocalDate withdrawalDate) {
        this.withdrawalDate = withdrawalDate;
    }
}
