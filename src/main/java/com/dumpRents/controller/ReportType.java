package com.dumpRents.controller;

public enum ReportType {
    INCOME("renda"),
    ENTRY_EXIT("entrada_saida");

    private String label;
    ReportType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
