package com.dumpRents.model.useCases.client;

import com.dumpRents.model.Notification;
import com.dumpRents.model.Validator;
import com.dumpRents.model.entities.Client;
import com.dumpRents.model.entities.valueObjects.Cpf;
import com.dumpRents.persistence.dao.ClientDAO;
import com.dumpRents.persistence.utils.EntityAlreadyExistsException;

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
