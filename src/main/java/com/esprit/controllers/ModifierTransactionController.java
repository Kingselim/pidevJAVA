package com.esprit.controllers;

import com.esprit.models.transaction;

import com.esprit.services.transactionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifierTransactionController {

    @FXML
    private TextField account_debited;

    @FXML
    private TextField account_destination;

    @FXML
    private TextField date_transaction;

    @FXML
    private TextField description;

    @FXML
    private TextField id_account_id;

    @FXML
    private TextField montant;

    @FXML
    private TextField type_transaction;
    private transaction TransactionAModifier;
    public void initData(transaction transaction)
    {
        TransactionAModifier = transaction;
        account_debited.setText(String.valueOf(transaction.getaccount_debited()));
        account_destination.setText(transaction.getaccount_destination());
        date_transaction.setText(transaction.getdate_transaction());
        description.setText(transaction.getdescription());
        id_account_id.setText(String.valueOf(transaction.getid_account_id()));
        montant.setText(String.valueOf(transaction.getmontant()));
        type_transaction.setText(transaction.gettype_transaction());
            ////////Controle de saisie////////////////
        // Ajout d'un écouteur de focus pour chaque champ de texte
        account_debited.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // L'utilisateur a quitté le champ de texte
                validerSaisie(account_debited.getText());
            }
        });

        account_destination.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validerSaisie(account_destination.getText());
            }
        });

        date_transaction.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validerSaisie(date_transaction.getText());
            }
        });

        montant.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validerSaisie(montant.getText());
            }
        });

        type_transaction.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validerSaisie(type_transaction.getText());
            }
        });

        description.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validerSaisie(description.getText());
            }
        });
    }
    private void validerSaisie(String texte) {
        // Ajoutez ici votre logique de validation
        if (texte.isEmpty()) {
            showAlert("Le champ ne peut pas être vide.");
        } else {
            // Le champ est valide
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void modifier(ActionEvent event) throws IOException {
        transactionService ts = new transactionService();
        // Obtenez les nouvelles valeurs des champs
        String newAccountDebited = account_debited.getText();
        String newAccountDestination = account_destination.getText();
        String newDateTransaction = date_transaction.getText();
        String newDescription = description.getText();
        String newIdAccountId = id_account_id.getText();
        String newMontant = montant.getText();
        String newTypeTransaction = type_transaction.getText();

        // Mettez à jour les propriétés de la transaction avec les nouvelles valeurs
        TransactionAModifier.setAccount_debited(newAccountDebited);
        TransactionAModifier.setAccount_destination(newAccountDestination);
        TransactionAModifier.setDate_transaction(newDateTransaction);
        TransactionAModifier.setDescription(newDescription);
        TransactionAModifier.setId_account_id(Integer.parseInt(newIdAccountId));
        TransactionAModifier.setMontant(Double.parseDouble(newMontant));
        TransactionAModifier.setType_transaction(newTypeTransaction);

        ts.modifier(TransactionAModifier);

        // Affichez une boîte de dialogue pour indiquer que la modification a été effectuée avec succès
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modification de la transaction");
        alert.setContentText("La transaction a été modifiée avec succès !");
        alert.showAndWait();

        // Fermez la fenêtre de modification
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void retour(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherTransaction.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
