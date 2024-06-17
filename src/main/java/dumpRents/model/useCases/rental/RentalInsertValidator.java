package com.model.useCases.rental;

import com.model.Notification;
import com.model.Validator;
import com.model.entities.Rental;

import java.time.LocalDate;

public class RentalInsertValidator extends Validator<Rental> {
    @Override
    public Notification validate(Rental rental) {
        Notification notification = new Notification();

        if (rental == null)
            notification.addError("Rental is null");

        assert rental != null;
        if (rental.getRubbleDumpster() == null) {
            notification.addError("Rubble Dumpster is null");
        }

        if (rental.getClient() == null) {
            notification.addError("Client is null");
        }

        if (rental.getInitialDate() == null) {
            notification.addError("Initial date is null");
        } else if (rental.getInitialDate().isAfter(LocalDate.now())) {
            notification.addError("Initial date cannot be in the future");
        }

        if (rental.getEndDate() != null && rental.getEndDate().isBefore(rental.getInitialDate())) {
            notification.addError("End date cannot be before initial date");
        }

        return notification;
    }
}
