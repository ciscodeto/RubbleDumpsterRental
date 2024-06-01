package com.view.rubbledumpsterrental.model.useCases.rubbleDumpster;

import com.view.rubbledumpsterrental.model.Validator;
import com.view.rubbledumpsterrental.model.entities.RubbleDumpster;
import com.view.rubbledumpsterrental.model.entities.RubbleDumpsterStatus;
import com.view.rubbledumpsterrental.persistence.dao.RubbleDumpsterDAO;
import com.view.rubbledumpsterrental.persistence.utils.EntityAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public class FindRubbleDumpsterUseCase {

    private RubbleDumpsterDAO rubbleDumbsterDAO;

    public FindRubbleDumpsterUseCase(RubbleDumpsterDAO rubbleDumbsterDAO) {
        this.rubbleDumbsterDAO = rubbleDumbsterDAO;
    }

    Validator<RubbleDumpster> validator = new RubbleDumbsterInsertValidator();


    public Optional<RubbleDumpster> findOne (Integer serialNumber) {
        if (serialNumber == null)
            throw new IllegalArgumentException("Serial number cannot be null!");

        if (rubbleDumbsterDAO.findOne(serialNumber).isEmpty())
            throw new EntityAlreadyExistsException("Caçamba não localizada.");

        return rubbleDumbsterDAO.findOne(serialNumber);
    }
    public List<RubbleDumpster> findAll (RubbleDumpsterStatus status) {
        if (status == null)
            throw new IllegalArgumentException("Status cannot be null!");

        if (rubbleDumbsterDAO.findAll(status).isEmpty())
            throw new EntityAlreadyExistsException("Nenhum elemento com este status foi encontrado!");

        return rubbleDumbsterDAO.findAll(status);
    }
    public List<RubbleDumpster> findAll () {
        return rubbleDumbsterDAO.findAll();
    }
    
}

