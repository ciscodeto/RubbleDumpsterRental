package dumpRents.model.useCases.client;

import dumpRents.model.Notification;
import dumpRents.model.Validator;
import dumpRents.model.entities.Client;
import dumpRents.model.entities.valueObjects.Cpf;
import dumpRents.persistence.dao.ClientDAO;
import dumpRents.persistence.utils.EntityAlreadyExistsException;

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
