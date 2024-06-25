package com.dumpRents.controller;

import com.dumpRents.model.entities.Client;
import com.dumpRents.model.entities.Rental;
import com.dumpRents.model.entities.RentalStatus;
import com.dumpRents.model.entities.RubbleDumpster;
import com.dumpRents.view.WindowLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class MainUIController {
    @FXML private TableView<Rental> tableView;
    @FXML private TableColumn<Rental, String> cRentalStatus;
    @FXML private TableColumn<Rental, String> cInitialDate;
    @FXML private TableColumn<Rental, String> cClient;
    @FXML private TableColumn<Rental, String> cRubbleDumpsterSN;
    @FXML private TableColumn<Rental, String> cAddress;

    private ObservableList<Rental> tableData;
    private Client client;
    private RubbleDumpster rubbleDumpster;

    private final String WITHDRAWAL_ORDER_TEXT = "Ordem De Retirada";
    private final String END_RENTAL_TEXT = "Finalizar Locação";

    @FXML
    private void initialize() {
        bindTableViewToItemsList();
        bindColumnsToValueSources();
        loadDataAndShow();
    }

    private void bindTableViewToItemsList() {
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    public void goToRubbleDumpsterManagement(ActionEvent actionEvent) {
        WindowLoader.setRoot();
    }

    public void goToClientManagement(ActionEvent actionEvent) {
    }

    private void bindColumnsToValueSources() {
    }

    private void loadDataAndShow() {
    }

    public void findByClient(ActionEvent actionEvent) {
    }

    public void findByPeriod(ActionEvent actionEvent) {
    }

    public void endRental(ActionEvent actionEvent) {
    }

    public void rentRubbleDumpster(ActionEvent actionEvent) {
    }


    public void goToReportUI(ActionEvent actionEvent) {
    }

    public void setRentalStatus(ActionEvent actionEvent) {
    }
}
