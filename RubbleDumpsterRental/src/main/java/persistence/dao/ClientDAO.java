package persistence.dao;

import model.entities.Client;
import model.entities.Rental;
import model.entities.valueObjects.Cpf;
import persistence.utils.DAO;

import java.util.Optional;

public interface ClientDAO extends DAO<Client, Integer> {
    Optional<Client> findByCpf(Cpf cpf);

    Optional<Client> findByName(String name);
}
