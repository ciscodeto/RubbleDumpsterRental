package com.view.rubbledumpsterrental.model.useCases.client;

import com.view.rubbledumpsterrental.model.Notification;
import com.view.rubbledumpsterrental.model.Validator;
import com.view.rubbledumpsterrental.model.entities.Client;
import com.view.rubbledumpsterrental.model.entities.valueObjects.Cpf;
import com.view.rubbledumpsterrental.persistence.dao.ClientDAO;
import com.view.rubbledumpsterrental.persistence.utils.EntityAlreadyExistsException;

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
