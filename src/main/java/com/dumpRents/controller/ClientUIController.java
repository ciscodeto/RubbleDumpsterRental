package com.dumpRents.controller;

import com.dumpRents.model.entities.Client;
import com.dumpRents.model.entities.RubbleDumpster;
import com.dumpRents.model.entities.valueObjects.*;
import com.dumpRents.persistence.utils.EntityAlreadyExistsException;
import com.dumpRents.view.WindowLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
        getEntityFromView();

        if (client.getId() == null) {
            try {
                insertClientUseCase.insert(client);
            } catch (EntityAlreadyExistsException e) {
                e.printStackTrace();
                showAlert("ERRO!", "ATENÇÃO!\n" + e.getMessage(), Alert.AlertType.ERROR);
                return;
            }
        }
        else {
            try {
                updateClientUseCase.updateClient(client);
            } catch (IllegalArgumentException e) {
                showAlert("ERRO!", "ATENÇÃO!\n" + e.getMessage(), Alert.AlertType.ERROR);
                return;
            }

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


        if (mode == UIMode.VIEW){
            configureViewModel();
        }
        if (mode == UIMode.UPDATE){
            txtCpf.setDisable(true);
        }
    }

    private void getEntityFromView(){
        if(client == null){
            client = new Client();
        }

        client.setName(txtName.getText());

        try {
            client.setCpf(new Cpf(txtCpf.getText()));
        } catch (IllegalArgumentException e) {
            showAlert("ERRO!", "ATENÇÃO!\n" + e.getMessage(), Alert.AlertType.ERROR);
            return;
        }

        try {
            client.setPhone1(new Phone(txtTelephone1.getText()));
        } catch (IllegalArgumentException e) {
            showAlert("ERRO!", "ATENÇÃO!\n" + e.getMessage(), Alert.AlertType.ERROR);
            return;
        }

        try {
            client.setPhone2(new Phone(txtTelephone2.getText()));
        } catch (IllegalArgumentException e) {
            showAlert("ERRO!", "ATENÇÃO!\n" + e.getMessage(), Alert.AlertType.ERROR);
            return;
        }

        try {
            String emailsText = txtEmails.getText();
            String[] emailsArray = emailsText.split("[ ,;]+");
            List<Email> emails = new ArrayList<>();
            for (String email : emailsArray) {
                emails.add(new Email(email));
            }
            client.setEmailList(emails);
        } catch (IllegalArgumentException e) {
            showAlert("ERRO!", "ATENÇÃO!\n" + e.getMessage(), Alert.AlertType.ERROR);
            return;
        }


        try {
            Address address = new Address(
                    txtStreet.getText(),
                    txtDistrict.getText(),
                    txtNumber.getText(),
                    txtCity.getText(),
                    new Cep(txtCep.getText())
            );
            client.setAddress(address);
        } catch (IllegalArgumentException e) {
            showAlert("ERRO!", "ATENÇÃO!\n" + e.getMessage(), Alert.AlertType.ERROR);
            return;
        }
    }

    private void setEntityIntoView(){
        txtName.setText(client.getName());
        txtCpf.setText(client.getCpf().toString());
        txtTelephone1.setText(client.getPhone1().toString());
        txtTelephone2.setText(client.getPhone2().toString());

        String emailsWithoutBrackets = client.getEmailList().toString().replace("[", "").replace("]", "");
        txtEmails.setText(emailsWithoutBrackets);

        txtStreet.setText(client.getAddress().getStreet());
        txtDistrict.setText(client.getAddress().getDistrict());
        txtNumber.setText(client.getAddress().getNumber());
        txtCity.setText(client.getAddress().getCity());
        txtCep.setText(client.getAddress().getCep().toString());
    }


    private void configureViewModel(){
        btnCancel.setText("Fechar");
        btnSaveOrUpdate.setVisible(false);

        txtName.setDisable(true);
        txtCpf.setDisable(true);
        txtTelephone1.setDisable(true);
        txtTelephone2.setDisable(true);
        txtEmails.setDisable(true);
        txtStreet.setDisable(true);
        txtDistrict.setDisable(true);
        txtNumber.setDisable(true);
        txtCity.setDisable(true);
        txtCep.setDisable(true);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
