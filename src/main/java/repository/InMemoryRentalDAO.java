package repository;

import model.entities.Client;
import model.entities.Rental;
import model.entities.RentalStatus;
import model.entities.RubbleDumpster;
import persistence.dao.RentalDAO;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryRentalDAO implements RentalDAO {

    private static final Map<Integer, Rental> db = new LinkedHashMap<>();
    private static int idCounter;

    @Override
    public Integer create(Rental rental) {
        idCounter++;
        rental.setId(idCounter);
        db.put(idCounter, rental);
        return idCounter;
    }

    @Override
    public Optional<Rental> findOne(Integer key) {
        if(db.containsKey(key)) {
            return Optional.of(db.get(key));
        }
        return Optional.empty();
    }

    @Override
    public List<Rental> findAll() {
        return List.of();
    }

    @Override
    public List<Rental> findRentalByPeriod(LocalDate initialDate, LocalDate endDate) {
        return List.of();
    }

    @Override
    public List<Rental> findRentalByClient(Client client) {
        return List.of();
    }

    @Override
    public List<Rental> findRentalByRubbleDumpster(RubbleDumpster rubbleDumpster) {
        return List.of();
    }

    @Override
    public List<Rental> findRentalByStatus(RentalStatus status) {
        return List.of();
    }

    @Override
    public boolean update(Rental type) {
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        return false;
    }

    @Override
    public boolean delete(Rental type) {
        return false;
    }
}
