package com.view.rubbledumpsterrental.model.useCases.rubbleDumpster;

import com.view.rubbledumpsterrental.model.Notification;
import com.view.rubbledumpsterrental.model.Validator;
import com.view.rubbledumpsterrental.model.entities.RubbleDumpster;
import com.view.rubbledumpsterrental.model.entities.RubbleDumpsterStatus;
import com.view.rubbledumpsterrental.persistence.dao.RubbleDumpsterDAO;
import com.view.rubbledumpsterrental.persistence.utils.EntityAlreadyExistsException;

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