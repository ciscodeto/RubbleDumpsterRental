package com.view.rubbledumpsterrental.model.useCases.rubbleDumpster;

import com.view.rubbledumpsterrental.model.Notification;
import com.view.rubbledumpsterrental.model.Validator;
import com.view.rubbledumpsterrental.model.entities.RubbleDumpster;
import com.view.rubbledumpsterrental.model.entities.RubbleDumpsterStatus;
import com.view.rubbledumpsterrental.persistence.dao.RubbleDumpsterDAO;
import com.view.rubbledumpsterrental.persistence.utils.EntityAlreadyExistsException;

public class InactivateRubbleDumpsterUseCase {
    private RubbleDumpsterDAO rubbleDumbsterDAO;

    public InactivateRubbleDumpsterUseCase(RubbleDumpsterDAO rubbleDumbsterDAO) {
        this.rubbleDumbsterDAO = rubbleDumbsterDAO;
    }

    public boolean inactivate(RubbleDumpster rubbleDumpster) {
        Validator<RubbleDumpster> validator = new RubbleDumbsterInsertValidator();

        Notification notification = validator.validate(rubbleDumpster);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer serialNumber = rubbleDumpster.getSerialNumber();

        if (rubbleDumbsterDAO.findOne(serialNumber).isEmpty() && rubbleDumpster.getStatus() != RubbleDumpsterStatus.AVAILABLE)
            throw new EntityAlreadyExistsException("Caçamba não localizada ou não está disponível");

        rubbleDumpster.setStatus(RubbleDumpsterStatus.DISABLED);

        return rubbleDumbsterDAO.update(rubbleDumpster);
    }
}

