package com.esprit.controllers;
import com.esprit.models.account;
import com.esprit.models.transaction;
import com.esprit.services.transactionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.cert.PolicyNode;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
public class AffichageTransactionController {
    @FXML
    private FlowPane flowPane;
    private final transactionService transactionService = new transactionService();

    //Charger les données depuis la base
    @FXML
    void initialize() {
        try {
            // Charger les données depuis la base de données lors de l'initialisation de la fenêtre
            loadTransactions();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //chagement de puis a base de donnée des informations
    private void loadTransactions() throws SQLException {
        List<transaction> transactions = transactionService.afficher();

        for (transaction transaction : transactions) {
            Label label = createTransactionLabel(transaction);
            flowPane.getChildren().add(label);
        }
    }

    private Label createTransactionLabel(transaction transaction) {
        Label label = new Label("ID: " + transaction.getId() + "\n" +
                "Montant: " + transaction.getmontant() + "\n" +
                "Compte utilisateur : " + transaction.getaccount_debited() + "\n" +
                "Compte destinataire : " + transaction.getaccount_destination() + "\n" +
                "Date de transaction : " + transaction.getdate_transaction() + "\n" +
                "Type de transaction :" + transaction.gettype_transaction() + "\n" +
                "Description : " + transaction.getdescription() + "\n" +
                "Compte association id :" + transaction.getid_account_id());
        label.setOnMouseClicked(event -> openEditOrDeleteDialog(transaction));

        return label;
    }

    private void openEditOrDeleteDialog(transaction transaction) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choisir une action");
        alert.setHeaderText("Que souhaitez-vous faire avec cette transaction ?");
        alert.setContentText("Choisissez votre option :");

        ButtonType editButton = new ButtonType("Modifier");
        ButtonType deleteButton = new ButtonType("Supprimer");
        ButtonType qrCodeButton = new ButtonType("QRCode");
        ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(editButton,qrCodeButton, deleteButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == editButton) {
                // Ouvrir l'interface de modification
                openEditInterface(transaction);
            } else if (result.get() == deleteButton) {
                // Supprimer le compte
                deleteTransaction(transaction);
            }  else if (result.get() == qrCodeButton) {
                // Afficher le code QR
                openQRCodeDialog(transaction);
            }
        }
    }

    //Ouverture de l'interface modifier
    private void openEditInterface(transaction transaction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierTransaction.fxml"));
            Parent root = loader.load();

            ModifierTransactionController controller = loader.getController();
            controller.initData(transaction);

            Stage currentStage = (Stage) flowPane.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Modifier Transaction");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void GotoAjouterTransaction(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterTransaction.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void deleteTransaction(transaction transaction) {
        //confirmation du choix de la suppression
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation de suppression");
        confirmationDialog.setHeaderText("Êtes-vous sûr de vouloir supprimer cette transaction ?");
        confirmationDialog.setContentText("Cette action est irréversible.");

        Optional<ButtonType> result = confirmationDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Appeler la méthode supprimer de votre service
                transactionService.supprimer(transaction);
                // Actualiser l'affichage des transaction après la suppression
                loadTransactions();
                refreshTransactions();
                showAlert("La transaction a été supprimée avec succès.");
            } catch (SQLException e) {
                showAlert("Une erreur s'est produite lors de la suppression de la transaction : " + e.getMessage());
            }
        }
    }


    @FXML
    void retour(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MenuAdmin.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Méthode utilitaire pour afficher une alerte
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Avertissement");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void refreshTransactions() {
        try {
            // Charger à nouveau les données depuis la base de données
            flowPane.getChildren().clear(); // Effacer les transactions actuellement affichées
            loadTransactions(); // Charger les transactions mises à jour
        } catch (SQLException e) {
            showAlert("Erreur lors du rafraîchissement des transactions : " + e.getMessage());
        }
    }

    //QR code
    // Ajoutez cette méthode à votre classe AffichageTransactionController
    private void openQRCodeDialog(transaction transaction) {
        Dialog<Void> qrCodeDialog = new Dialog<>();
        qrCodeDialog.setTitle("Code QR de la transaction");

        // Générer le contenu du code QR en fonction des informations de la transaction
        String qrContent = generateQRContent(transaction);

        // Générer l'image QR code à partir du contenu
        Image qrCodeImage = GenerateQRCode.generateQRCodeImage(qrContent);

        // Créer un ImageView pour afficher l'image QR code
        ImageView imageView = new ImageView(qrCodeImage);

        // Créer une boîte VBox pour organiser l'interface utilisateur
        VBox vBox = new VBox();
        vBox.getChildren().addAll(new Label("Code QR de la transaction :"), imageView);

        // Définir le contenu de la boîte de dialogue
        qrCodeDialog.getDialogPane().setContent(vBox);

        // Afficher la boîte de dialogue
        qrCodeDialog.showAndWait();
    }

    private String generateQRContent(transaction transaction) {
        // Générer le contenu du code QR en fonction des informations de la transaction
        // Par exemple, vous pouvez concaténer les détails de la transaction dans une chaîne
        return "ID: " + transaction.getId() + "\n" +
                "Montant: " + transaction.getmontant() + "\n" +
                "Compte utilisateur : " + transaction.getaccount_debited() + "\n" +
                "Compte destinataire : " + transaction.getaccount_destination() + "\n" +
                "Date de transaction : " + transaction.getdate_transaction() + "\n" +
                "Type de transaction :" + transaction.gettype_transaction() + "\n" +
                "Description : " + transaction.getdescription() + "\n" +
                "Compte association id :" + transaction.getid_account_id();
    }

    @FXML
    void goTransaction(ActionEvent event) {


    }

    @FXML
    void goassociation(ActionEvent event) {

    }

    @FXML
    void gopret(ActionEvent event) {

    }

    @FXML
    void goseminaire(ActionEvent event) {

    }

    @FXML
    void gosponsoring(ActionEvent event) {

    }

    @FXML
    void gouser(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {

    }
}
