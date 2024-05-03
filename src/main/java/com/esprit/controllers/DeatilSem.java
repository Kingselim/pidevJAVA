package com.esprit.controllers;


import com.esprit.models.Seminaire;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;

public class DeatilSem {
    @FXML
    private ImageView imageView;
    @FXML
    private Label lblParagraphe;

    @FXML
    private Label lblAdresse;

    @FXML
    private Label lblEquipement;

    @FXML
    private Label lblDescription;


    private Seminaire Seminaire;

    @FXML
    private void click(MouseEvent mouseEvent) {
        // Logique de clic (si nécessaire)
    }

    public void setSeminaire(Seminaire Seminaire) {
        this.Seminaire = Seminaire;

        if (Seminaire != null) {
            String nomass = Seminaire.getNomassociation();
            String date = Seminaire.getDate_seminar();
            String description = Seminaire.getDescription();


            // Ajoute un retour à la ligne après chaque point dans la description
            description = description.replaceAll("\\.", ".\n");

            String paragraph = "Ce seminaire d'association " + nomass + ". ";
            paragraph += "à la date: " + date + ". ";
            paragraph += "avec : " + description + ". ";


            // Afficher le paragraphe dans votre interface utilisateur
            lblParagraphe.setText(paragraph);

        } else {
            //clearFields();
            return; // Ajout de ce return pour sortir de la méthode si logement est null
        }
    }




   /* private void clearFields() {
        lblAdresse.setText("");
        lblEquipement.setText("");
        lblDescription.setText("");
        lblTarifs.setText("");

    }*/
}