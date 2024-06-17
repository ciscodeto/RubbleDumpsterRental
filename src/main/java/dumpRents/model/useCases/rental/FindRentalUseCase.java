package dumpRents.model.useCases.rental;

import dumpRents.model.entities.Client;
import dumpRents.model.entities.Rental;
import dumpRents.model.exceptions.DataAccessException;
import dumpRents.persistence.dao.RentalDAO;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class FindRentalUseCase {
    private RentalDAO rentalDAO;

    public FindRentalUseCase(RentalDAO rentalDAO) {
        this.rentalDAO = rentalDAO;
    }

    public Collection<Rental> findRentalByPeriod(LocalDate initialDate, LocalDate endDate) {
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
        if (client == null) {
            throw new IllegalArgumentException("Client must not be null.");
        }
        try {
            return rentalDAO.findRentalByClient(client);
        } catch (DataAccessException e) {
            throw new RuntimeException("An error occurred while fetching rentals by client.", e);
        }
    }

    public List<Rental> findAll() { return rentalDAO.findAll(); }

    public Optional<Rental> findOne(Integer rentalId) { return rentalDAO.findOne(rentalId); }
}
