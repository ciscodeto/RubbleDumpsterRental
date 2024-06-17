package dumpRents.model.useCases.client;

import dumpRents.model.Validator;
import dumpRents.model.entities.Client;
import dumpRents.model.entities.valueObjects.Cpf;
import dumpRents.persistence.dao.ClientDAO;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class FindClientUseCase {

    private ClientDAO clientDAO;

    public FindClientUseCase(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public Optional<Client> findOne(Integer id) {
        if (Validator.nullOrEmpty(id))
            throw new IllegalArgumentException("Id cannot be null or empty");
        return clientDAO.findOne(id);
    }

    public Optional<Client> findClientByCpf(Cpf cpf) {
        if (Validator.nullOrEmpty((Collection) cpf))
            throw new IllegalArgumentException("CPF cannot be null or empty");
        return clientDAO.findByCpf(cpf);
    }

    public Optional<Client> findClientByName(String name) {
        if (Validator.nullOrEmpty(name))
            throw new IllegalArgumentException("Name cannot be null or empty");
        return clientDAO.findByName(name);
    }

    public List<Client> findAll() { return clientDAO.findAll(); }

}
