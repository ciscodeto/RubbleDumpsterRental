package com.dumpRents.controller;

import com.dumpRents.model.entities.Client;
import com.dumpRents.model.entities.RubbleDumpster;
import com.dumpRents.model.entities.valueObjects.*;
import com.dumpRents.view.WindowLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.dumpRents.main.Main.*;

public class ClientUIController {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCpf;
    @FXML
    private TextField txtTelephone1;
    @FXML
    private TextField txtTelephone2;
    @FXML
    private TextArea txtEmails;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtStreet;
    @FXML
    private TextField txtNumber;
    @FXML
    private TextField txtDistrict;
    @FXML
    private TextField txtCep;
    @FXML
    private Button btnSaveOrUpdate;
    @FXML
    private Button btnCancel;

    private Client client;

    @FXML
    private void initialize() {

    }

    public void saveOrUpdate(ActionEvent actionEvent) throws IOException {
        getEntityToView();
        boolean newClient = findClientUseCase.findClientByCpf(client.getCpf()).isEmpty();

        if (newClient) {
            insertClientUseCase.insert(client);
        }
        else {
            updateClientUseCase.updateClient(client);
        }
        WindowLoader.setRoot("clientManagementUI");

    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("clientManagementUI");
    }

    public void setClient(Client client, UIMode mode) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }

        this.client = client;
        setEntityIntoView();

        txtCep.setEditable(false);

        if (mode == UIMode.VIEW){
            configureViewModel();
        }
        if (mode == UIMode.UPDATE){
            txtCpf.setEditable(false);
        }
    }

    private void getEntityToView(){
        if(client == null){
            client = new Client();
        }
        client.setName(txtName.getText());
        client.setCpf(new Cpf(txtCpf.getText()));
        client.setPhone1(new Phone(txtTelephone1.getText()));
        client.setPhone2(new Phone(txtTelephone2.getText()));

        String emailsText = txtEmails.getText();
        String[] emailsArray = emailsText.split("[ ,;]+");
        List<Email> emails = new ArrayList<>();
        for (String email : emailsArray) {
            emails.add(new Email(email));
        }
        client.setEmailList(emails);

        Address address = new Address(
                txtStreet.getText(),
                txtDistrict.getText(),
                txtNumber.getText(),
                txtCity.getText(),
                new Cep(txtCep.getText())
        );

        client.setAddress(address);
    }

    private void setEntityIntoView(){
        txtName.setText(client.getName());
        txtCpf.setText(client.getCpf().toString());
        txtTelephone1.setText(client.getPhone1().toString());
        txtTelephone2.setText(client.getPhone2().toString());
        txtEmails.setText(client.getEmailList().toString());
        txtStreet.setText(client.getAddress().getStreet());
        txtDistrict.setText(client.getAddress().getDistrict());
        txtNumber.setText(client.getAddress().getNumber());
        txtCity.setText(client.getAddress().getCity());
        txtCep.setText(client.getAddress().getCep().toString());
    }

    private void configureViewModel(){
        btnCancel.setText("Fechar");
        btnSaveOrUpdate.setVisible(false);

        txtName.setEditable(false);
        txtCpf.setEditable(false);
        txtTelephone1.setEditable(false);
        txtTelephone2.setEditable(false);
        txtEmails.setEditable(false);
        txtStreet.setEditable(false);
        txtDistrict.setEditable(false);
        txtNumber.setEditable(false);
        txtCity.setEditable(false);
        txtCep.setEditable(false);
    }
}
