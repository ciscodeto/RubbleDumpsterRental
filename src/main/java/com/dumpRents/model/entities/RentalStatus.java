package com.dumpRents.model.entities;

import java.util.Arrays;

public enum RentalStatus {
    OPEN("Aberto"),
    WITHDRAWAL_ORDER("Ordem de Retirada"),
    CLOSED("Fechado");

    private String label;
    RentalStatus(String label) {
        this.label = label;
    }

    public static RentalStatus toEnum(String value) {
        return Arrays.stream(RentalStatus.values())
                .filter(c -> value.equals(c.toString()))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public String toString() {
        return label;
    }
}
