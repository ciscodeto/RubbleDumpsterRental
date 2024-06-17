package com.model.entities;

public enum RentalStatus {
    OPEN("Aberto"),
    WITHDRAWAL_ORDER("Ordem de Retirada"),
    CLOSED("Fechado");

    private String label;
    RentalStatus(String label) {
        this.label = label;
    }
}
