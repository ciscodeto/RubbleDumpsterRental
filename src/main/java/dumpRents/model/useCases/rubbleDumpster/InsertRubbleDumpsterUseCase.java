package dumpRents.model.useCases.rubbleDumpster;


// Status = AVAILABLE

// Registro existir

import dumpRents.model.Notification;
import dumpRents.model.Validator;
import dumpRents.model.entities.RubbleDumpster;
import dumpRents.model.entities.RubbleDumpsterStatus;
import dumpRents.persistence.dao.RubbleDumpsterDAO;
import dumpRents.persistence.utils.EntityAlreadyExistsException;

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
