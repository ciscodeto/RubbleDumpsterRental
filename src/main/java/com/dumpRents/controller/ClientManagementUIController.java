package com.dumpRents.controller;

import com.dumpRents.model.entities.Client;
import com.dumpRents.model.entities.RubbleDumpster;
import com.dumpRents.model.entities.RubbleDumpsterStatus;
import com.dumpRents.model.entities.valueObjects.Address;
import com.dumpRents.model.entities.valueObjects.Cpf;
import com.dumpRents.model.entities.valueObjects.Email;
import com.dumpRents.model.entities.valueObjects.Phone;
import com.dumpRents.view.WindowLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

import static com.dumpRents.main.Main.findClientUseCase;
import static com.dumpRents.main.Main.findRubbleDumpsterUseCase;

public class ClientManagementUIController {

    @FXML
    private TableView<Client> tableView;
    @FXML
    private TableColumn<Client, String> cName;
    @FXML
    private TableColumn<Client, Cpf> cCpf;
    @FXML
    private TableColumn<Client, Phone> cPhone1;
    @FXML
    private TableColumn<Client, Phone> cPhone2;
    @FXML
    private TableColumn<Client, List<Email>> cEmails;
    @FXML
    private TableColumn<Client, Address> cEndereco;

    private ObservableList<Client> tableData;

    @FXML
    public void initialize() {
        bindTableViewToItensList();
        bindColumsToValueSources();
        loadDataAndShow();
    }

    private void bindTableViewToItensList() {
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);

    }

    private void bindColumsToValueSources() {
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        cPhone1.setCellValueFactory(new PropertyValueFactory<>("phone1"));
        cPhone2.setCellValueFactory(new PropertyValueFactory<>("phone2"));
        cEmails.setCellValueFactory(new PropertyValueFactory<>("email"));
        cEndereco.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void loadDataAndShow() {
        List<Client> client = findClientUseCase.findAll();
        tableData.clear();
        tableData.addAll(client);
    }

    private void showClientInMode(UIMode mode) throws IOException {
        Client selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            WindowLoader.setRoot("rubbleDumpsterUI");
            RubbleDumpsterUIController controller = (RubbleDumpsterUIController) WindowLoader.getController();
            controller.setClient(selectedItem, mode);
        }
    }

    public void updateClient(javafx.event.ActionEvent actionEvent) throws IOException {
        showClientInMode(UIMode.UPDATE);
    }


    public void comeBack(javafx.event.ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("clientUI");
    }

    public void addClient(javafx.event.ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("clientUI");
    }

    public void showClient(javafx.event.ActionEvent actionEvent) throws IOException {
        showClientInMode(UIMode.VIEW);
    }

}
