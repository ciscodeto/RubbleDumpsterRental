package com.dumpRents.persistence.dao;

import com.dumpRents.persistence.utils.DAO;
import com.dumpRents.model.entities.Client;
import com.dumpRents.model.entities.Rental;
import com.dumpRents.model.entities.RentalStatus;
import com.dumpRents.model.entities.RubbleDumpster;

import java.time.LocalDate;
import java.util.List;

public interface RentalDAO extends DAO<Rental, Integer> {
    List<Rental> findRentalByPeriod(LocalDate initialDate, LocalDate endDate);
    List<Rental> findRentalByClient(Client client);
    List<Rental> findRentalByRubbleDumpster(RubbleDumpster rubbleDumpster);
    List<Rental> findRentalByStatus(RentalStatus status);
}
