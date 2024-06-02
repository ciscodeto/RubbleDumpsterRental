package model.useCases.rubbleDumpster;

import model.Notification;
import model.Validator;
import model.entities.RubbleDumpster;
import model.entities.RubbleDumpsterStatus;
import persistence.dao.RubbleDumpsterDAO;
import persistence.utils.EntityAlreadyExistsException;

public class ActivateRubbleDumpsterUseCase {
    private RubbleDumpsterDAO rubbleDumpsterDAO;

    public ActivateRubbleDumpsterUseCase(RubbleDumpsterDAO rubbleDumpsterDAO) {
        this.rubbleDumpsterDAO = rubbleDumpsterDAO;
    }

    public boolean inactivate(RubbleDumpster rubbleDumpster) {
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
