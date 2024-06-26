package com.dumpRents.controller;

import com.dumpRents.model.entities.Client;
import com.dumpRents.model.entities.Rental;
import com.dumpRents.model.entities.valueObjects.*;
import com.dumpRents.model.useCases.rental.InsertRentalUseCase;
import com.dumpRents.view.WindowLoader;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.dumpRents.main.Main.*;


public class RentalUIController {
    @FXML private Button btnCancel;
    @FXML private Button btnFindClient;
    @FXML private Button btnSave;
    @FXML private Label lbClientName;
    @FXML private Label lbDumpsterSerialNumber;
    @FXML private TextField txtCity;
    @FXML private TextField txtCpf;
    @FXML private TextField txtDistrict;
    @FXML private TextField txtNumber;
    @FXML private TextField txtStreet;
    @FXML private TextField txtCep;

    private ObservableList<Rental> tableData;
    private Client client;
    private Rental rental;
    private Address address;

    private void getEntityFromView() {
        if(rental == null){
            rental = new Rental();
        }
        address = new Address(
                txtStreet.getText(),
                txtDistrict.getText(),
                txtNumber.getText(),
                txtCity.getText(),
                new Cep(txtCep.getText())
        );
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("mainUI");
    }

    public void saveOrUpdate(ActionEvent actionEvent) {
        insertRentalUseCase.insertRental(rental.getClient().getId(),address);
    }

    public void setRental(Rental selectedItem, UIMode mode) {
    }

    public void findClient(ActionEvent actionEvent) {
        String cpfText = txtCpf.getText();

        if (cpfText == null || cpfText.isEmpty()) {
            showAlert("Erro!", "CPF do cliente não pode ser vazio", Alert.AlertType.ERROR);
            return;
        }

        try {
            Cpf cpf = new Cpf(cpfText);
            if (!cpf.isValid()) {
                showAlert("Erro!", "CPF inválido", Alert.AlertType.ERROR);
                return;
            }
            Optional<Client> newClient = findClientUseCase.findClientByCpf(cpf);
            if (newClient.isPresent()) {
                this.client = newClient.get();
            } else {
                showAlert("Erro!", "Cliente não encontrado", Alert.AlertType.ERROR);
            }
        } catch (IllegalArgumentException e) {
            showAlert("Erro!", "CPF inválido: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
