package com.esprit.controllers;
import com.esprit.models.Participant;
import com.esprit.models.Seminaire;
import com.esprit.services.ServiceParticipant;
import com.esprit.services.ServiceSeminaire;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifierParticipant {

    @FXML
    private Button modifierpart;

    @FXML
    private TextField function;

    @FXML
    private TextField last_name;

    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    private Participant selectedParticipant;


    public void initData(Participant participant) {
        this.selectedParticipant = participant;

        // Populate the fields in the UI with the data from selectedSeminaire
        //iduserasso_id.setText(selectedSeminaire.getIduserasso_id());
        name.setText(selectedParticipant.getName());
        last_name.setText(selectedParticipant.getLast_name());
        function.setText(selectedParticipant.getFunction());
        phone.setText(String.valueOf(selectedParticipant.getPhone()));
        // Set other fields here as needed
    }
    public void modifierpart(javafx.event.ActionEvent actionEvent) {
        // Vérifier si les champs requis sont vides
        if (name.getText().isEmpty() || last_name.getText().isEmpty() || function.getText().isEmpty() || phone.getText().isEmpty()) {
            // Afficher un message d'erreur et empêcher la modification du participant si un champ est vide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
            return; // Sortir de la méthode
        }

        // Vérifier si les champs name, last_name et function contiennent uniquement des lettres
        String nameInput = name.getText();
        String lastNameInput = last_name.getText();
        String functionInput = function.getText();
        if (!nameInput.matches("[a-zA-Z]+") || !lastNameInput.matches("[a-zA-Z]+") || !functionInput.matches("[a-zA-Z]+")) {
            // Afficher un message d'erreur si les champs ne contiennent pas uniquement des lettres
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Les champs Nom, Prénom et Fonction doivent contenir uniquement des lettres.");
            alert.showAndWait();
            return; // Sortir de la méthode
        }

        // Vérifier si le numéro de téléphone comporte exactement 8 chiffres
        String phoneNumber = phone.getText();
        if (!phoneNumber.matches("\\d{8}")) {
            // Afficher un message d'erreur si le numéro de téléphone ne comporte pas 8 chiffres
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Le numéro de téléphone doit comporter exactement 8 chiffres");
            alert.showAndWait();
            return; // Sortir de la méthode
        }

        // Récupérer les valeurs mises à jour depuis les champs de texte
        String selectedname = name.getText();
        String selectedlast_name = last_name.getText();
        String selectedFunction = function.getText();
        String selectedPhone = phone.getText();
        // Récupérer d'autres valeurs depuis d'autres champs si nécessaire

        // Créer un nouvel objet Participant avec les valeurs récupérées
        Participant participant = new Participant(selectedParticipant.getId(), selectedParticipant.getIdseminar_id(), selectedname, selectedlast_name, selectedFunction, selectedParticipant.getPhone());
        ServiceParticipant serviceParticipant = new ServiceParticipant();

        // Mettre à jour le participant dans la base de données
        serviceParticipant.modifier(participant);
        System.out.println("Participant mis à jour avec succès.");

        // Fermer la fenêtre de modification après la mise à jour réussie
        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();
    }




   /* public void modifierpart(javafx.event.ActionEvent actionEvent) {
        // Vérifier si les champs requis sont vides
        if (name.getText().isEmpty() || last_name.getText().isEmpty() || function.getText().isEmpty() || phone.getText().isEmpty()) {
            // Afficher un message d'erreur et empêcher la modification du participant si un champ est vide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
            return; // Sortir de la méthode
        }

        // Vérifier si le numéro de téléphone comporte exactement 8 chiffres
        String phoneNumber = phone.getText();
        if (!phoneNumber.matches("\\d{8}")) {
            // Afficher un message d'erreur si le numéro de téléphone ne comporte pas 8 chiffres
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Le numéro de téléphone doit comporter exactement 8 chiffres");
            alert.showAndWait();
            return; // Sortir de la méthode
        }

        // Récupérer les valeurs mises à jour depuis les champs de texte
        String selectedname = name.getText();
        String selectedlast_name = last_name.getText();
        String selectedFunction = function.getText();
        String selectedPhone = phone.getText();
        // Récupérer d'autres valeurs depuis d'autres champs si nécessaire

        // Créer un nouvel objet Participant avec les valeurs récupérées
        Participant participant = new Participant(selectedParticipant.getId(), selectedParticipant.getIdseminar_id(), selectedname, selectedlast_name, selectedFunction, selectedParticipant.getPhone());
        ServiceParticipant serviceParticipant = new ServiceParticipant();

        // Mettre à jour le participant dans la base de données
        serviceParticipant.modifier(participant);
        System.out.println("Participant mis à jour avec succès.");

        // Fermer la fenêtre de modification après la mise à jour réussie
        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();
    }*/


   /* public void modifierpart(javafx.event.ActionEvent actionEvent) {
        // Récupérer les valeurs mises à jour depuis les champs de texte
        //selectedIdSeminar = iduserasso_id.getValue();
        String selectedname = name.getText();
        String selectedlast_name = last_name.getText();
        String  selectedFunction =function.getText();
        String  selectedPhone = phone.getText();
        // Récupérer d'autres valeurs depuis d'autres champs si nécessaire

        // Créer un nouvel objet Participant avec les valeurs récupérées
        Participant participant = new Participant(selectedParticipant.getId(),selectedParticipant.getIdseminar_id(),selectedname, selectedlast_name, selectedFunction, selectedParticipant.getPhone());
        ServiceParticipant serviceParticipant = new ServiceParticipant();


        // Mettre à jour le séminaire dans la base de données
        serviceParticipant.modifier(participant);
        System.out.println("Participant updated successfully.");


        // Fermer la fenêtre de modification après la mise à jour réussie
        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();

    }*/


}
