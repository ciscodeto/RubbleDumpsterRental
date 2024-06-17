package dumpRents.model.useCases.client;

import dumpRents.model.Notification;
import dumpRents.model.Validator;
import dumpRents.model.entities.Client;
import dumpRents.model.entities.valueObjects.Cpf;
import dumpRents.persistence.dao.ClientDAO;
import dumpRents.persistence.utils.EntityAlreadyExistsException;

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
