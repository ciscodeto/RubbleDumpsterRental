package com.dumpRents.model.useCases.rental;

import com.dumpRents.model.Notification;
import com.dumpRents.model.Validator;
import com.dumpRents.model.entities.Report;

import java.time.LocalDate;

public class EntryAndReportValidator extends Validator<Report> {
    @Override
    public Notification validate(Report report) {
        LocalDate initialDate = report.initialDate();
        LocalDate endDate = report.withdrawalDate();

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
