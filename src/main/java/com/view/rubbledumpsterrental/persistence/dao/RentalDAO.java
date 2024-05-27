package com.view.rubbledumpsterrental.persistence.dao;

import com.view.rubbledumpsterrental.model.entities.Rental;
import com.view.rubbledumpsterrental.persistence.utils.DAO;
import java.util.Optional;

public interface RentalDAO extends DAO<Rental, Integer> {
    Optional<Rental> findOpenByRubbleDumpsterId(Integer id);
}
