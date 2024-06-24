package com.dumpRents.model.useCases.rubbleDumpster;

import com.dumpRents.model.Notification;
import com.dumpRents.model.Validator;
import com.dumpRents.model.entities.RubbleDumpster;
import com.dumpRents.persistence.dao.RubbleDumpsterDAO;
import com.dumpRents.persistence.utils.EntityAlreadyExistsException;

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

        rubbleDumpster.UpdateRentalPrice(newMonthlyAmount);

        return rubbleDumpsterDAO.update(rubbleDumpster);
    }
}