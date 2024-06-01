package com.view.rubbledumpsterrental.model.entities.valueObjects;

public class Cep {
    private String cep;

    public Cep(String cep) {
        this.cep = cep;
    }

    public boolean isValid() {
        if (cep == null || cep.isEmpty()) {
            return false;
        }
        // Verifica se o CEP segue o formato "XXXXX-XXX" ou "XXXXXXXX"
        return cep.matches("\\d{5}-\\d{3}") || cep.matches("\\d{8}");
    }
}
