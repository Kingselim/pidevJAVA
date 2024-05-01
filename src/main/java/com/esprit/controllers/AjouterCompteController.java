package com.esprit.controllers;

import com.esprit.models.account;
import com.esprit.services.accountService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AjouterCompteController implements Initializable {

    @FXML
    private TextField Association;

    @FXML
    private TextField Date_creation;

    @FXML
    private TextField Solde;

    @FXML
    private TextField State;

    @FXML
    private TextField type_account;
    private final account ac = new account();

    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Association.setText("");
        Date_creation.setText("");
        Solde.setText("");
        State.setText("");
        type_account.setText("");

        //////////////
        // Ajouter un écouteur de focus pour le champ Date_creation
        Date_creation.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Quand le champ perd le focus
                validateDate();
            }
        });
        //validate Solde
        Solde.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                showAlert("Le solde doit être un nombre décimal.");
                // Effacer le champ Solde pour indiquer une saisie invalide
                Solde.setText("");
            }
        });
/*
        //validate Type_account
        type_account.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("Collect de fond|Pret|Autre")) {
                showAlert("Le type de compte doit être 'Collect de fond', 'Pret' ou 'Autre'.");
                // Effacer le champ Type_account pour indiquer une saisie invalide
                type_account.setText("");
            }
        });*/


        //validate State
        State.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[01]")) {
                showAlert("La valeur de l'état doit être 0 ou 1.");
                // Effacer le champ State pour indiquer une saisie invalide
                State.setText("");
            }
        });

        

    }

    // Méthode pour valider le format de la date
    private void validateDate() {
        String date = Date_creation.getText();
        Matcher matcher = DATE_PATTERN.matcher(date);
        if (!matcher.matches()) {
            showAlert("Format de date invalide. Le format doit être dd/mm/yyyy.");
            // Effacer le champ Date_creation pour indiquer une saisie invalide
            Date_creation.setText("");
        }
    }


    // Méthode utilitaire pour afficher une boîte de dialogue d'alerte
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void retour(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCompte.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }


    @FXML
    void ajouter(ActionEvent event) throws IOException {
        // Création d'une nouvelle instance de service de compte
        accountService service = new accountService();


        double soldeValue = Double.parseDouble(Solde.getText());
        String typeAccountValue = type_account.getText();
        String dateCreationValue = Date_creation.getText();
        int stateValue = Integer.parseInt(State.getText());
        int idAssociationValue = Integer.parseInt(Association.getText());

        // Création d'une nouvelle instance de compte avec les valeurs récupérées
        account newAccount = new account(soldeValue, typeAccountValue, dateCreationValue, stateValue, idAssociationValue);

        // Appel de la méthode d'ajout du service de compte
        service.ajouter(newAccount);

        // Fermeture de la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCompte.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}





