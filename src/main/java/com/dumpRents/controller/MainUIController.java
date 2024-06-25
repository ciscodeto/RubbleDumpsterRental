package com.dumpRents.controller;

import com.dumpRents.model.entities.Client;
import com.dumpRents.model.entities.Rental;
import com.dumpRents.model.entities.RentalStatus;
import com.dumpRents.model.entities.valueObjects.Cpf;
import com.dumpRents.view.WindowLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.dumpRents.main.Main.*;


public class MainUIController {


    @FXML private Button btnClient;
    @FXML private Button btnDetail;
    @FXML private Button btnEndOrWithdrawal;
    @FXML private Button btnRent;
    @FXML private Button btnReport;
    @FXML private Button btnRubbleDumpster;
    @FXML private Button btnSearchByClient;
    @FXML private Button btnSearchByPeriod;
    @FXML private TableColumn<Rental, String> cAddress;
    @FXML private TableColumn<Rental, String> cClient;
    @FXML private TableColumn<Rental, String> cInitialDate;
    @FXML private TableColumn<Rental, String> cRentalStatus;
    @FXML private TableColumn<Rental, String> cRubbleDumpsterSN;
    @FXML private DatePicker dpEndDate;
    @FXML private DatePicker dpInitialDate;
    @FXML private TableView<Rental> tableView;
    @FXML private TextField txtCpf;

    private ObservableList<Rental> tableData;
    private Client client;
    private Rental selectedRental;

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

    private void bindColumnsToValueSources() {
        cRentalStatus.setCellValueFactory(new PropertyValueFactory<>("rentalStatus"));
        cInitialDate.setCellValueFactory(new PropertyValueFactory<>("initialDate"));
        cClient.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        cRubbleDumpsterSN.setCellValueFactory(new PropertyValueFactory<>("rubbleDumpsterSN"));
        cAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void loadDataAndShow() {
        List<Rental> rentals = findRentalUseCase.findAll();
        tableData.clear();
        tableData.addAll(rentals);
    }

    public void endOrWithdrawal(ActionEvent actionEvent) {
        if(selectedRental != null) {
            if (selectedRental.getRentalStatus() == RentalStatus.WITHDRAWAL_ORDER) {
                endRentalUseCase.endRental(selectedRental.getId());
                System.out.println(selectedRental);
            } else {
                withdrawalRequestUseCase.requestWithdrawal(selectedRental.getId());
            }
        }
        loadDataAndShow();
    }

    public void detailRental(ActionEvent actionEvent) {
    }

    public void addRent(ActionEvent actionEvent) {
    }

    public void findByClient(ActionEvent actionEvent) {
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
            Optional<Client> client = findClientUseCase.findClientByCpf(cpf);
            if (client.isPresent()) {
                List<Rental> rentals = findRentalUseCase.findRentalByClient(client.get());
                tableData.clear();
                tableData.addAll(rentals);
            } else {
                showAlert("Erro!", "Cliente não encontrado", Alert.AlertType.ERROR);
            }
        } catch (IllegalArgumentException e) {
            showAlert("Erro!", "CPF inválido: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void findByPeriod(ActionEvent actionEvent) {
        LocalDate initialDate = dpInitialDate.getValue();
        LocalDate endDate = dpEndDate.getValue();

        if (initialDate == null || endDate == null) {
            showAlert("Erro!", "As datas inicial e final devem ser preenchidas", Alert.AlertType.ERROR);
            return;
        }

        List<Rental> rentals = (List<Rental>) findRentalUseCase.findRentalByPeriod(initialDate, endDate);
        tableData.clear();
        tableData.addAll(rentals);
    }

    public void goToClientManagement(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("clientManagementUI");
    }

    public void goToRubbleDumpsterManagement(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("rubbleDumpsterManagementUI");
    }

    public void goToReportUI(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("reportUI");
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void getSelectedAndSetButton(MouseEvent mouseEvent) {
        selectedRental = tableView.getSelectionModel().getSelectedItem();
        if (selectedRental != null && selectedRental.getRentalStatus() == RentalStatus.WITHDRAWAL_ORDER) {
            btnEndOrWithdrawal.setText(END_RENTAL_TEXT);
        } else {
            btnEndOrWithdrawal.setText(WITHDRAWAL_ORDER_TEXT);
        }
    }
}
