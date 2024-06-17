package dumpRents.model.useCases.rubbleDumpster;

import dumpRents.model.Validator;
import dumpRents.model.entities.RubbleDumpster;
import dumpRents.model.entities.RubbleDumpsterStatus;
import dumpRents.persistence.dao.RubbleDumpsterDAO;
import dumpRents.persistence.utils.EntityNotFoundException;

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
            throw new IllegalArgumentException("O serial number não pode ser nulo!");

        if (rubbleDumpsterDAO.findOne(serialNumber).isEmpty())
            throw new EntityNotFoundException("Caçamba não localizada.");

        return rubbleDumpsterDAO.findOne(serialNumber);
    }
    public List<RubbleDumpster> findAll (RubbleDumpsterStatus status) {
        if (status == null)
            throw new IllegalArgumentException("Status não pode ser nulo!");

        if (rubbleDumpsterDAO.findAll(status).isEmpty())
            throw new EntityNotFoundException("Nenhum elemento com "+ status.toString() +" foi encontrado!");

        return rubbleDumpsterDAO.findAll(status);
    }
    public List<RubbleDumpster> findAll () {
        return rubbleDumpsterDAO.findAll();
    }

}

