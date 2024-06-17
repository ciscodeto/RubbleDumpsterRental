package com.model.useCases.rubbleDumpster;

import com.model.Notification;
import com.model.Validator;
import com.model.entities.RubbleDumpster;
import com.model.entities.RubbleDumpsterStatus;
import com.persistence.dao.RubbleDumpsterDAO;
import com.persistence.utils.EntityAlreadyExistsException;

public class InactivateRubbleDumpsterUseCase {
    private RubbleDumpsterDAO rubbleDumpsterDAO;

    public InactivateRubbleDumpsterUseCase(RubbleDumpsterDAO rubbleDumpsterDAO) {
        this.rubbleDumpsterDAO = rubbleDumpsterDAO;
    }

    public boolean inactivate(RubbleDumpster rubbleDumpster) {
        Validator<RubbleDumpster> validator = new RubbleDumpsterInsertValidator();

        Notification notification = validator.validate(rubbleDumpster);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer serialNumber = rubbleDumpster.getSerialNumber();

        if (rubbleDumpsterDAO.findOne(serialNumber).isEmpty() && rubbleDumpster.getStatus() != RubbleDumpsterStatus.AVAILABLE)
            throw new EntityAlreadyExistsException("Caçamba não localizada ou não  disponível");

        rubbleDumpster.setStatus(RubbleDumpsterStatus.DISABLED);

        return rubbleDumpsterDAO.update(rubbleDumpster);
    }
}

