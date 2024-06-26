package com.dumpRents.controller;

import com.dumpRents.model.entities.RubbleDumpster;
import com.dumpRents.model.entities.RubbleDumpsterStatus;
import com.dumpRents.persistence.utils.EntityAlreadyExistsException;
import com.dumpRents.view.WindowLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import static com.dumpRents.main.Main.*;

import javafx.event.ActionEvent;

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

    private void showRubbleDumpsterInMode(UIMode mode) throws IOException {
        RubbleDumpster selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            WindowLoader.setRoot("rubbleDumpsterUI");
            RubbleDumpsterUIController controller = (RubbleDumpsterUIController) WindowLoader.getController();
            controller.setRubbleDumpster(selectedItem, mode);
        }
    }

    public void inactivateRubbleDumpster(ActionEvent actionEvent) {
        RubbleDumpster selectedItem = (RubbleDumpster) tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                inactivateRubbleDumpsterUseCase.inactivate(selectedItem);
            } catch (IllegalArgumentException | EntityAlreadyExistsException e) {
                showAlert("Erro!", "ATENÇÃO!\n" + e.getMessage(), Alert.AlertType.ERROR);
            }
            loadDataAndShow();
        }
    }

    public void activateRubbleDumpster(ActionEvent actionEvent) {
        RubbleDumpster selectedItem = (RubbleDumpster) tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                activateRubbleDumpsterUseCase.activate(selectedItem);
            } catch (IllegalArgumentException | EntityAlreadyExistsException e) {
                showAlert("Erro!", "ATENÇÃO!" + e.getMessage(), Alert.AlertType.ERROR);
            }
            loadDataAndShow();
        }
    }

    public void insertRubbleDumpster(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("rubbleDumpsterUI");
    }

    public void updateRubbleDumbster(ActionEvent actionEvent) throws IOException {
        showRubbleDumpsterInMode(UIMode.UPDATE);
    }

    public void comeBack(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("mainUI");
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

}
