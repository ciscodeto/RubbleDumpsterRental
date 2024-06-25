package com.dumpRents.controller;

import com.dumpRents.model.entities.Client;
import com.dumpRents.model.entities.Rental;
import com.dumpRents.model.entities.RentalStatus;
import com.dumpRents.model.entities.RubbleDumpster;
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
    @FXML private TableView<Rental> tableView;
    @FXML private TableColumn<Rental, String> cRentalStatus;
    @FXML private TableColumn<Rental, String> cInitialDate;
    @FXML private TableColumn<Rental, String> cClient;
    @FXML private TableColumn<Rental, String> cRubbleDumpsterSN;
    @FXML private TableColumn<Rental, String> cAddress;

    @FXML private TextField txtCpf;
    @FXML private DatePicker dpInitialDate;
    @FXML private DatePicker dpEndDate;
    @FXML private Label lbName;
    @FXML private Button btnRentOr;

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
        cClient.setCellValueFactory(new PropertyValueFactory<>("client"));
        cRubbleDumpsterSN.setCellValueFactory(new PropertyValueFactory<>("rubbleDumpsterSN"));
        cAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void loadDataAndShow() {
        List<Rental> rentals = findRentalUseCase.findAll();
        tableData.clear();
        tableData.addAll(rentals);
    }

    public void goToRubbleDumpsterManagement(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("rubbleDumpsterManagementUI");
    }

    public void goToClientManagement(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("clientManagementUI");
    }

    public void findByClient(ActionEvent actionEvent) {
        Cpf cpf = new Cpf(txtCpf.getText());

        Optional<Client> result = findClientUseCase.findClientByCpf(cpf);
        if (result.isPresent()) {
            client = result.get();
            lbName.setText(client.getName());
        } else {
            client = null;
            lbName.setText("");
            showAlert("Erro!", "Cliente não encontrado", Alert.AlertType.ERROR);
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

    public void endRental(ActionEvent actionEvent) {
        if (selectedRental != null && selectedRental.getRentalStatus() == RentalStatus.OPEN) {
            try {
                endRentalUseCase.endRental(selectedRental.getId());
                loadDataAndShow();
                showAlert("Sucesso", "Locação finalizada com sucesso", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                showAlert("Erro!", e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Erro!", "A locação deve estar em andamento para ser finalizada", Alert.AlertType.ERROR);
        }
    }

    public void rentRubbleDumpster(ActionEvent actionEvent) {
        if (client == null) {
            showAlert("Erro!", "Selecione um cliente para realizar a locação", Alert.AlertType.ERROR);
            return;
        }

        try {
            Rental rental = insertRentalUseCase.insertRental(client.getId());
            loadDataAndShow();
            showAlert("Sucesso", "Caçamba alugada com sucesso", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Erro!", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void goToReportUI(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("reportUI");
    }

    public void setRentalStatus(ActionEvent actionEvent) {
        if (selectedRental != null) {
            try {
                if (selectedRental.getRentalStatus() == RentalStatus.OPEN) {
                    selectedRental.requestWithdrawal(LocalDate.now().plusDays(1));
                } else if (selectedRental.getRentalStatus() == RentalStatus.WITHDRAWAL_ORDER) {
                    endRentalUseCase.endRental(selectedRental.getId());
                }
                loadDataAndShow();
            } catch (Exception e) {
                showAlert("Erro!", e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Erro!", "Selecione uma locação para alterar o status", Alert.AlertType.ERROR);
        }
    }

    public void detailRental(ActionEvent actionEvent) {
        if (selectedRental != null) {
            // Lógica para detalhar a locação
        } else {
            showAlert("Erro!", "Selecione uma locação para detalhar", Alert.AlertType.ERROR);
        }
    }

    public void withdrawal(ActionEvent actionEvent) {
        if (selectedRental != null) {
            try {
                withdrawalRequestUseCase.requestWithdrawal(selectedRental.getId(), LocalDate.now().plusDays(1));
                loadDataAndShow();
                showAlert("Sucesso", "Ordem de retirada criada com sucesso", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                showAlert("Erro!", e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Erro!", "Selecione uma locação para solicitar a retirada", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    @FXML
    private void getSelectedAndSetButton(MouseEvent mouseEvent) {
        selectedRental = tableView.getSelectionModel().getSelectedItem();
        if (selectedRental != null && selectedRental.getRentalStatus() == RentalStatus.WITHDRAWAL_ORDER) {
            btnRentOr.setText(END_RENTAL_TEXT);
        } else {
            btnRentOr.setText(WITHDRAWAL_ORDER_TEXT);
        }
    }
}
