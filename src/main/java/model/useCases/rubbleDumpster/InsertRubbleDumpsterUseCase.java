package model.useCases.rubbleDumpster;


// Status = AVAILABLE

// Registro existir

import model.Notification;
import model.Validator;
import model.entities.RubbleDumpster;
import model.entities.RubbleDumpsterStatus;
import persistence.dao.RubbleDumpsterDAO;
import persistence.utils.EntityAlreadyExistsException;

import javax.management.NotificationBroadcaster;

public class InsertRubbleDumpsterUseCase {

    private RubbleDumpsterDAO rubbleDumbsterDAO;

    public InsertRubbleDumpsterUseCase(RubbleDumpsterDAO rubbleDumbsterDAO) {
        this.rubbleDumbsterDAO = rubbleDumbsterDAO;
    }

    public Integer insert(RubbleDumpster rubbleDumpster) {
        Validator<RubbleDumpster> validator = new RubbleDumbsterInsertValidator();

        Notification notification = validator.validate(rubbleDumpster);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer serialNumber = rubbleDumpster.getSerialNumber();

        if (rubbleDumbsterDAO.findOne(serialNumber).isPresent())
            throw new EntityAlreadyExistsException("Este Serial Number já existe");

        rubbleDumpster.setStatus(RubbleDumpsterStatus.AVAILABLE);

        return rubbleDumbsterDAO.create(rubbleDumpster);
    }

}
