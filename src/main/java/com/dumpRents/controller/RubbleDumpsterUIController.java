package com.dumpRents.controller;

import com.dumpRents.model.entities.RubbleDumpsterStatus;
import com.dumpRents.persistence.utils.EntityAlreadyExistsException;
import javafx.event.ActionEvent;

import com.dumpRents.model.entities.Client;
import com.dumpRents.model.entities.RubbleDumpster;
import com.dumpRents.view.WindowLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

import static com.dumpRents.main.Main.*;

public class RubbleDumpsterUIController {

    @FXML
    private TextField txtSerialNumber;
    @FXML
    private TextField txtMinAmount;
    @FXML
    private TextField txtMonthlyAmount;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    private RubbleDumpster rubbleDumpster;


    public void saveOrUpdate(ActionEvent actionEvent) throws IOException {
        getEntityFromView();
        if(rubbleDumpster.getId() == null){
            try {
                insertRubbleDumpsterUseCase.insert(rubbleDumpster);
            } catch (EntityAlreadyExistsException e) {
                showAlert("Erro!", "ATENÇÃO!" + e.getMessage(), Alert.AlertType.ERROR);
                return;
            }
        }else {
            updateRubbleDumpsterRentalPriceUseCase.update(rubbleDumpster, Double.valueOf(txtMonthlyAmount.getText()));
        }
        WindowLoader.setRoot("rubbleDumpsterManagementUI");
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("rubbleDumpsterManagementUI");
    }

    public void setRubbleDumpster(RubbleDumpster rubbleDumpster, UIMode mode) {
        if(rubbleDumpster == null){
            showAlert("Erro!", "ATENÇÃO!" + "A caçamba não pode ser nula", Alert.AlertType.ERROR);
        }
        this.rubbleDumpster = rubbleDumpster;
        setEntityIntoView();

        if (mode == UIMode.UPDATE){
            configureViewModel();
        }
    }

    private void getEntityFromView(){
        if(rubbleDumpster == null){
            rubbleDumpster = new RubbleDumpster(
                    Integer.valueOf(txtSerialNumber.getText()),
                    Double.valueOf(txtMinAmount.getText()),
                    Double.valueOf(txtMonthlyAmount.getText()),
                    RubbleDumpsterStatus.AVAILABLE
            );
        }
    }


    private void setEntityIntoView() {
        txtSerialNumber.setText(rubbleDumpster.getSerialNumber().toString());
        txtMinAmount.setText(String.valueOf(rubbleDumpster.getMinAmount()));
        txtMonthlyAmount.setText(String.valueOf(rubbleDumpster.getMonthlyAmount()));
    }

    private void configureViewModel() {
        btnSave.setText("Editar");
        txtSerialNumber.setDisable(true);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
