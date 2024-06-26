package com.dumpRents.model.entities.valueObjects;

import java.util.Objects;

public class Cpf {
    private String cpf;

    public Cpf(String cpf) {
        this.cpf = cpf;
        if (!isValid()){
            throw new IllegalArgumentException("Invalid CPF");
        }
    }

    public boolean isValid() {
        if (cpf == null || cpf.isEmpty()) {
            return false;
        }

        // Remove pontos e traços caso existam
        String cpfNumeros = cpf.replaceAll("\\D", "");

        // O CPF deve conter 11 dígitos
        if (cpfNumeros.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (caso contrário inválido)
        if (cpfNumeros.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Calcula o primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (cpfNumeros.charAt(i) - '0') * (10 - i);
        }
        int primeiroDigitoVerificador = 11 - (soma % 11);
        if (primeiroDigitoVerificador >= 10) {
            primeiroDigitoVerificador = 0;
        }

        // Calcula o segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (cpfNumeros.charAt(i) - '0') * (11 - i);
        }
        int segundoDigitoVerificador = 11 - (soma % 11);
        if (segundoDigitoVerificador >= 10) {
            segundoDigitoVerificador = 0;
        }

        // Verifica se os dígitos verificadores estão corretos
        return cpfNumeros.charAt(9) - '0' == primeiroDigitoVerificador &&
                cpfNumeros.charAt(10) - '0' == segundoDigitoVerificador;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cpf cpf1 = (Cpf) o;
        return Objects.equals(cpf, cpf1.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cpf);
    }
}
