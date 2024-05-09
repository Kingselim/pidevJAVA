package com.esprit.controllers;

import com.esprit.models.Sponsoring;
import com.esprit.services.SponsoringService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AjouterSponsoring implements Initializable {

    @FXML
    private TextField budget;

    @FXML
    private DatePicker dateSponsoring;

    @FXML
    private TextField type;

    @FXML
    void ajouterSponsoringAction(ActionEvent event) {
        // Validate budget
        if (!isValidBudget(budget.getText())) {
            showAlert("Budget must be a positive number.");
            return; // Exit method if budget is invalid
        }

        // Validate date
        LocalDate selectedDate = dateSponsoring.getValue();
        if (selectedDate == null || selectedDate.isBefore(LocalDate.now())) {
            showAlert("Please select a valid date for sponsoring.");
            return; // Exit method if date is invalid
        }

        // Validate type
        if (!isValidType(type.getText())) {
            showAlert("Type must be a string with maximum 15 characters.");
            return; // Exit method if type is invalid
        }

        // If all fields are valid, proceed with adding the sponsoring
        Date dates = Date.valueOf(selectedDate);
        SponsoringService ss = new SponsoringService();
        Sponsoring s = new Sponsoring(Double.valueOf(budget.getText()), dates, type.getText());
        ss.ajouter(s);
        showSuccess("Sponsoring added successfully!");
        TrayNotification tray = new TrayNotification();
        tray.setTitle("Notification");
        tray.setMessage("Sponsoring ajoutee avec success");
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndWait();
    }

//    @FXML
//    void ajouterSponsoringAction(ActionEvent event) {
//        if (validateInputs()) {
//            LocalDate selectedDate = dateSponsoring.getValue();
//            Date dates = Date.valueOf(selectedDate);
//            SponsoringService ss = new SponsoringService();
//            Sponsoring s = new Sponsoring(Double.valueOf(budget.getText()), dates, type.getText());
//            ss.ajouter(s);
//            showSuccess("Sponsoring added successfully!");
//        }
//    }



    private boolean validateInputs() {
        return false;
    }

    private void addValidators() {
        // Budget validator
        budget.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidBudget(newValue)) {
                budget.setStyle("-fx-border-color: red;");
            } else {
                budget.setStyle("-fx-border-color: none;");
            }
        });

        // Date picker validator
        dateSponsoring.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isBefore(LocalDate.now())) {
                dateSponsoring.setStyle("-fx-border-color: red;");
            } else {
                dateSponsoring.setStyle("-fx-border-color: none;");
            }
        });

        // Type validator
        type.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidType(newValue)) {
                type.setStyle("-fx-border-color: red;");
            } else {
                type.setStyle("-fx-border-color: none;");
            }
        });
    }

    private boolean isValidBudget(String value) {
        try {
            double amount = Double.parseDouble(value);
            return amount > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidType(String value) {
        return value.matches("[a-zA-Z\\s]{1,15}");
    }

    public void saveSponsoring() {
        // Check if all fields are valid
        if (isValidBudget(budget.getText()) && dateSponsoring.getValue() != null && isValidType(type.getText())) {
            // Perform saving operation
            showSuccess("Sponsoring added successfully!");
        } else {
            showAlert("Please fill in all fields correctly.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Avertissement");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addValidators();
        dateSponsoring.setValue(LocalDate.now());
    }


    public void retour(ActionEvent actionEvent) {

    }
}
