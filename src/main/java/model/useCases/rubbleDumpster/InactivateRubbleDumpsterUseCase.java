package model.useCases.rubbleDumpster;

import model.Notification;
import model.Validator;
import model.entities.RubbleDumpster;
import model.entities.RubbleDumpsterStatus;
import persistence.dao.RubbleDumpsterDAO;
import persistence.utils.EntityAlreadyExistsException;

import java.time.LocalDate;

import static model.entities.RubbleDumpsterStatus.RENTED;

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

        if (rubbleDumpsterDAO.findOne(serialNumber).isEmpty()
                && rubbleDumpster.getRental().getEndDate().isBefore(LocalDate.now()))
            throw new EntityAlreadyExistsException("Caçamba não localizada ou fora das condições necessárias para inativação!");
        if  (rubbleDumpster.getStatus() != RubbleDumpsterStatus.RENTED)
            throw new IllegalArgumentException("Campo invalido");
        if (rubbleDumpster.getRental().getEndDate().isAfter(LocalDate.now()))
            throw new IllegalArgumentException("A data do término da locação foi atingida!");

        rubbleDumpster.setStatus(RubbleDumpsterStatus.DISABLED);

        return rubbleDumpsterDAO.update(rubbleDumpster);
    }
}

