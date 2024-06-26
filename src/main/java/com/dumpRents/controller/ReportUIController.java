package com.dumpRents.controller;

import com.dumpRents.model.entities.Report;
import com.dumpRents.view.WindowLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.dumpRents.main.Main.*;

public class ReportUIController {
    @FXML private Button btnBack;
    @FXML private Button btnEntryAndExitReport;
    @FXML private Button btnExport;
    @FXML private Button btnIncomeReport;
    @FXML private TableColumn<Report, String> cClient;
    @FXML private TableColumn<Report, String> cEndDate;
    @FXML private TableColumn<Report, String> cFinalAmount;
    @FXML private TableColumn<Report, String> cInitialDate;
    @FXML private TableColumn<Report, String> cRubbleDumpsterSN;
    @FXML private DatePicker dpEndDate;
    @FXML private DatePicker dpInitialDate;
    @FXML private TableView<Report> tableView;

    private ObservableList<Report> tableData;
    private Enum<ReportType> reportType;

    @FXML
    private void initialize() {
        bindTableColumnsToValueSources();
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    private void bindTableColumnsToValueSources() {
        cRubbleDumpsterSN.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        cClient.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        cInitialDate.setCellValueFactory(new PropertyValueFactory<>("initialDate"));
        cEndDate.setCellValueFactory(new PropertyValueFactory<>("withdrawalDate"));
        cFinalAmount.setCellValueFactory(new PropertyValueFactory<>("finalAmount"));
    }

    public void exportReport(ActionEvent actionEvent) {
        String csvFileName = LocalDate.now() + "_" + reportType.toString() + "_report.csv";

        if (reportType == ReportType.ENTRY_EXIT) {
            String[] headers = {"Serial Number", "Client Name", "Initial Date", "Withdrawal Date", "Final Amount"};
            List<String[]> data = tableData.stream()
                    .map(report -> new String[]{
                            report.serialNumber(),
                            report.clientName() != null ? report.clientName() : "",
                            report.initialDate() != null ? report.initialDate().toString() : "",
                            report.withdrawalDate() != null ? report.withdrawalDate().toString() : "",
                            report.finalAmount() != null ? report.finalAmount().toString() : ""
                    })
                    .toList();
            exportCSVUseCase.export(csvFileName, headers, data);
            showAlert("Success", "Report exported successfully.", Alert.AlertType.INFORMATION);
        }
        if (reportType == ReportType.INCOME) {
            String[] headers = {"Serial Number", "Client Name", "Initial Date", "Withdrawal Date", "Final Amount"};
            List<String[]> data = tableData.stream()
                    .map(report -> new String[]{
                            report.serialNumber(),
                            report.initialDate() != null ? report.initialDate().toString() : "",
                            report.withdrawalDate() != null ? report.withdrawalDate().toString() : "",
                            report.finalAmount() != null ? report.finalAmount().toString() : ""
                    })
                    .toList();
            exportCSVUseCase.export(csvFileName, headers, data);
            showAlert("Success", "Report exported successfully.", Alert.AlertType.INFORMATION);
        }
    }


    public void generateIncomeReport(ActionEvent actionEvent) {
        reportType = ReportType.INCOME;
        LocalDate initialDate = dpInitialDate.getValue();
        LocalDate endDate = dpEndDate.getValue();
        cFinalAmount.setVisible(true);
        cClient.setVisible(false);
        if (initialDate == null || endDate == null) {
            showAlert("Erro!", "As datas inicial e final devem ser preenchidas", Alert.AlertType.ERROR);
            return;
        }
        var report = incomeReportUseCase.generateReport(initialDate, endDate);

        tableData.clear();
        tableData.addAll(report.reports());
    }

    public void generateEntryAndExitReport(ActionEvent actionEvent) {
        reportType = ReportType.ENTRY_EXIT;
        cFinalAmount.setVisible(false);
        cClient.setVisible(true);
        LocalDate initialDate = dpInitialDate.getValue();
        LocalDate endDate = dpEndDate.getValue();
        if (initialDate == null || endDate == null) {
            showAlert("Erro!", "As datas inicial e final devem ser preenchidas", Alert.AlertType.ERROR);
            return;
        }
        var report = entryExitReportUseCase.generateReport(initialDate, endDate);

        tableData.clear();
        tableData.addAll(report.reports());
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
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
