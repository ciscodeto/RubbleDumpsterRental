package model.useCases.rubbleDumpster;

import model.Notification;
import model.Validator;
import model.entities.RubbleDumpster;
import model.entities.RubbleDumpsterStatus;
import persistence.dao.RubbleDumpsterDAO;
import persistence.utils.EntityAlreadyExistsException;

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

