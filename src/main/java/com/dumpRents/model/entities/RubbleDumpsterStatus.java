package com.dumpRents.model.entities;

import javax.swing.*;
import java.util.Arrays;

public enum RubbleDumpsterStatus {
    AVAILABLE("Disponível"),
    RENTED("Alugada"),
    WITHDRAWAL_ORDER("Ordem de Retirada"),
    DISABLED("Desabilitado");

    private String label;

    RubbleDumpsterStatus(String label) {
        this.label = label;
    }

    @Override
    public String toString() {return label;}

    public static RubbleDumpsterStatus toEnum(String value) {
        return Arrays.stream(RubbleDumpsterStatus.values())
                .filter(c->value.equals(c.toString()))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
