package com.dumpRents.model.useCases.rubbleDumpster;

import com.dumpRents.model.Notification;
import com.dumpRents.model.Validator;
import com.dumpRents.model.entities.RubbleDumpster;
import com.dumpRents.model.entities.RubbleDumpsterStatus;
import com.dumpRents.persistence.dao.RubbleDumpsterDAO;
import com.dumpRents.persistence.utils.EntityAlreadyExistsException;

public class ActivateRubbleDumpsterUseCase {
    private RubbleDumpsterDAO rubbleDumpsterDAO;

    public ActivateRubbleDumpsterUseCase(RubbleDumpsterDAO rubbleDumpsterDAO) {
        this.rubbleDumpsterDAO = rubbleDumpsterDAO;
    }

    public boolean activate(RubbleDumpster rubbleDumpster) {
        Validator<RubbleDumpster> validator = new RubbleDumpsterInsertValidator();

        Notification notification = validator.validate(rubbleDumpster);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer serialNumber = rubbleDumpster.getSerialNumber();

        if (rubbleDumpsterDAO.findOne(serialNumber).isEmpty() && rubbleDumpster.getStatus() != RubbleDumpsterStatus.DISABLED)
            throw new EntityAlreadyExistsException("Caçamba não localizada ou encontra-se com status diferente desabilitado");

//        rubbleDumpster.setStatus(RubbleDumpsterStatus.DISABLED);
        rubbleDumpster.activateRubbleDumpster();

        return rubbleDumpsterDAO.update(rubbleDumpster);
    }
}