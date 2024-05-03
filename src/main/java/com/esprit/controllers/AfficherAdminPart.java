package com.esprit.controllers;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;

import com.esprit.models.Participant;
import com.esprit.services.ServiceParticipant;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class AfficherAdminPart implements Initializable {


    @FXML
    private Button Delete;

    @FXML
    private TableColumn<Participant, String> function;

    @FXML
    private TableColumn<Participant, Integer> idseminar_id;

    @FXML
    private TableColumn<Participant, String> last_name;

    @FXML
    private TableColumn<Participant, String> name;

    @FXML
    private TableView<Participant> parti;

    @FXML
    private TableColumn<Participant, Integer> phone;

    @FXML
    private Button retour;

    @FXML
    void retourner(ActionEvent event) {
        // Fermer la fenêtre actuelle
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();

        // Charger et afficher la nouvelle interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/BackAdmin.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'exception, le cas échéant
        }


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        populateTableView();
    }

    private void populateTableView() {
        // Retrieve data from the database
        ServiceParticipant serviceParticipant = new ServiceParticipant();
        List<Participant> participantList = serviceParticipant.afficher();

        // Clear existing items in the TableView
        parti.getItems().clear();

        // Set cell value factories for each column
        idseminar_id.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdseminar_id()).asObject());
        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        last_name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLast_name()));
        function.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFunction()));
        phone.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPhone()).asObject());

        // Add the retrieved data to the TableView
        parti.getItems().addAll(participantList);

        // Add event handler for selection
        parti.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Open QR code dialog for the selected participant
                openQRCodeDialog(newSelection);
            }
        });
    }

    private void navigateToUpdateParticipant(Participant selectedP) {
        try {
            // Charger le fichier FXML de la fenêtre de mise à jour
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierParticipant.fxml"));
            Parent root = loader.load();

            // Accéder au contrôleur et passer le Participant sélectionné à celui-ci
            ModifierParticipant controller = loader.getController();
            controller.initData(selectedP);

            // Afficher la scène contenant le fichier FXML de la mise à jour
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Rafraîchir la TableView lorsque la fenêtre de mise à jour est fermée
            stage.setOnCloseRequest(event -> {
                populateTableView(); // Rafraîchir la TableView
            });

            // Afficher la fenêtre de mise à jour
            stage.showAndWait(); // Utilisez showAndWait() pour attendre la fermeture de la fenêtre

            // Rafraîchir la TableView après la mise à jour
            populateTableView();
        } catch (IOException e) {
            // Afficher un message d'erreur sur la console en cas d'échec
            System.out.println("Une erreur s'est produite lors du chargement de la fenêtre de mise à jour : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void Delete(ActionEvent event) {
        // Récupérer le Participant sélectionné dans la table
        Participant selectedParticipant = parti.getSelectionModel().getSelectedItem();
        if (selectedParticipant != null) {
            // Afficher une boîte de dialogue de confirmation
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Dialogue de Confirmation");
            confirmAlert.setHeaderText("Supprimer Participant");
            confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce Participant ?");

            // Utiliser une expression lambda pour gérer le choix des Participants
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Participant confirmé, procéder à la suppression
                    // Appeler la méthode deleteParticipant avec le Participant sélectionné
                    ServiceParticipant ServiceParticipant = new ServiceParticipant();
                    ServiceParticipant.supprimer(selectedParticipant);
                    // Afficher un message de réussite
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Suppression Réussie");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Participant supprimé avec succès.");
                    successAlert.showAndWait();
                    // Rafraîchir la table après la suppression
                    populateTableView();
                }
            });
        } else {
            // Afficher une alerte si aucun seminaire n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune Sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un Participant à supprimer.");
            alert.showAndWait();
        }
    }

    private void openQRCodeDialog(Participant participant) {
        Dialog<Void> qrCodeDialog = new Dialog<>();
        qrCodeDialog.setTitle("Code QR du Participant");
        // Générer le contenu du code QR en fonction des informations du Participant
        String qrContent = generateQRContent(participant);
        // Générer l'image QR code à partir du contenu
        Image qrCodeImage = GenerateQRCode.generateQRCodeImage(qrContent);
        // Créer un ImageView pour afficher l'image QR code
        ImageView imageView = new ImageView(qrCodeImage);
        // Créer une boîte VBox pour organiser l'interface utilisateur
        VBox vBox = new VBox();
        vBox.getChildren().addAll(new Label("Code QR de la transaction :"), imageView);
        // Définir le contenu de la boîte de dialogue
        qrCodeDialog.getDialogPane().setContent(vBox);
        // Ajouter un bouton de fermeture à la boîte de dialogue
        ButtonType closeButton = new ButtonType("Fermer", ButtonBar.ButtonData.CANCEL_CLOSE);
        qrCodeDialog.getDialogPane().getButtonTypes().add(closeButton);
        // Gérer l'événement de fermeture de la boîte de dialogue
        qrCodeDialog.setOnCloseRequest(event -> {
            qrCodeDialog.close(); // Fermer la boîte de dialogue lorsque l'événement de fermeture est déclenché
        });
        // Afficher la boîte de dialogue
        qrCodeDialog.showAndWait();
    }

/*
    private void openQRCodeDialog(Participant Participant) {
        Dialog<Void> qrCodeDialog = new Dialog<>();
        qrCodeDialog.setTitle("Code QR du Participant");
        // Générer le contenu du code QR en fonction des informations du Participant
        String qrContent = generateQRContent(Participant);
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
    }*/

    private String generateQRContent(Participant Participant) {
        // Générer le contenu du code QR en fonction des informations du Participant
        // Par exemple, vous pouvez concaténer les détails du Participant dans une chaîne
        return "ID: " + Participant.getId() + "\n" +
                "Id_Seminaire: " + Participant.getIdseminar_id() + "\n" +
                "Nom du participant : " + Participant.getName() + "\n" +
                "Prenom du participant : " + Participant.getLast_name() + "\n" +
                "Fonction : " + Participant.getFunction() + "\n" +
                "Phone :" + Participant.getPhone();

    }
    private void openEditOrDeleteDialog(Participant participant) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choisir une action");
        alert.setHeaderText("Que souhaitez-vous faire avec ce participant ?");
        alert.setContentText("Choisissez votre option :");
        ButtonType qrCodeButton = new ButtonType("QRCode");
        ButtonType cancelButton = new ButtonType("Annuler",
                ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(qrCodeButton, cancelButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
        if (result.get() == qrCodeButton) {
            // Afficher le code QR openQRCodeDialog(participant);

        }
        }
    }
}