package persistence.dao;

import model.entities.Rental;
import persistence.utils.DAO;
import java.util.Optional;

public interface RentalDAO extends DAO<Rental, Integer> {
    Optional<Rental> findOpenByRubbleDumpsterId(Integer id);
}
