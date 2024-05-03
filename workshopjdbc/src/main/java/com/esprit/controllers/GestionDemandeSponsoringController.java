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

public class GestionDemandeSponsoringController implements Initializable {

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

    private final SponsoringService sponsoringService = new SponsoringService();

    @FXML
    void AjouterDemande(ActionEvent event) {
        if (validateInputs()) {
            LocalDate dateDebut = dateDebutPicker.getValue();
            Date dateD = Date.valueOf(dateDebut);
            LocalDate dateFin = dateFinPicker.getValue();
            Date dateF = Date.valueOf(dateFin);

            int idsponsoring = getSelectedTypeID();
            int userId = Integer.parseInt(userIdField.getText());
            double budget = Double.parseDouble(budgetField.getText());
            String nomAssociation = nomAssociationField.getText(); // Assuming you have a TextField for nom_association

            DemandeSponsoringService dss = new DemandeSponsoringService();
            DemandeSponsoring ds = new DemandeSponsoring(
                    budget,
                    dateD,
                    dateF,
                    idsponsoring, // Assurez-vous d'avoir la variable sponsoringId définie
                    userId,
                    nomAssociation,
                    sponsoringIdField.getText()
            );
            dss.ajouter(ds);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sponsoringIdField.setVisible(false);
        try {
            populateComboBox();
            setupComboBoxListener();
            setupSponsoringIdFieldListener(); // Add this line
        } catch (SQLException e) {
            e.printStackTrace();
        }
        addValidators();

    }

    private void setupComboBoxListener() {
        typecombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if ("Autre".equals(newValue)) {
                sponsoringIdField.setVisible(true);
                sponsoringIdField.setDisable(false);
                sponsoringIdField.setText("");
            } else {
                sponsoringIdField.setVisible(false);
                sponsoringIdField.setDisable(true);
                sponsoringIdField.setText("");
            }
        });
    }

    private Map<String, Integer> sponsoringCountMap = new HashMap<>();
    private void setupSponsoringIdFieldListener() {
        sponsoringIdField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                int count = sponsoringCountMap.getOrDefault(newValue, 0);
                count++;
                sponsoringCountMap.put(newValue, count);

                if (count >= 5) {
                    // Automatically add to sponsoring table
                    addSponsoringToTable(newValue);
                }
            }
        });
    }

    private void addSponsoringToTable(String sponsoringType) {
        SponsoringService sponsoringService = new SponsoringService();
        Sponsoring s = new Sponsoring(10000.0, null, sponsoringType); // assuming id and date will be generated automatically
        sponsoringService.ajouter(s);
        System.out.println("Automatically adding sponsoring type to table: " + sponsoringType);
    }
    private Map<String, Integer> typeIDMap = new HashMap<>();

    private void populateComboBox() throws SQLException {
        // Clear existing items
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

    // Method to get the ID of the selected type
    private int getSelectedTypeID() {
        String selectedType = typecombo.getValue();
        if (selectedType != null && typeIDMap.containsKey(selectedType)) {
            return typeIDMap.get(selectedType);
        } else {
            return -1; // Return -1 if no type is selected or if the type is not found in the map
        }
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

    private boolean isValidBudget(String value) {
        try {
            double budget = Double.parseDouble(value);
            return budget > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidNomAssociation(String value) {
        return value.length() < 20;
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

        // Validate nom association
        if (!isValidNomAssociation(nomAssociationField.getText())) {
            nomAssociationField.setStyle("-fx-border-color: red;");
            isValid = false;
        }

        return isValid;
    }
}
