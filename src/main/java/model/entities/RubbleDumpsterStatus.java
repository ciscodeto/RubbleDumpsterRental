package model.entities;

import javax.swing.*;

public enum RubbleDumpsterStatus {
    AVAILABLE("Disponível"),
    RENTED("Alugada"),
    WITHDRAWAL_ORDER("Ordem de Retirada"),
    DISABLED("Desabilitado");

    private String label;
    RubbleDumpsterStatus(String label) {
        this.label = label;
    }
}
