package com.persistence.dao;

import com.model.entities.RubbleDumpster;
import com.model.entities.RubbleDumpsterStatus;
import com.persistence.utils.DAO;

import java.util.List;

public interface RubbleDumpsterDAO extends DAO<RubbleDumpster, Integer> {
    List<RubbleDumpster> findAll(RubbleDumpsterStatus status);
}


