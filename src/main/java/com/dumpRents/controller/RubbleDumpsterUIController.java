package com.dumpRents.controller;

import javafx.event.ActionEvent;

import com.dumpRents.model.entities.Client;
import com.dumpRents.model.entities.RubbleDumpster;
import com.dumpRents.view.WindowLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;
import java.io.IOException;

import static com.dumpRents.main.Main.insertRubbleDumpsterUseCase;
import static com.dumpRents.main.Main.updateRubbleDumpsterRentalPriceUseCase;

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
        getEntityToView();
        if(rubbleDumpster.getId() == null){
            insertRubbleDumpsterUseCase.insert(rubbleDumpster);
        }else {
            updateRubbleDumpsterRentalPriceUseCase.update(rubbleDumpster, Double.valueOf(txtMonthlyAmount.getText()));
        }
        WindowLoader.setRoot("rubbleDumpsterManagementUI");

    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("mainUI");
    }

    public void setRubbleDumpster(RubbleDumpster rubbleDumpster, UIMode mode) {
        if(rubbleDumpster == null){
            throw new IllegalArgumentException("Rubble dumpster cannot be null");
        }
        this.rubbleDumpster = rubbleDumpster;
        setEntityIntoView();

        if (mode == UIMode.VIEW){
            configureViewModel();
        }
    }

    private void getEntityToView(){
        if(rubbleDumpster == null){
            rubbleDumpster = new RubbleDumpster();
        }
        rubbleDumpster.setSerialNumber(Integer.valueOf(txtSerialNumber.getText()));
        rubbleDumpster.setMinAmount(Double.valueOf(txtMinAmount.getText()));
        rubbleDumpster.setMonthlyAmount(Double.valueOf(txtMonthlyAmount.getText()));
    }


    private void setEntityIntoView() {
        txtSerialNumber.setText(rubbleDumpster.getSerialNumber().toString());
        txtMinAmount.setText(String.valueOf(rubbleDumpster.getMinAmount()));
        txtMonthlyAmount.setText(String.valueOf(rubbleDumpster.getMonthlyAmount()));
    }

    private void configureViewModel() {
        btnCancel.setLabel("Fechar");
        btnSave.setVisible(false);
    }
}
