package com.esprit.controllers;

import com.esprit.models.account;
import com.esprit.services.accountService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifierCompteController {

    @FXML
    private TextField Association;

    @FXML
    private TextField Date_creation;

    @FXML
    private TextField Solde;

    @FXML
    private TextField State;

    @FXML
    private TextField id;

    @FXML
    private TextField type_account;
    private account CompteAModifier;

   public void initData(account A)
    {
        CompteAModifier = A;
        id.setText(String.valueOf(A.getId()));
        Solde.setText(String.valueOf(A.getSolde()));
        type_account.setText(String.valueOf(A.getType_account()));
        Date_creation.setText(String.valueOf(A.getDate_creation()));
        State.setText(String.valueOf(A.getState()));
        Association.setText(String.valueOf(A.getId_association_id()));

    }

    @FXML
    void modifier(ActionEvent event) throws IOException {
        //L'ajout de la partie dédier
        if (!validerSaisie()) {
            return; // Arrête l'exécution si la saisie est invalide
        }

        accountService ac = new accountService();
        // Assurez-vous d'obtenir correctement l'association à partir de votre interface utilisateur
        String association = Association.getText();
        CompteAModifier.setId(Integer.parseInt(id.getText()));
        CompteAModifier.setSolde(Double.parseDouble(Solde.getText()));
        CompteAModifier.setType_account(type_account.getText());
        CompteAModifier.setDate_creation(Date_creation.getText());
        CompteAModifier.setState(Integer.parseInt(State.getText()));
        CompteAModifier.setId_association_id(CompteAModifier.getId_association_id());



        // Appel de la méthode de service pour mettre à jour le compte
        ac.modifier(CompteAModifier);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modifier Club");
        alert.setContentText("Modification avec succces");
        alert.showAndWait();

        // Fermer la fenêtre de modification
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        // Ouvrir la fenêtre d'affichage des comptes après modification
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCompte.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private boolean validerSaisie() {
        // Vérification de l'ID
        if (id.getText().isEmpty() || !id.getText().matches("\\d+")) {
            showAlert("ID invalide.");
            return false;
        }

        // Vérification du solde
        if (Solde.getText().isEmpty() || !Solde.getText().matches("\\d+(\\.\\d+)?")) {
            showAlert("Solde invalide.");
            return false;
        }

        // Vérification du type de compte
        if (type_account.getText().isEmpty()) {
            showAlert("Type de compte invalide.");
            return false;
        }

        // Vérification de la date de création
        if (Date_creation.getText().isEmpty()) {
            showAlert("Date de création invalide.");
            return false;
        }

        // Vérification de l'état
        if (State.getText().isEmpty() || !State.getText().matches("\\d+")) {
            showAlert("État invalide.");
            return false;
        }

        return true;
    }
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

}
