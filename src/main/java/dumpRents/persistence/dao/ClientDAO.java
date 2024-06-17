package dumpRents.persistence.dao;

import dumpRents.model.entities.Client;
import dumpRents.model.entities.valueObjects.Cpf;
import dumpRents.persistence.utils.DAO;

import java.util.Optional;

public interface ClientDAO extends DAO<Client, Integer> {
    Optional<Client> findByCpf(Cpf cpf);

    Optional<Client> findByName(String name);
}
