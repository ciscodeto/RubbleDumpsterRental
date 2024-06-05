package model.useCases.client;

import model.Notification;
import model.Validator;
import model.entities.Client;
import model.entities.valueObjects.Cpf;
import persistence.dao.ClientDAO;
import persistence.utils.EntityAlreadyExistsException;

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
