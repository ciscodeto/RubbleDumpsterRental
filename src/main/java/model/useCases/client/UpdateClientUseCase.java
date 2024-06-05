package model.useCases.client;

import model.Notification;
import model.Validator;
import model.entities.Client;
import model.entities.valueObjects.Cpf;
import persistence.dao.ClientDAO;
import persistence.utils.EntityAlreadyExistsException;

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
        System.out.println("UPDATECPF: " + cpf.toString());
        if (clientDAO.findByCpf(cpf).isEmpty())
            throw new EntityAlreadyExistsException("This CPF isn't in the database");

        return clientDAO.update(client);
    }
}
