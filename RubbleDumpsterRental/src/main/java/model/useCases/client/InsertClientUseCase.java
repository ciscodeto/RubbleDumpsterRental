package com.model.useCases.client;

import com.model.Notification;
import com.model.Validator;
import com.model.entities.Client;
import com.model.entities.valueObjects.Cpf;
import com.persistence.dao.ClientDAO;
import com.persistence.utils.EntityAlreadyExistsException;

public class InsertClientUseCase {

    private ClientDAO clientDAO;

    public InsertClientUseCase(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public Integer insert(Client client) {

        Validator<Client> validator = new ClientInsertValidator();
        Notification notification = validator.validate(client);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        Cpf cpf = client.getCpf();
        if (clientDAO.findByCpf(cpf).isPresent())
            throw new EntityAlreadyExistsException("This CPF already exists");

        return clientDAO.create(client);
    }
}
