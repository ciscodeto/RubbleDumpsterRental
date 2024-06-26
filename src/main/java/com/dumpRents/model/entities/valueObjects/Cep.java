package com.dumpRents.model.entities.valueObjects;

public class Cep {
    private String cep;

    public Cep(String cep) {
        this.cep = cep;

        if (!isValid()){
            throw new IllegalArgumentException("Cep inválido");
        }
    }

    public boolean isValid() {
        if (cep == null || cep.isEmpty()) return false;

        // Verifica se o CEP segue o formato "XXXXX-XXX" ou "XXXXXXXX"
        return cep.matches("\\d{5}-\\d{3}") || cep.matches("\\d{8}");
    }

    @Override
    public String toString() {
        return cep;
    }
}
