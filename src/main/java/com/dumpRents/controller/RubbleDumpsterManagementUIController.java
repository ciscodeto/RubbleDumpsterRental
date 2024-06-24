package com.dumpRents.controller;

import com.dumpRents.model.entities.RubbleDumpster;
import com.dumpRents.model.entities.RubbleDumpsterStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.util.List;

import static com.dumpRents.main.Main.findRubbleDumpsterUseCase;
import static com.dumpRents.main.Main.inactivateRubbleDumpsterUseCase;

public class RubbleDumpsterManagementUIController {

    @FXML
    private TableView<RubbleDumpster> tableView;
    @FXML
    private TableColumn<RubbleDumpster, Integer> cSerialNumber;
    @FXML
    private TableColumn<RubbleDumpster, Double> cMinAmount;
    @FXML
    private TableColumn<RubbleDumpster, Double> cMonthlyAmount;
    @FXML
    private TableColumn<RubbleDumpster, RubbleDumpsterStatus> cStatus;

    private ObservableList<RubbleDumpster> tableData;

    @FXML
    public void initialize() {
        bindTableViewToItensList();
        bindColumsToValueSources();
        loadDataAndShow();
        cSerialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));

    }

    private void bindTableViewToItensList() {
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);

    }

    private void bindColumsToValueSources() {
        cSerialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        cMinAmount.setCellValueFactory(new PropertyValueFactory<>("minAmount"));
        cMonthlyAmount.setCellValueFactory(new PropertyValueFactory<>("monthlyAmount"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadDataAndShow() {
        List<RubbleDumpster> rubbleDumpster = findRubbleDumpsterUseCase.findAll();
        tableData.clear();
        tableData.addAll(rubbleDumpster);
    }

    public void inactivateRubbleDumpster(ActionEvent actionEvent) {
        RubbleDumpster selectedItem = (RubbleDumpster) tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            inactivateRubbleDumpsterUseCase.inactivate(selectedItem);
            loadDataAndShow();
        }
    }

    public void insertRubbleDumpster(ActionEvent actionEvent) {
    }

    public void updateRubbleDumbster(ActionEvent actionEvent) {
    }

    public void comeBack(ActionEvent actionEvent) {
    }
}
