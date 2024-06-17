package com.persistence.dao;

import com.model.entities.Client;
import com.model.entities.Rental;
import com.model.entities.valueObjects.Cpf;
import com.persistence.utils.DAO;

import java.util.Optional;

public interface ClientDAO extends DAO<Client, Integer> {
    Optional<Client> findByCpf(Cpf cpf);

    Optional<Client> findByName(String name);
}
