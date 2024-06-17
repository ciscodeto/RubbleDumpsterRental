package dumpRents.model.useCases.rubbleDumpster;

import dumpRents.model.Notification;
import dumpRents.model.Validator;
import dumpRents.model.entities.RubbleDumpster;
import dumpRents.model.entities.RubbleDumpsterStatus;
import dumpRents.persistence.dao.RubbleDumpsterDAO;
import dumpRents.persistence.utils.EntityAlreadyExistsException;

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
