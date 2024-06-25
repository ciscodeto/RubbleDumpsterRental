package com.dumpRents.model.entities;

import javax.naming.InsufficientResourcesException;
import java.time.LocalDate;

import static com.dumpRents.model.entities.RubbleDumpsterStatus.*;

public class RubbleDumpster {
    private Integer serialNumber;
    private Double minAmount;
    private Double monthlyAmount;
    private RubbleDumpsterStatus status;

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
    public RubbleDumpster(Integer serialNumber, Double minAmount, Double monthlyAmount, RubbleDumpsterStatus status, Integer id) {
        this.serialNumber = serialNumber;
        this.minAmount = minAmount;
        this.monthlyAmount = monthlyAmount;
        this.status = status;
        this.Id = id;
    }

    @Override
    public String toString() {
        return "RubbleDumpster serialNumber: " + this.getSerialNumber() +
                ", minAmount: " + this.getMinAmount() +
                ", monthlyAmount: " + this.getMonthlyAmount() +
                ", status: " + this.getStatus();
    }

    public void rentRubbleDumpster() {
        this.status = RENTED;
    }


    public void withdrawalRequest( double withdrawalAmount)  {
        this.status = WITHDRAWAL_ORDER;
    }

    public void inactivateRubbleDumpster() {
        if (this.status == AVAILABLE ) {
            this.status = DISABLED;
        } else
            throw new IllegalArgumentException("Não é possível realizar a desativação pois a caçamba não está ativa");
    }

    public void activateRubbleDumpster() {
        if (this.status == DISABLED) {
            this.status = AVAILABLE;
        } else
            throw new IllegalArgumentException("Não é possível realizar a ativação pois a caçamba não está desabilitada");
    }
    public void UpdateRentalPrice(Double monthlyAmount){
        this.setMonthlyAmount(monthlyAmount);

    }
    public void activateNewRubbleDumpster(){
        this.setStatus(AVAILABLE);
    }
}

