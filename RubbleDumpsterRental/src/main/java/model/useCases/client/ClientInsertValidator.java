package com.model.useCases.client;

import com.model.Notification;
import com.model.Validator;
import com.model.entities.Client;

public class ClientInsertValidator extends Validator<Client>{

    @Override
    public Notification validate(Client client) {
        Notification notification = new Notification();

        if (client == null)
            notification.addError("Client is null");

        if (nullOrEmpty(client.getName()))
            notification.addError("Client name is null or empty");

        if (!client.getCpf().isValid())
            notification.addError("Client cpf is invalid");

        if (!client.getAddress().isValid()){
            notification.addError("Client address is invalid");
        }

        if (client.getEmailList().isEmpty())
            notification.addError("Client email list is empty");

        if (!client.getPhone1().isValid())
            notification.addError("Client phone is invalid");

        if (!client.getPhone2().isValid())
            notification.addError("Client phone is invalid");

        return notification;
    }

}
