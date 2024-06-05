package model.useCases.rental;

import model.Notification;
import model.Validator;
import model.entities.Rental;

import java.time.LocalDate;

public class EntryAndReportValidator {
    public Notification validate(LocalDate initialDate, LocalDate endDate) {
        Notification notification = new Notification();

        if (initialDate == null) {
            notification.addError("Initial date is null");
        } else if (initialDate.isAfter(LocalDate.now())) {
            notification.addError("Initial date cannot be in the future");
        }

        if (endDate != null && endDate.isBefore(initialDate)) {
            notification.addError("End date cannot be before initial date");
        }

        return notification;
    }
}
