package com.esprit.controllers;

import java.io.IOException;
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
import com.esprit.models.Seminaire;
import com.esprit.services.ServiceSeminaire;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherSeminaire implements Initializable {

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
    private Button listeParti;
    @FXML
    void listeParti(ActionEvent event) {
        // Fermer la fenêtre actuelle
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();

        // Charger et afficher la nouvelle interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherParticipant.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'exception, le cas échéant
        }


    }

    @FXML
    void ajouter(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage
        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterSeminaire.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
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
    void supprimer(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            populateTableView();


    }

    public void populateTableView() {
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
                        navigateToUpdateSeminaire(selectedSeminaire);
                    }
                }
            }
        });
    }

    private void navigateToUpdateSeminaire(Seminaire selectedS) {
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
    private void deleteOne(ActionEvent event) {
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
                    // Seminaire confirmé, procéder à la suppression
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
}