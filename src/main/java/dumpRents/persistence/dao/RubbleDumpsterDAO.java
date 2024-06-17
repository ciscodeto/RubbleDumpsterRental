package dumpRents.persistence.dao;

import dumpRents.model.entities.RubbleDumpster;
import dumpRents.model.entities.RubbleDumpsterStatus;
import dumpRents.persistence.utils.DAO;

import java.util.List;
import java.util.Optional;

public interface RubbleDumpsterDAO extends DAO<RubbleDumpster, Integer> {
    List<RubbleDumpster> findAll(RubbleDumpsterStatus status);
    Optional<RubbleDumpster> findById(Integer id);
}


