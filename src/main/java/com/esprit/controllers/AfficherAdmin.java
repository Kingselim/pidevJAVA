package com.esprit.controllers;
import com.esprit.models.Seminaire;
import com.esprit.models.Participant;
import com.esprit.services.ServiceParticipant;
import com.esprit.services.ServiceSeminaire;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherAdmin implements Initializable {

    @FXML
    private TableView<Seminaire> Seminar;

    @FXML
    private TableColumn<Seminaire, String> date_seminar;

    @FXML
    private TableColumn<Seminaire, String> description;

    @FXML
    private TableColumn<Seminaire, Integer> iduserasso_id;

    @FXML
    private TableColumn<Seminaire, String> nomassociation;

    @FXML
    private Button goParticipan;

    @FXML
    void goParticipan(ActionEvent event) {
        // Fermer la fenêtre actuelle
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();

        // Charger et afficher la nouvelle interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/BackAdminPart.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'exception, le cas échéant
        }
    }

    @FXML
    void supprimer(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        populateTableView();
        // Lier la méthode afficherParticipants à l'événement de clic de la TableView
        Seminar.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Clic simple détecté
                // Appeler la méthode afficherParticipants lorsqu'un séminaire est cliqué
                afficherParticipants(null);

            }
        });


    }
    @FXML
    void afficherParticipants(ActionEvent event) {
        // Récupérer le séminaire sélectionné dans le TableView
        Seminaire seminaireSelectionne = Seminar.getSelectionModel().getSelectedItem();

        if (seminaireSelectionne != null) {
            // Récupérer l'ID du séminaire sélectionné
            int idSeminaire = seminaireSelectionne.getId();

            // Appeler la méthode pour récupérer les participants pour cet ID de séminaire
            ServiceParticipant serviceParticipant = new ServiceParticipant();
            List<Participant> participants = serviceParticipant.getParticipantsForSeminaire(idSeminaire);

            // Afficher la liste des participants
            if (!participants.isEmpty()) {
                // Vous pouvez afficher les participants dans une fenêtre modale, dans une nouvelle TableView, ou toute autre méthode que vous préférez
                // Par exemple, afficher les participants dans la console pour la démonstration
                participants.forEach(System.out::println);
            } else {
                // Afficher un message si aucun participant n'est trouvé pour ce séminaire
                showAlert("Aucun participant", "Aucun participant trouvé pour ce séminaire.");
            }
        } else {
            // Si aucun séminaire n'est sélectionné, affichez un message d'erreur
            showAlert("Erreur", "Veuillez sélectionner un séminaire pour afficher les participants.");
        }
    }
    void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }




    private void populateTableView() {
        // Retrieve data from the database
        ServiceSeminaire ServiceSeminaire = new ServiceSeminaire();
        List<Seminaire> SeminaireList = ServiceSeminaire.afficher();

        // Clear existing items in the TableView
        Seminar.getItems().clear();

        // Set cell value factories for each column
        iduserasso_id.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIduserasso_id()).asObject());
        date_seminar.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate_seminar()));
        description.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        nomassociation.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomassociation()));

        // Add the retrieved data to the TableView
        Seminar.getItems().addAll(SeminaireList);
        //add
        Seminar.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click detected
                Seminaire selectedSeminaire = Seminar.getSelectionModel().getSelectedItem();
                if (selectedSeminaire != null) {
                    int SeminaireId = selectedSeminaire.getId();
                    if (selectedSeminaire != null) {
                        // Navigate to UpdateUser.fxml
                        navigateToUpdateSeminair(selectedSeminaire);
                    }
                }
            }
        });
    }

    private void navigateToUpdateSeminair(Seminaire selectedS) {
        try {
            // Charger le fichier FXML de la fenêtre de mise à jour
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierSeminaire.fxml"));
            Parent root = loader.load();

            // Accéder au contrôleur et passer le séminaire sélectionné à celui-ci
            ModifierSeminaire controller = loader.getController();
            controller.initData(selectedS);

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
    void modifer(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage
        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ModifierSeminaire.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }

    }

    @FXML
    private void delete(ActionEvent event) {
        // Récupérer le Seminaire sélectionné dans la table
        Seminaire selectedSeminaire = Seminar.getSelectionModel().getSelectedItem();
        if (selectedSeminaire != null) {
            // Afficher une boîte de dialogue de confirmation
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Dialogue de Confirmation");
            confirmAlert.setHeaderText("Supprimer Seminaire");
            confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce Seminaire ?");

            // Utiliser une expression lambda pour gérer le choix du seminaire
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Logement confirmé, procéder à la suppression
                    // Appeler la méthode deleteOne avec le seminaire sélectionné
                    ServiceSeminaire serviceSeminaire = new ServiceSeminaire();
                    serviceSeminaire.supprimer(selectedSeminaire);
                    // Afficher un message de réussite
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Suppression Réussie");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Seminaire supprimé avec succès.");
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
            alert.setContentText("Veuillez sélectionner un Seminaire à supprimer.");
            alert.showAndWait();
        }
    }



    @FXML
    void openQRCodeDialog(ActionEvent event) {
        // Récupérer le séminaire sélectionné dans la table
        Seminaire selectedSeminar = Seminar.getSelectionModel().getSelectedItem();
        if (selectedSeminar != null) {
            // Générer le contenu du code QR en fonction du séminaire sélectionné
            String qrContent = generateQRContent(selectedSeminar);

            // Générer l'image QR code à partir du contenu
            Image qrCodeImage = GenerateQRCode.generateQRCodeImage(qrContent);

            // Créer un ImageView pour afficher l'image QR code
            ImageView imageView = new ImageView(qrCodeImage);

            // Créer une boîte VBox pour organiser l'interface utilisateur
            VBox vBox = new VBox();
            vBox.getChildren().addAll(new Label("Code QR du Séminaire :"), imageView);

            // Créer une boîte de dialogue pour afficher le code QR
            Dialog<Void> qrCodeDialog = new Dialog<>();
            qrCodeDialog.setTitle("Code QR du Séminaire");
            qrCodeDialog.getDialogPane().setContent(vBox);

            // Ajouter un bouton de fermeture à la boîte de dialogue
            ButtonType closeButton = new ButtonType("Fermer", ButtonBar.ButtonData.CANCEL_CLOSE);
            qrCodeDialog.getDialogPane().getButtonTypes().add(closeButton);

            // Afficher la boîte de dialogue
            qrCodeDialog.showAndWait();
        } else {
            // Afficher une alerte si aucun séminaire n'est sélectionné
            showAlert("Aucune Sélection", "Veuillez sélectionner un Séminaire pour afficher le code QR.");
        }
    }

    // Autres méthodes existantes...




    private String generateQRContent(Seminaire P) {
        // Générer le contenu du code QR en fonction des informations du Séminaire
        // Par exemple, vous pouvez concaténer les détails du Séminaire dans une chaîne
        return "ID_semainaire: " + P.getId() ;


    }





}


