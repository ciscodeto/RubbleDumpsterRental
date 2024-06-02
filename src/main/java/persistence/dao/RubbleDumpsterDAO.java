package persistence.dao;

import model.entities.RubbleDumpster;
import model.entities.RubbleDumpsterStatus;
import persistence.utils.DAO;

import java.util.List;

public interface RubbleDumpsterDAO extends DAO<RubbleDumpster, Integer> {
    List<RubbleDumpster> findAll(RubbleDumpsterStatus status);
}


