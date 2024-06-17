package persistence.dao;

import model.entities.RubbleDumpster;
import model.entities.RubbleDumpsterStatus;
import persistence.utils.DAO;

import java.util.List;
import java.util.Optional;

public interface RubbleDumpsterDAO extends DAO<RubbleDumpster, Integer> {
    List<RubbleDumpster> findAll(RubbleDumpsterStatus status);
    Optional<RubbleDumpster> findById(Integer id);
}


