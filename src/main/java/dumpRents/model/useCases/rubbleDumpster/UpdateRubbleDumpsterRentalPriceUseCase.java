package dumpRents.model.useCases.rubbleDumpster;

import dumpRents.model.Notification;
import dumpRents.model.Validator;
import dumpRents.model.entities.RubbleDumpster;
import dumpRents.persistence.dao.RubbleDumpsterDAO;
import dumpRents.persistence.utils.EntityAlreadyExistsException;

public class UpdateRubbleDumpsterRentalPriceUseCase {

    private RubbleDumpsterDAO rubbleDumpsterDAO;

    public UpdateRubbleDumpsterRentalPriceUseCase(RubbleDumpsterDAO rubbleDumpsterDAO) {
        this.rubbleDumpsterDAO = rubbleDumpsterDAO;
    }

    public boolean update(RubbleDumpster rubbleDumpster, double newMonthlyAmount) {
        Validator<RubbleDumpster> validator = new RubbleDumpsterInsertValidator();

        Notification notification = validator.validate(rubbleDumpster);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer serialNumber = rubbleDumpster.getSerialNumber();

        if (rubbleDumpsterDAO.findOne(serialNumber).isEmpty())
            throw new EntityAlreadyExistsException("Caçamba não localizada.");

        rubbleDumpster.setMonthlyAmount(newMonthlyAmount);

        return rubbleDumpsterDAO.update(rubbleDumpster);
    }
}