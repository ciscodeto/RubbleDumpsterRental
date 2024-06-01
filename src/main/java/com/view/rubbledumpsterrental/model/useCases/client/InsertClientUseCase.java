package com.view.rubbledumpsterrental.model.useCases.client;

import com.view.rubbledumpsterrental.model.Notification;
import com.view.rubbledumpsterrental.model.Validator;
import com.view.rubbledumpsterrental.model.entities.Client;
import com.view.rubbledumpsterrental.model.entities.RubbleDumpster;
import com.view.rubbledumpsterrental.model.entities.RubbleDumpsterStatus;
import com.view.rubbledumpsterrental.model.entities.valueObjects.Cpf;
import com.view.rubbledumpsterrental.model.useCases.rubbleDumpster.RubbleDumbsterInsertValidator;
import com.view.rubbledumpsterrental.persistence.dao.ClientDAO;
import com.view.rubbledumpsterrental.persistence.dao.RubbleDumpsterDAO;
import com.view.rubbledumpsterrental.persistence.utils.EntityAlreadyExistsException;

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
        if (clientDAO.findOne(cpf).isPresent())
            throw new EntityAlreadyExistsException("This entity already exists");

        client.setStatus(RubbleDumpsterStatus.AVAILABLE);

        return rubbleDumbsterDAO.create(client);
    }
}
