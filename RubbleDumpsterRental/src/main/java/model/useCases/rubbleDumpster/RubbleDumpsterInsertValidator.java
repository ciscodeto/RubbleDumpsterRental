package model.useCases.rubbleDumpster;

import model.Notification;
import model.Validator;
import model.entities.RubbleDumpster;

public class RubbleDumpsterInsertValidator extends Validator<RubbleDumpster> {
    @Override
    public Notification validate(RubbleDumpster rubbleDumpster) {
        Notification notification = new Notification();
        if (rubbleDumpster == null) {
            notification.addError("RubbleDumpster não pode ser nulo!");
        }
        if (nullOrEmpty(rubbleDumpster.getMinAmount()))
            notification.addError("Minimum amount não pode ser nulo ou vazio!");

        if (nullOrEmpty(rubbleDumpster.getMonthlyAmount()))
            notification.addError("Monthly amount não pode ser nulo ou vazio!");

        if (nullOrEmpty(rubbleDumpster.getSerialNumber()))
            notification.addError("Serial number não pode ser nulo ou vazio!");

        if (nullOrEmpty(rubbleDumpster.getStatus()))
            notification.addError("RubbleDumpsterStatus não pode ser nulo ou vazio!");

        if (nullOrEmpty(rubbleDumpster.getRental()))
            notification.addError("Rental não pode ser nulo ou vazio!");

        return notification;
    }
}

