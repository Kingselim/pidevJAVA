package com.esprit.controllers;

import com.esprit.models.Sponsoring;
import com.esprit.services.SponsoringService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;


public class ModifierSponsoring {

    @FXML
    private TextField budget;

    @FXML
    private DatePicker dateSponsoring;

    @FXML
    private TextField type;
    private Sponsoring sponsoring; // The Sponsoring object to edit
    private AfficherSponsoring AfficherSponsoring; // Reference to the parent controller

    // Initialize method to set the fields with existing data
    public void initData(Sponsoring sponsoring, AfficherSponsoring AfficherSponsoring) {
        this.sponsoring = sponsoring;
        this.AfficherSponsoring = AfficherSponsoring;

        budget.setText(String.valueOf(sponsoring.getBudget()));
        dateSponsoring.setValue(sponsoring.getDatesponsoring().toLocalDate());
        type.setText(sponsoring.getType());
    }

    @FXML
    void update(ActionEvent event) throws SQLException {
        // Validate input fields
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

        // Get the selected date from the DatePicker
        selectedDate = dateSponsoring.getValue();
        Date dates = Date.valueOf(selectedDate);

        // Create a new Sponsoring object with updated values
        Sponsoring updatedSponsoring = new Sponsoring(sponsoring.getId(), Double.valueOf(budget.getText()), dates, type.getText());

        // Call the service to update the existing Sponsoring object
        SponsoringService ss = new SponsoringService();
        ss.modifier(updatedSponsoring); // Assuming you have an update method in your SponsoringService

        AfficherSponsoring.refreshGrid();

        //notification
        TrayNotification tray = new TrayNotification();
        tray.setTitle("Notification");
        tray.setMessage("Sponsoring modifiee avec success");
        tray.setNotificationType(NotificationType.INFORMATION);
        tray.showAndWait();

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


    public void retour(ActionEvent actionEvent) {
        try {
            // Fermeture de la fenêtre actuelle
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherSponsoring.fxml"));
            Parent root = loader.load();
            // Création de la scène
            Scene scene = new Scene(root);
            // Création de la nouvelle fenêtre pour afficher la vue AffcherCompte.fxml
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Afficher sponsoring");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gouser(ActionEvent actionEvent) {
    }

    public void goassociation(ActionEvent actionEvent) {
    }


    public void gocompte(ActionEvent actionEvent) {
    }


    public void gopret(ActionEvent actionEvent) {
    }


    public void goseminaire(ActionEvent actionEvent) {
    }


    public void gosponsoring(ActionEvent actionEvent) {
    }


    public void logout(ActionEvent actionEvent) {
    }


}
