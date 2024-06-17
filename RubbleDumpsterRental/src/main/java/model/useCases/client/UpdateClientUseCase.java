package com.model.useCases.client;

import com.model.Notification;
import com.model.Validator;
import com.model.entities.Client;
import com.model.entities.valueObjects.Cpf;
import com.persistence.dao.ClientDAO;
import com.persistence.utils.EntityAlreadyExistsException;

public class UpdateClientUseCase {

    private ClientDAO clientDAO;

    public UpdateClientUseCase(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public boolean updateClient(Client client) {

        Validator<Client> validator = new ClientInsertValidator();
        Notification notification = validator.validate(client);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        Cpf cpf = client.getCpf();
        if (clientDAO.findByCpf(cpf).isEmpty())
            throw new EntityAlreadyExistsException("This CPF isn't in the database");

        return clientDAO.update(client);
    }
}
