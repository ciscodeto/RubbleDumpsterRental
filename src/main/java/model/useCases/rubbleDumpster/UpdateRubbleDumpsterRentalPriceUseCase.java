package model.useCases.rubbleDumpster;

import model.Notification;
import model.Validator;
import model.entities.RubbleDumpster;
import model.entities.RubbleDumpsterStatus;
import persistence.dao.RubbleDumpsterDAO;
import persistence.utils.EntityAlreadyExistsException;

public class UpdateRubbleDumpsterRentalPriceUseCase {


    private RubbleDumpsterDAO rubbleDumbsterDAO;

    public UpdateRubbleDumpsterRentalPriceUseCase(RubbleDumpsterDAO rubbleDumbsterDAO) {
        this.rubbleDumbsterDAO = rubbleDumbsterDAO;
    }

    public boolean update(RubbleDumpster rubbleDumpster, double newMonthlyAmount) {
        Validator<RubbleDumpster> validator = new RubbleDumbsterInsertValidator();

        Notification notification = validator.validate(rubbleDumpster);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer serialNumber = rubbleDumpster.getSerialNumber();

        if (rubbleDumbsterDAO.findOne(serialNumber).isEmpty())
            throw new EntityAlreadyExistsException("Caçamba não localizada.");

        rubbleDumpster.setMonthlyAmount(newMonthlyAmount);

        return rubbleDumbsterDAO.update(rubbleDumpster);
    }
}