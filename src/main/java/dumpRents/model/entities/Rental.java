package dumpRents.model.entities;

import dumpRents.model.entities.valueObjects.Address;

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

    public Rental(RubbleDumpster dumpster, Client client, LocalDate initialDate) {
        this.client = client;
        this.rubbleDumpster = dumpster;
        this.initialDate = initialDate;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", rentalStatus=" + rentalStatus +
                '}';
    }

    public void withdrawalRequest() {}
    public void endRental() {}

    public double calculateFinalAmount() {
        double differenceInDays = ChronoUnit.DAYS.between(this.initialDate, this.endDate);
        double differenceInMonths = Math.ceil(differenceInDays/30.0);
        return differenceInMonths > 1 ?
                differenceInMonths * rubbleDumpster.getMonthlyAmount() :
                rubbleDumpster.getMinAmount();
    }

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

    public void conclude() {
        this.endDate = LocalDate.now();
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

    public RubbleDumpster getRubbleDumpster() {
        return rubbleDumpster;
    }

    public void setRubbleDumpster(RubbleDumpster rubbleDumpster) {
        this.rubbleDumpster = rubbleDumpster;
    }
}
