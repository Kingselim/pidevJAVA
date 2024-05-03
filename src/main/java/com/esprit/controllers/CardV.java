package com.esprit.controllers;

import com.esprit.controllers.FrontListeSemin;
import com.esprit.models.Seminaire;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class CardV {
    @FXML
    private Button Detail;

    @FXML
    private AnchorPane cardPane;

    @FXML
    private Label dateLabel;

    @FXML
    private Label dateLable;


    @FXML
    private Button Detail1;
    private Seminaire Seminaire;

    public Seminaire getSeminaire() {
        return Seminaire;
    }
    private FrontListeSemin afficherSFController;

    public void setAfficherLogFController(FrontListeSemin controller) {
        this.afficherSFController = controller;
    }

    public void setLogement(Seminaire Seminaire) {
        this.Seminaire = Seminaire;

        dateLabel.setText(Seminaire.getNomassociation());
        dateLable.setText(String.valueOf(Seminaire.getDate_seminar()));

    }
    private void ouvrirFenetreT(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ouvrirAjouterS(ActionEvent actionEvent) {
        ouvrirFenetreT("/AjouterParticipant.fxml", "Participer");
    }
    @FXML
    void afficherDetails(ActionEvent event) {
        // Récupérer le logement associé à cette carte
        Seminaire selectedS = getSeminaire();

        try {
            // Chargez le fichier FXML des détails du logement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DeatilSem.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            // Obtenez le contrôleur des détails du logement
            DeatilSem detailController = loader.getController();

            // Passez les informations du Seminaires sélectionné au contrôleur des détails du Seminaire
            detailController.setSeminaire(selectedS);

            // Afficher la fenêtre modale des détails du logement
            stage.setTitle("Détails du Seminaire");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}