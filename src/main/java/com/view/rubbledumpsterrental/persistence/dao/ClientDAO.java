package com.view.rubbledumpsterrental.persistence.dao;

import com.view.rubbledumpsterrental.model.entities.Client;
import com.view.rubbledumpsterrental.model.entities.Rental;
import com.view.rubbledumpsterrental.persistence.utils.DAO;

import java.util.Optional;

public interface ClientDAO extends DAO<Client, Integer> {
    Optional<Client> findById(Integer id);

}
