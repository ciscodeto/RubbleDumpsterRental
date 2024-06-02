package model.useCases.rental;

import model.Validator;
import model.entities.Client;
import model.entities.Rental;
import model.entities.RentalStatus;
import model.entities.RubbleDumpster;
import model.entities.valueObjects.Cpf;
import persistence.dao.RentalDAO;

import java.time.LocalDate;
import java.util.List;

public class FindRentalUseCase {
    private RentalDAO rentalDAO;

    public FindRentalUseCase(RentalDAO rentalDAO) {
        this.rentalDAO = rentalDAO;
    }

    public List<Rental> findRentalByPeriod(LocalDate initialDate, LocalDate endDate) {
        return rentalDAO.findRentalByPeriod(initialDate,endDate);
    }

    public List<Rental> findRentalByClient(Client client) {
        return rentalDAO.findRentalByClient(client);
    }

    public List<Rental> findRentalByRubbleDumpster(RubbleDumpster rubbleDumpster) {
        return rentalDAO.findRentalByRubbleDumpster(rubbleDumpster);
    }

    public List<Rental> findRentalByStatus(RentalStatus status) {
        return rentalDAO.findRentalByStatus(status);
    }

    public List<Rental> findAll() { return rentalDAO.findAll(); }
}
