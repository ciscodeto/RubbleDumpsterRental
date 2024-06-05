package model.useCases.rubbleDumpster;

import model.Validator;
import model.entities.RubbleDumpster;
import model.entities.RubbleDumpsterStatus;
import persistence.dao.RubbleDumpsterDAO;
import persistence.utils.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public class FindRubbleDumpsterUseCase {

    private RubbleDumpsterDAO rubbleDumpsterDAO;

    public FindRubbleDumpsterUseCase(RubbleDumpsterDAO rubbleDumpsterDAO) {
        this.rubbleDumpsterDAO = rubbleDumpsterDAO;
    }

    Validator<RubbleDumpster> validator = new RubbleDumpsterInsertValidator();


    public Optional<RubbleDumpster> findOne (Integer serialNumber) {
        if (serialNumber == null)
            throw new IllegalArgumentException("Serial number cannot be null!");

        if (rubbleDumpsterDAO.findOne(serialNumber).isEmpty())
            throw new EntityNotFoundException("Rubble Dumpster not found.");

        return rubbleDumpsterDAO.findOne(serialNumber);
    }

    public List<RubbleDumpster> findAll (RubbleDumpsterStatus status) {
        if (status == null)
            throw new IllegalArgumentException("Status cannot be null!");

        if (rubbleDumpsterDAO.findAll(status).isEmpty())
            throw new EntityNotFoundException("No such element has been found!");

        return rubbleDumpsterDAO.findAll(status);
    }
    public List<RubbleDumpster> findAll () {
        return rubbleDumpsterDAO.findAll();
    }

    public RubbleDumpster findAvailableUnit() {

        List<RubbleDumpster> availableDumpsters = rubbleDumpsterDAO.findAll(RubbleDumpsterStatus.AVAILABLE);

        if (availableDumpsters.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma caçamba disponível foi encontrada!");
        }

        return availableDumpsters.getFirst();
    }
}

