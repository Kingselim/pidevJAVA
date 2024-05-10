package com.esprit.controllers;

import com.esprit.models.DemandeSponsoring;
import com.esprit.models.Sponsoring;
import com.esprit.services.DemandeSponsoringService;
import com.esprit.services.SponsoringService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ModifierDemandeSponsoring implements Initializable {

    @FXML
    private DatePicker dateDebutPicker;

    @FXML
    private DatePicker dateFinPicker;

    @FXML
    private TextField budgetField;

    @FXML
    private TextField userIdField;

    @FXML
    private TextField sponsoringIdField;

    @FXML
    private ComboBox<String> typecombo;

    @FXML
    private TextField nomAssociationField;

    private final DemandeSponsoringService demandeSponsoringService = new DemandeSponsoringService();
    private final SponsoringService sponsoringService = new SponsoringService();

    private AfficherDemandeSponsoring afficherDemandeSponsoring; // Reference to the parent controller


    private DemandeSponsoring demandeSponsoring = new DemandeSponsoring();


    public void initData(DemandeSponsoring demandeSponsoring,AfficherDemandeSponsoring afficherDemandeSponsoring) {
        budgetField.setText(String.valueOf(demandeSponsoring.getBudget()));
        dateDebutPicker.setValue(demandeSponsoring.getDatedebut().toLocalDate());
        dateFinPicker.setValue(demandeSponsoring.getDatefin().toLocalDate());
        userIdField.setText(String.valueOf(demandeSponsoring.getId_user()));
        sponsoringIdField.setText(String.valueOf(demandeSponsoring.getId_sponsoring()));
        nomAssociationField.setText(demandeSponsoring.getNomassociation());

        // Populate the ComboBox with all sponsoring types
        populateComboBox();
        typecombo.getSelectionModel().select(demandeSponsoring.getSponsoring().getType());
        this.afficherDemandeSponsoring = afficherDemandeSponsoring;
    this.demandeSponsoring = demandeSponsoring;
    }



    @FXML
    void ModifierDemande(ActionEvent event) throws SQLException {
        if (validateInputs()) {
            LocalDate selectedDateDebut = dateDebutPicker.getValue();
            LocalDate selectedDateFin = dateFinPicker.getValue();
            Date dateDebut = Date.valueOf(selectedDateDebut);
            Date dateFin = Date.valueOf(selectedDateFin);
            int idsponsoring = getSelectedTypeID();
            DemandeSponsoring updatedDemandeSponsoring = new DemandeSponsoring(
                     demandeSponsoring.getId(),
                    Double.parseDouble(budgetField.getText()),
                    dateDebut,
                    dateFin,
                    idsponsoring,
                    Integer.parseInt(userIdField.getText()),
                    nomAssociationField.getText(),
                    sponsoringIdField.getText());

            demandeSponsoringService.update(updatedDemandeSponsoring);
            try {
                this.afficherDemandeSponsoring.refreshGrid();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            TrayNotification tray = new TrayNotification();
            tray.setTitle("Notification");
            tray.setMessage("Votre demande a ete modifiee");
            tray.setNotificationType(NotificationType.INFORMATION);
            tray.showAndWait();
        }

    }
    private int getSelectedTypeID() {
        String selectedType = typecombo.getValue();
        if (selectedType != null && typeIDMap.containsKey(selectedType)) {
            return typeIDMap.get(selectedType);
        } else {
            return -1; // Return -1 if no type is selected or if the type is not found in the map
        }
    }
    private boolean validateInputs() {
        boolean isValid = true;

        // Validate budget
        if (!isValidBudget(budgetField.getText())) {
            budgetField.setStyle("-fx-border-color: red;");
            isValid = false;
        }

        // Validate date picker
        LocalDate startDate = dateDebutPicker.getValue();
        LocalDate endDate = dateFinPicker.getValue();
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            dateDebutPicker.setStyle("-fx-border-color: red;");
            dateFinPicker.setStyle("-fx-border-color: red;");
            isValid = false;
        }

        // Validate user ID and sponsoring ID (assuming they should be integers)
        if (!isValidInteger(userIdField.getText()) || !isValidInteger(sponsoringIdField.getText())) {
            userIdField.setStyle("-fx-border-color: red;");
            sponsoringIdField.setStyle("-fx-border-color: red;");
            isValid = false;
        }

        // Validate nom association
        if (!isValidNomAssociation(nomAssociationField.getText())) {
            nomAssociationField.setStyle("-fx-border-color: red;");
            isValid = false;
        }

        return isValid;
    }

    private boolean isValidBudget(String value) {
        try {
            double budget = Double.parseDouble(value);
            return budget > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidNomAssociation(String value) {
        return value.length() < 20;
    }
    private Map<String, Integer> typeIDMap = new HashMap<>();
    private void populateComboBox() {
        typecombo.getItems().clear();
        typeIDMap.clear(); // Clear the map

        // Get the list of sponsorings from the service
        List<Sponsoring> sponsorings = sponsoringService.afficher();

        // Add types to the ComboBox and populate the type-ID map
        for (Sponsoring sponsoring : sponsorings) {
            typecombo.getItems().add(sponsoring.getType());
            typeIDMap.put(sponsoring.getType(), sponsoring.getId());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sponsoringIdField.setVisible(false);
        addValidators();
    }

    private void addValidators() {
        // Budget validator
        budgetField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidBudget(newValue)) {
                budgetField.setStyle("-fx-border-color: red;");
            } else {
                budgetField.setStyle("-fx-border-color: none;");
            }
        });

        // Date picker validators
        dateDebutPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && dateFinPicker.getValue() != null && newValue.isAfter(dateFinPicker.getValue())) {
                dateDebutPicker.setStyle("-fx-border-color: red;");
            } else {
                dateDebutPicker.setStyle("-fx-border-color: none;");
            }
        });

        dateFinPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && dateDebutPicker.getValue() != null && newValue.isBefore(dateDebutPicker.getValue())) {
                dateFinPicker.setStyle("-fx-border-color: red;");
            } else {
                dateFinPicker.setStyle("-fx-border-color: none;");
            }
        });

        // Nom association validator
        nomAssociationField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidNomAssociation(newValue)) {
                nomAssociationField.setStyle("-fx-border-color: red;");
            } else {
                nomAssociationField.setStyle("-fx-border-color: none;");
            }
        });
    }

    public void retour(ActionEvent actionEvent) {
        try {
            // Fermeture de la fenêtre actuelle
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherDemandeSponsoring.fxml"));
            Parent root = loader.load();
            // Création de la scène
            Scene scene = new Scene(root);
            // Création de la nouvelle fenêtre pour afficher la vue AffcherCompte.fxml
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Afficher demande sponsoring");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void gouser(ActionEvent actionEvent) {
    }

    public void gocompte(ActionEvent actionEvent) {
    }

    public void gopret(ActionEvent actionEvent) {
    }

    public void goseminair(ActionEvent actionEvent) {
    }

    public void goassociation(ActionEvent actionEvent) {
    }
}
