package com.view.rubbledumpsterrental.model.useCases.rubbleDumpster;

import com.view.rubbledumpsterrental.model.Notification;
import com.view.rubbledumpsterrental.model.Validator;
import com.view.rubbledumpsterrental.model.entities.Rental;
import com.view.rubbledumpsterrental.model.entities.RubbleDumpster;
import com.view.rubbledumpsterrental.model.entities.RubbleDumpsterStatus;

public class RubbleDumbsterInsertValidator extends Validator<RubbleDumpster> {
    @Override
    public Notification validate(RubbleDumpster rubbleDumpster) {
        Notification notification = new Notification();
        if (rubbleDumpster == null) {
            notification.addError("RubbleDumpster is null");
        }
        if (nullOrEmpty(rubbleDumpster.getMinAmount()))
            notification.addError("Minimum amount is null or empty");

        if (nullOrEmpty(rubbleDumpster.getMonthlyAmount()))
            notification.addError("Monthly amount is null or empty");

        if (nullOrEmpty(rubbleDumpster.getSerialNumber()))
            notification.addError("Serial number is null or empty");

        if (nullOrEmpty(rubbleDumpster.getStatus()))
            notification.addError("RubbleDumpsterStatus is null or empty");

        if (nullOrEmpty(rubbleDumpster.getRental()))
            notification.addError("Rental is null or empty");

        return notification;
    }
}

