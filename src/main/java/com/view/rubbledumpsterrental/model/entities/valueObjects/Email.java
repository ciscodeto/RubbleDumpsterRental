package com.view.rubbledumpsterrental.model.entities.valueObjects;

public class Email {
    private String email;

    public Email(String email) {
        this.email = email;
    }

    public boolean isValid() {
        if (email == null || email.isEmpty()) {
            return false;
        }

        // Expressão regular para validar e-mails
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

        return email.matches(emailRegex);
    }}
