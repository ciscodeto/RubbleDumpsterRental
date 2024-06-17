package com.model.entities;

import javax.naming.InsufficientResourcesException;
import java.lang.ref.SoftReference;
import java.time.LocalDate;

import static com.model.entities.RubbleDumpsterStatus.*;

public class RubbleDumpster {
    private Integer serialNumber;
    private Double minAmount;
    private Double monthlyAmount;
    private RubbleDumpsterStatus status;
    private Rental rental;

    private Integer Id;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public Double getMinAmount() {
        return minAmount;
    }

    public Double getMonthlyAmount() {
        return monthlyAmount;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
    }

    public void setMonthlyAmount(Double monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
    }

    public RubbleDumpsterStatus getStatus() {
        return status;
    }

    public void setStatus(RubbleDumpsterStatus status) {
        this.status = status;
    }



    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    public RubbleDumpster() {
    }
    public RubbleDumpster(Double minAmount, Double monthlyAmount, RubbleDumpsterStatus status) {
        this( null, minAmount,monthlyAmount, status);
    }
    public RubbleDumpster(Integer serialNumber, Double minAmount, Double monthlyAmount, RubbleDumpsterStatus status) {
        this.serialNumber = serialNumber;
        this.minAmount = minAmount;
        this.monthlyAmount = monthlyAmount;
        this.status = status;
    }
    public RubbleDumpster(Integer serialNumber, Double minAmount, Double monthlyAmount, RubbleDumpsterStatus status, Rental rental) {
        this.serialNumber = serialNumber;
        this.minAmount = minAmount;
        this.monthlyAmount = monthlyAmount;
        this.status = status;
        this.rental = rental;
    }

    @Override
    public String toString() {
        return "RubbleDumpster serialNumber: " + this.getSerialNumber() +
                ", minAmount: " + this.getMinAmount() +
                ", monthlyAmount: " + this.getMonthlyAmount() +
                ", status: " + this.getStatus();
    }

    public void rentRubbleDumpster() {}



    public void withdrawalRequest( double withdrawalAmount)  {
        if (withdrawalAmount <= 0){
            throw new IllegalArgumentException("O valor da ordem de retirada deve ser um valor positivo!");
        }
        if (withdrawalAmount > this.rental.getFinalAmount()){
            try {
                throw new InsufficientResourcesException("O valor da ordem de retirada excede o balanço disponível!");
            } catch (InsufficientResourcesException e) {
                throw new RuntimeException(e);
            }
        }

        this.rental.setFinalAmount(this.rental.getFinalAmount() - withdrawalAmount);
        this.setStatus(WITHDRAWAL_ORDER);

        System.out.println("O valor final atual é de: " + this.rental.getFinalAmount());
        System.out.println("\n E o status atual da caçamba é: " + this.getStatus());
    }

    public void inactivateRubbleDumpster() {
        if (this.status == RENTED && this.rental.getEndDate().isBefore(LocalDate.now())) {
            this.status = DISABLED;
        }
        else
            System.out.println("Não é possível realizar a desativação pois a caçamba não está alugada");
    }

    public void activateRubbleDumpster() {
        if (this.status == WITHDRAWAL_ORDER) {
            this.status = AVAILABLE;
        } else
            System.out.println("Para ativar a caçamba, é necessário que ela possua ordem de retirada!");
    }
}

