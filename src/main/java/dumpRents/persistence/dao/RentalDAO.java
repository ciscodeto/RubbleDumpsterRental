package dumpRents.persistence.dao;

import dumpRents.persistence.utils.DAO;
import dumpRents.model.entities.Client;
import dumpRents.model.entities.Rental;
import dumpRents.model.entities.RentalStatus;
import dumpRents.model.entities.RubbleDumpster;

import java.time.LocalDate;
import java.util.List;

public interface RentalDAO extends DAO<Rental, Integer> {
    List<Rental> findRentalByPeriod(LocalDate initialDate, LocalDate endDate);
    List<Rental> findRentalByClient(Client client);
    List<Rental> findRentalByRubbleDumpster(RubbleDumpster rubbleDumpster);
    List<Rental> findRentalByStatus(RentalStatus status);
}
