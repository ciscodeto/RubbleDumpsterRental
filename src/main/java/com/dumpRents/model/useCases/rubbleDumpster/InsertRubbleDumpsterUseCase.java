package com.dumpRents.model.useCases.rubbleDumpster;


// Status = AVAILABLE

// Registro existir

import com.dumpRents.model.Notification;
import com.dumpRents.model.Validator;
import com.dumpRents.model.entities.RubbleDumpster;
import com.dumpRents.persistence.dao.RubbleDumpsterDAO;
import com.dumpRents.persistence.utils.EntityAlreadyExistsException;

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

        if (rubbleDumpsterDAO.findOneBySerialNumber(serialNumber).isPresent())
            throw new EntityAlreadyExistsException("Este Serial Number já existe");

        rubbleDumpster.activate();

        return rubbleDumpsterDAO.create(rubbleDumpster);
    }

}