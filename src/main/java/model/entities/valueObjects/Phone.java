package com.view.rubbledumpsterrental.model.entities.valueObjects;

public class Phone {
    private String phone;

    public Phone(String phone) {
        this.phone = phone;
    }

    public boolean isValid() {
        if (phone == null || phone.isEmpty())  return false;

        // Remove espaços, parênteses e traços
        String cleanedPhone = phone.replaceAll("[\\s()-]", "");

        // Verifica se o telefone tem 10 ou 11 dígitos e se todos os caracteres são numéricos
        return (cleanedPhone.matches("\\d{10}") || cleanedPhone.matches("\\d{11}"));
    }
}
