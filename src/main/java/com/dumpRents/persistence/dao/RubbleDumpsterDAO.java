package com.dumpRents.persistence.dao;

import com.dumpRents.model.entities.RubbleDumpster;
import com.dumpRents.model.entities.RubbleDumpsterStatus;
import com.dumpRents.persistence.utils.DAO;

import java.util.List;
import java.util.Optional;

public interface RubbleDumpsterDAO extends DAO<RubbleDumpster, Integer> {
    List<RubbleDumpster> findAll(RubbleDumpsterStatus status);
    Optional<RubbleDumpster> findById(Integer id);
}


