package com.view.rubbledumpsterrental.persistence.dao;

import com.view.rubbledumpsterrental.model.entities.RubbleDumpster;
import com.view.rubbledumpsterrental.model.entities.RubbleDumpsterStatus;
import com.view.rubbledumpsterrental.persistence.utils.DAO;

import java.util.List;

public interface RubbleDumpsterDAO extends DAO<RubbleDumpster, Integer> {
    List<RubbleDumpster> findAll(RubbleDumpsterStatus status);
}


