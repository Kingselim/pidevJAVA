package com.esprit.controllers;

import com.esprit.models.Participant;
import com.esprit.models.Seminaire;
import com.esprit.services.ServiceParticipant;
import com.esprit.services.ServiceSeminaire;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import okhttp3.*;

public class AfficherParticipant implements Initializable {

    @FXML
    private Button AjouterParticip;

    @FXML
    private Button buttonpdf;
    @FXML
    private TableColumn<Participant, String> function;

    @FXML
    private TableColumn<Participant, Integer> idseminar_id;

    @FXML
    private TableColumn<Participant, String> last_name;

    @FXML
    private TableColumn<Participant, String> name;

    @FXML
    private TableColumn<Participant, Integer> phone;

    @FXML
    private TableView<Participant> participant;

    @FXML
    private Button Return;

    @FXML
    void Return(ActionEvent event) {
        // Fermer la fenêtre actuelle
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();

        // Charger et afficher la nouvelle interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherSeminaire.fxml"));
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
        ServiceParticipant ServiceParticipant = new ServiceParticipant();
        List<Participant> ParticipantList = ServiceParticipant.afficher();

        // Clear existing items in the TableView
        participant.getItems().clear();

        // Set cell value factories for each column
        idseminar_id.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdseminar_id()).asObject());
        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        last_name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLast_name()));
        function.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFunction()));
        phone.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPhone()).asObject());

        // Add the retrieved data to the TableView
        participant.getItems().addAll(ParticipantList);
        //add
        participant.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click detected
                Participant selectedParticipant = participant.getSelectionModel().getSelectedItem();
                if (selectedParticipant != null) {
                    int ParticipantId = selectedParticipant.getId();
                    if (selectedParticipant != null) {
                        // Navigate to UpdateUser.fxml
                        navigateToUpdateParticipant(selectedParticipant);
                    }
                }
            }
        });
    }

    @FXML
    void AjouterParticip(ActionEvent event) {
        try {
            // Load and show the new interface for adding a participant
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterParticipant.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();

            // Refresh the TableView after adding a participant
            newStage.setOnHiding(e -> {
                populateTableView(); // Refresh the TableView
            });
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }
    }

    @FXML
    void modiferpar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage
        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ModifierParticipant.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }

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
    private void SupprimerParti(ActionEvent event) {
        // Récupérer le Participant sélectionné dans la table
        Participant selectedParticipant = participant.getSelectionModel().getSelectedItem();
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
     public void printLogPdf(ActionEvent actionEvent) {
        try {
            Participant selectedSeminaire = participant.getSelectionModel().getSelectedItem();
            if (selectedSeminaire == null) {
                showAlert("No Seminaire Selected", "Please select a seminaire to print.");
                return;
            }

            // Appeler la méthode generatePdf de la classe pdfgenerator pour générer le PDF avec les détails du seminaire sélectionné
            pdfgenerator.generatePdf(selectedSeminaire.getLast_name(),selectedSeminaire.getName(),selectedSeminaire.getIdseminar_id(),selectedSeminaire.getPhone());

            // Appeler la méthode envoyermail au lieu de generatePdf pour envoyer un email
            envoyermail(selectedSeminaire);

            showAlert("PDF Created", "Seminaire details printed to PDF successfully.");

        } catch (Exception e) {
            showAlert("Error", "An error occurred while printing the seminaire to PDF.");
            e.printStackTrace();
        }
    }
    public void envoyermail(Participant participant)
    {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"from\":{\"email\":\"mailtrap@demomailtrap.com\",\"name\":\"Transaction effectuée avec succées\"},\"to\":[{\"email\":\"selim03gaaloul@gmail.com\"}],\"subject\":\"Verification de transaction\",\"text\":\"Merci d'avoir subvenu et d'avoir effectuée une transaction \",\"category\":\"Integration Test\"}");
        Request request = new Request.Builder()
                .url("https://send.api.mailtrap.io/api/send")
                .method("POST", body)
                .addHeader("Authorization", "Bearer e0970a027a348eb6c62fbf88336ac050")
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println("mailenvoyer");
        } catch (IOException e) {
            //throw new RuntimeException(e);
            System.out.println(e.getMessage());
        }
    }


    void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }



}
