package com.dumpRents.repository;

import com.dumpRents.model.entities.Client;
import com.dumpRents.model.entities.Rental;
import com.dumpRents.model.entities.RentalStatus;
import com.dumpRents.model.entities.RubbleDumpster;
import com.dumpRents.persistence.dao.RentalDAO;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
        return new ArrayList<>(db.values());
    }

    @Override
    public List<Rental> findRentalByPeriod(LocalDate initialDate, LocalDate endDate) {
        return db.values().stream()
                .filter(rental -> !rental.getInitialDate().isBefore(initialDate) && !rental.getInitialDate().isAfter(endDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Rental> findRentalByClient(Client client) {
        return db.values().stream()
                .filter(rental -> rental.getClient().equals(client))
                .collect(Collectors.toList());
    }

    @Override
    public List<Rental> findRentalByRubbleDumpster(RubbleDumpster rubbleDumpster) {
        return db.values().stream()
                .filter(rental -> rental.getRubbleDumpster().equals(rubbleDumpster))
                .collect(Collectors.toList());
    }

    @Override
    public List<Rental> findRentalByStatus(RentalStatus status) {
        return db.values().stream()
                .filter(rental -> rental.getRentalStatus().equals(status))
                .collect(Collectors.toList());
    }

    @Override
    public boolean update(Rental rental) {
        if (db.containsKey(rental.getId())) {
            db.replace(rental.getId(), rental);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        if (db.containsKey(key)) {
            db.remove(key);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Rental rental) {
        return deleteByKey(rental.getId());
    }
}
