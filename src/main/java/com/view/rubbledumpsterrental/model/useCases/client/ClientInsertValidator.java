package com.view.rubbledumpsterrental.model.useCases.client;

import com.view.rubbledumpsterrental.model.Notification;
import com.view.rubbledumpsterrental.model.Validator;
import com.view.rubbledumpsterrental.model.entities.Client;

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


        return notification;
    }

}
