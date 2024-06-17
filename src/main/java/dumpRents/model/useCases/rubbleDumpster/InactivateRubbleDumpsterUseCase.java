package dumpRents.model.useCases.rubbleDumpster;

import dumpRents.model.Notification;
import dumpRents.model.Validator;
import dumpRents.model.entities.RubbleDumpster;
import dumpRents.model.entities.RubbleDumpsterStatus;
import dumpRents.persistence.dao.RubbleDumpsterDAO;
import dumpRents.persistence.utils.EntityAlreadyExistsException;

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

