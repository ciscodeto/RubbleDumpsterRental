package com.dumpRents.persistence.dao;

import com.dumpRents.model.entities.Client;
import com.dumpRents.model.entities.valueObjects.Cpf;
import com.dumpRents.persistence.utils.DAO;

import java.util.Optional;

public interface ClientDAO extends DAO<Client, Integer> {
    Optional<Client> findByCpf(Cpf cpf);

    Optional<Client> findByName(String name);
}
