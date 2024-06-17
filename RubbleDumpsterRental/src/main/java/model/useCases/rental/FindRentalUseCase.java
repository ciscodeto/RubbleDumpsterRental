package com.model.useCases.rental;

import com.model.entities.Client;
import com.model.entities.Rental;
import com.model.entities.RentalStatus;
import com.model.entities.RubbleDumpster;
import com.persistence.dao.RentalDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public Optional<Rental> findOne(Integer rentalId) { return rentalDAO.findOne(rentalId); }
}
