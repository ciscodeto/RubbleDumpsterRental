package com.view.rubbledumpsterrental.model.useCases.rubbleDumpster;


// Status = AVAILABLE

// Registro existir

import com.view.rubbledumpsterrental.model.Notification;
import com.view.rubbledumpsterrental.model.Validator;
import com.view.rubbledumpsterrental.model.entities.RubbleDumpster;
import com.view.rubbledumpsterrental.model.entities.RubbleDumpsterStatus;
import com.view.rubbledumpsterrental.persistence.dao.RubbleDumpsterDAO;
import com.view.rubbledumpsterrental.persistence.utils.EntityAlreadyExistsException;

import javax.management.NotificationBroadcaster;

public class InsertRubbleDumpsterUseCase {

    private RubbleDumpsterDAO rubbleDumbsterDAO;

    public InsertRubbleDumpsterUseCase(RubbleDumpsterDAO rubbleDumbsterDAO) {
        this.rubbleDumbsterDAO = rubbleDumbsterDAO;
    }

    public Integer insert(RubbleDumpster rubbleDumpster) {
        Validator<RubbleDumpster> validator = new RubbleDumbsterInsertValidator();

        Notification notification = validator.validate(rubbleDumpster);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer serialNumber = rubbleDumpster.getSerialNumber();

        if (rubbleDumbsterDAO.findOne(serialNumber).isPresent())
            throw new EntityAlreadyExistsException("Este Serial Number já existe");

        rubbleDumpster.setStatus(RubbleDumpsterStatus.AVAILABLE);

        return rubbleDumbsterDAO.create(rubbleDumpster);
    }

}
