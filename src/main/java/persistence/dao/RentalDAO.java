package persistence.dao;

import model.entities.*;
import persistence.utils.DAO;

import java.time.LocalDate;
import java.util.List;

public interface RentalDAO extends DAO<Rental, Integer> {
    List<Rental> findRentalByPeriod(LocalDate initialDate, LocalDate endDate);
    List<Rental> findRentalByClient(Client client);
    List<Rental> findRentalByRubbleDumpster(RubbleDumpster rubbleDumpster);
    List<Rental> findRentalByStatus(RentalStatus status);
}
