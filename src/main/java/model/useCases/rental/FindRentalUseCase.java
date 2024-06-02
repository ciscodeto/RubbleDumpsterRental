package com.view.rubbledumpsterrental.model.useCases.rental;

import com.view.rubbledumpsterrental.model.Validator;
import com.view.rubbledumpsterrental.model.entities.Client;
import com.view.rubbledumpsterrental.model.entities.Rental;
import com.view.rubbledumpsterrental.model.entities.RentalStatus;
import com.view.rubbledumpsterrental.model.entities.RubbleDumpster;
import com.view.rubbledumpsterrental.model.entities.valueObjects.Cpf;
import com.view.rubbledumpsterrental.persistence.dao.RentalDAO;

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
