package com.model.useCases.rubbleDumpster;


// Status = AVAILABLE

// Registro existir

import com.model.Notification;
import com.model.Validator;
import com.model.entities.RubbleDumpster;
import com.model.entities.RubbleDumpsterStatus;
import com.persistence.dao.RubbleDumpsterDAO;
import com.persistence.utils.EntityAlreadyExistsException;

public class InsertRubbleDumpsterUseCase {

    private RubbleDumpsterDAO rubbleDumpsterDAO;

    public InsertRubbleDumpsterUseCase(RubbleDumpsterDAO rubbleDumpsterDAO) {
        this.rubbleDumpsterDAO = rubbleDumpsterDAO;
    }

    public Integer insert(RubbleDumpster rubbleDumpster) {
        Validator<RubbleDumpster> validator = new RubbleDumpsterInsertValidator();

        Notification notification = validator.validate(rubbleDumpster);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer serialNumber = rubbleDumpster.getSerialNumber();

        if (rubbleDumpsterDAO.findOne(serialNumber).isPresent())
            throw new EntityAlreadyExistsException("Este Serial Number já existe");

        rubbleDumpster.setStatus(RubbleDumpsterStatus.AVAILABLE);

        return rubbleDumpsterDAO.create(rubbleDumpster);
    }

}
