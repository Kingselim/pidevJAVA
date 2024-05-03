package com.esprit.controllers;

import com.esprit.models.Seminaire;
import com.esprit.services.ServiceSeminaire;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.time.LocalDate;

public class ModifierSeminaire {

    @FXML
    private Button confirmerButton;


    @FXML
    private TextField date_seminar;

    @FXML
    private TextField description;

    @FXML
    private TextField nomassociation;
    private Seminaire selectedSeminaire;

    public void initData(Seminaire seminaire) {
        this.selectedSeminaire = seminaire;

        // Populate the fields in the UI with the data from selectedSeminaire
        //iduserasso_id.setText(selectedSeminaire.getIduserasso_id());
        date_seminar.setText(selectedSeminaire.getDate_seminar());
        description.setText(selectedSeminaire.getDescription());
        nomassociation.setText(selectedSeminaire.getNomassociation());
        // Set other fields here as needed
    }

    public void updateOne(javafx.event.ActionEvent actionEvent) {
        // Vérification de la saisie dans tous les champs
        if (date_seminar.getText().isEmpty() || description.getText().isEmpty() || nomassociation.getText().isEmpty()) {
            // Afficher un message d'erreur si un champ est vide
            showAlert("Erreur de saisie", "Veuillez remplir tous les champs");
            return;
        }

        // Vérification de la validité de la date
        String selectedDateSeminar = date_seminar.getText();
        LocalDate seminarDate = LocalDate.parse(selectedDateSeminar);

        if (seminarDate.isBefore(LocalDate.now())) {
            // Afficher un message d'erreur si la date est antérieure à la date actuelle
            showAlert("Erreur de saisie", "La date du séminaire doit être égale ou postérieure à la date actuelle");
            return;
        }

        // Vérification si description ne contient que des lettres et des espaces
        String descriptionInput = description.getText();
        if (!descriptionInput.matches("[a-zA-Z ]+")) {
            // Afficher un message d'erreur si la description contient des caractères non alphabétiques ou non des espaces
            showAlert("Erreur de saisie", "La description doit contenir uniquement des lettres et des espaces.");
            return;
        }

        // Vérification si nomassociation ne contient que des lettres et des espaces
        String nomAssociationInput = nomassociation.getText();
        if (!nomAssociationInput.matches("[a-zA-Z ]+")) {
            // Afficher un message d'erreur si le nom de l'association contient des caractères non alphabétiques ou non des espaces
            showAlert("Erreur de saisie", "Le nom de l'association doit contenir uniquement des lettres et des espaces.");
            return;
        }

        // Créer un nouvel objet Séminaire avec les valeurs récupérées
        Seminaire seminaire = new Seminaire(selectedSeminaire.getId(), selectedSeminaire.getIduserasso_id(), selectedDateSeminar, descriptionInput, nomAssociationInput);
        ServiceSeminaire serviceSeminaire = new ServiceSeminaire();

        // Mettre à jour le séminaire dans la base de données
        serviceSeminaire.modifier(seminaire);
        System.out.println("Séminaire mis à jour avec succès.");

        // Fermer la fenêtre de modification après la mise à jour réussie
        Stage stage = (Stage) description.getScene().getWindow();
        stage.close();
    }

    /*public void updateOne(javafx.event.ActionEvent actionEvent) {
        // Vérification de la saisie dans tous les champs
        if (date_seminar.getText().isEmpty() || description.getText().isEmpty() || nomassociation.getText().isEmpty()) {
            // Afficher un message d'erreur si un champ est vide
            showAlert("Erreur de saisie", "Veuillez remplir tous les champs");
            return;
        }

        // Vérification de la validité de la date
        String selectedDateSeminar = date_seminar.getText();
        LocalDate seminarDate = LocalDate.parse(selectedDateSeminar);

        if (seminarDate.isBefore(LocalDate.now())) {
            // Afficher un message d'erreur si la date est antérieure à la date actuelle
            showAlert("Erreur de saisie", "La date du séminaire doit être égale ou postérieure à la date actuelle");
            return;
        }

        // Créer un nouvel objet Séminaire avec les valeurs récupérées
        Seminaire seminaire = new Seminaire(selectedSeminaire.getId(), selectedSeminaire.getIduserasso_id(), selectedDateSeminar, description.getText(), nomassociation.getText());
        ServiceSeminaire serviceSeminaire = new ServiceSeminaire();

        // Mettre à jour le séminaire dans la base de données
        serviceSeminaire.modifier(seminaire);
        System.out.println("Séminaire mis à jour avec succès.");

        // Fermer la fenêtre de modification après la mise à jour réussie
        Stage stage = (Stage) description.getScene().getWindow();
        stage.close();
    }*/

    // Méthode utilitaire pour afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


/*
    public void updateOne(javafx.event.ActionEvent actionEvent) {
        // Récupérer les valeurs mises à jour depuis les champs de texte
        //selectedIdSeminar = iduserasso_id.getValue();
        String selectedDateSeminar = date_seminar.getText();
        String selectedDescription = description.getText();
        String  selectedNomAssociation =nomassociation.getText();
        // Récupérer d'autres valeurs depuis d'autres champs si nécessaire

        // Créer un nouvel objet Seminaire avec les valeurs récupérées
       Seminaire seminaire = new Seminaire(selectedSeminaire.getId(),selectedSeminaire.getIduserasso_id(),selectedDateSeminar, selectedDescription, selectedNomAssociation);
        ServiceSeminaire serviceSeminaire = new ServiceSeminaire();


        // Mettre à jour le séminaire dans la base de données
       serviceSeminaire.modifier(seminaire);
        System.out.println("Seminaire updated successfully.");


        // Fermer la fenêtre de modification après la mise à jour réussie
        Stage stage = (Stage) description.getScene().getWindow();
        stage.close();

    }
    */


}


