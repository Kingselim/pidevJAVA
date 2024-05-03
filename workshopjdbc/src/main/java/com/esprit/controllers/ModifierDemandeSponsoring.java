package com.esprit.controllers;

import com.esprit.models.DemandeSponsoring;
import com.esprit.models.Sponsoring;
import com.esprit.services.DemandeSponsoringService;
import com.esprit.services.SponsoringService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

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





    public void initData(DemandeSponsoring demandeSponsoring) {
        budgetField.setText(String.valueOf(demandeSponsoring.getBudget()));
        dateDebutPicker.setValue(demandeSponsoring.getDatedebut().toLocalDate());
        dateFinPicker.setValue(demandeSponsoring.getDatefin().toLocalDate());
        userIdField.setText(String.valueOf(demandeSponsoring.getId_user()));
        sponsoringIdField.setText(String.valueOf(demandeSponsoring.getId_sponsoring()));
        nomAssociationField.setText(demandeSponsoring.getNomassociation());

        // Populate the ComboBox with all sponsoring types
        populateComboBox();
        typecombo.getSelectionModel().select(demandeSponsoring.getSponsoring().getType());
    }



    @FXML
    void ModifierDemande(ActionEvent event) throws SQLException {
        if (validateInputs()) {
            LocalDate selectedDateDebut = dateDebutPicker.getValue();
            LocalDate selectedDateFin = dateFinPicker.getValue();
            Date dateDebut = Date.valueOf(selectedDateDebut);
            Date dateFin = Date.valueOf(selectedDateFin);

            DemandeSponsoring updatedDemandeSponsoring = new DemandeSponsoring(
                    Integer.parseInt(userIdField.getText()),
                    Double.parseDouble(budgetField.getText()),
                    dateDebut,
                    dateFin,
                    Integer.parseInt(sponsoringIdField.getText()),
                    Integer.parseInt(userIdField.getText()), nomAssociationField.getText(),
                    sponsoringIdField.getText());

            demandeSponsoringService.update(updatedDemandeSponsoring);
            AfficherDemandeSponsoring afficherDemandeSponsoring = new AfficherDemandeSponsoring();
            try {
                afficherDemandeSponsoring.refreshGrid();

            } catch (SQLException e) {
                e.printStackTrace();
            }
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
}
