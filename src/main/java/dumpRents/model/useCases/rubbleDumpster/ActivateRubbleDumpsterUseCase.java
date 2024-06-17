package com.model.useCases.rubbleDumpster;

import com.model.Notification;
import com.model.Validator;
import com.model.entities.RubbleDumpster;
import com.model.entities.RubbleDumpsterStatus;
import com.persistence.dao.RubbleDumpsterDAO;
import com.persistence.utils.EntityAlreadyExistsException;

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
            throw new EntityAlreadyExistsException("Caçamba não localizada ou encontra-se com status diferente de desabilitado");

        rubbleDumpster.setStatus(RubbleDumpsterStatus.AVAILABLE);

        return rubbleDumpsterDAO.update(rubbleDumpster);
    }
}
