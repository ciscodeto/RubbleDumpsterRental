package model.useCases.rental;

import model.entities.Client;
import model.entities.Rental;
import model.entities.RentalStatus;
import model.entities.RubbleDumpster;
import model.exceptions.DataAccessException;
import persistence.dao.RentalDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class FindRentalUseCase {
    private RentalDAO rentalDAO;

    public FindRentalUseCase(RentalDAO rentalDAO) {
        this.rentalDAO = rentalDAO;
    }

    public List<Rental> findRentalByPeriod(LocalDate initialDate, LocalDate endDate) {
        if (initialDate == null || endDate == null) {
            throw new IllegalArgumentException("Initial date and end date must not be null.");
        }
        if (initialDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Initial date must not be after end date.");
        }

        try {
            return rentalDAO.findRentalByPeriod(initialDate, endDate);
        } catch (DataAccessException e) {
            throw new RuntimeException("An error occurred while fetching rentals by period.", e);
        }
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

    public Optional<Rental> findOne(Integer rentalId) { return rentalDAO.findOne(rentalId); }
}
