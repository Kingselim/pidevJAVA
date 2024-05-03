package com.esprit.controllers;

import com.esprit.models.Seminaire;
import com.esprit.services.ServiceSeminaire;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.List;

public class FrontListeSemin {

    @FXML
    private ScrollPane scroll;


    @FXML
    private FlowPane SeminaireFlowPane;

    private final ServiceSeminaire serviceSeminaire = new ServiceSeminaire();

    @FXML
    private void initialize() {

        loadS(null); // Charge tous les Seminaires initialement
    }
   /* @FXML
    void printLogPdf(ActionEvent event) {
        try {
            Seminaire selectedSeminaire = (Seminaire) event.getSource();
            if (selectedSeminaire == null) {
                showAlert("No Seminaire Selected", "Please select a seminaire to print.");
                return;
            }

            // Appeler la méthode generatePdf de la classe pdfgenerator pour générer le PDF avec les détails du seminaire sélectionné
            pdfgenerator.generatePdf(selectedSeminaire.getDescription(), selectedSeminaire.getDate_seminar(), selectedSeminaire.getNomassociation());

            showAlert("PDF Created", "Seminaire details printed to PDF successfully.");

        } catch (Exception e) {
            showAlert("Error", "An error occurred while printing the seminaire to PDF.");
            e.printStackTrace();
        }
    }*/


    void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void loadS(String searchTerm) {
        try {
            List<Seminaire> logements = serviceSeminaire.afficher();

            // Nettoie le conteneur avant d'ajouter de nouveaux logements
            SeminaireFlowPane.getChildren().clear();

            for (Seminaire log : logements) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CardV.fxml"));
                try {
                    Node card = loader.load(); // Cette ligne peut générer une IOException
                    CardV controller = loader.getController();
                    controller.setLogement(log);
                    controller.setAfficherLogFController(this); // Passe la référence à ce contrôleur
                    SeminaireFlowPane.getChildren().add(card);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) { // Attrape toute exception ici
            e.printStackTrace();
        }
    }




}
