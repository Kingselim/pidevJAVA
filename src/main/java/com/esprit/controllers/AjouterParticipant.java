package com.esprit.controllers;

import com.esprit.models.Participant;
import com.esprit.models.Seminaire;
import com.esprit.models.TwilioSMSParticipant;
import com.esprit.services.ServiceParticipant;
import com.esprit.services.ServiceSeminaire;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AjouterParticipant implements Initializable {

        @FXML
        private Button AjouterParticip;

        @FXML
        private TextField function;

        @FXML
        private ComboBox<Integer> comboboxid;

        @FXML
        private TextField last_name;

        @FXML
        private TextField name;

        @FXML
        private TextField phone;


    private final ServiceParticipant serviceParticipant = new ServiceParticipant();
    private final ServiceSeminaire serviceSeminaire = new ServiceSeminaire();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        name.setText("");
        last_name.setText("");
        phone.setText("");
        function.setText("");

        // Charge les idseminar de la table Seminaire dans la ComboBox idseminar_id
        List<Integer> seminarIds = serviceSeminaire.getAllSeminarIds();
        comboboxid.getItems().addAll(seminarIds);

    }
@FXML
void AjouterParticip(ActionEvent event) throws IOException {

    // Vérifier si les champs requis sont vides
    if (name.getText().isEmpty() || last_name.getText().isEmpty() || function.getText().isEmpty() || phone.getText().isEmpty() || comboboxid.getValue() == null ) {
        // Afficher un message d'erreur et empêcher l'ajout du participant si un champ est vide
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

    // Création d'une nouvelle instance de service de Participant
    ServiceParticipant service = new ServiceParticipant();

    int idseminairev = comboboxid.getValue();
    String namev= name.getText();
    String last_namev = last_name.getText();
    String functionv = function.getText();
    int phonev = Integer.parseInt(phone.getText());

    // Création d'une nouvelle instance de participant avec les valeurs récupérées
    Participant newParticipant = new Participant(idseminairev, namev, last_namev, functionv, phonev );

    // Appel de la méthode d'ajout du service de Participant
    serviceParticipant.ajouter(newParticipant);
    String messageText = "Participant ajouté avec succès:\n" +
            "Nom Participant: " + namev + "\n";

    TwilioSMSParticipant.sendCustomMessage("+21626548920", messageText);
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setHeaderText(null);
    alert.setContentText("Participant ajouté avec succès!");
    alert.showAndWait();
    clearFields();

    // Charger l'interface  AjouterParticipant.fxml
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterParticipant.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
        // Gérer l'exception, le cas échéant
    }
    // Afficher une confirmation à l'utilisateur
    //Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Participation Ajoute");
    alert.setHeaderText(null);
    alert.setContentText("Vous avez été inscrit avec succès au séminaire.");
    alert.showAndWait();

    // Fermeture de la fenêtre actuelle
    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    currentStage.close();
}

/*
    void AjouterParticip(ActionEvent event) throws IOException {

        // Vérifier si les champs requis sont vides
        if (name.getText().isEmpty() || last_name.getText().isEmpty() || function.getText().isEmpty() || phone.getText().isEmpty() || comboboxid.getValue() == null ) {
            // Afficher un message d'erreur et empêcher l'ajout du participant si un champ est vide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
            return; // Sortir de la méthode
        }

        // Vérifier si le numéro de téléphone comporte exactement 7 chiffres
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

        // Création d'une nouvelle instance de service de Participant
        ServiceParticipant service = new ServiceParticipant();

        int idseminairev = comboboxid.getValue();
        String namev= name.getText();
        String last_namev = last_name.getText();
        String functionv = function.getText();
        int phonev = Integer.parseInt(phone.getText());

        // Création d'une nouvelle instance de participant avec les valeurs récupérées
        Participant newParticipant = new Participant(idseminairev, namev, last_namev, functionv, phonev );

        // Appel de la méthode d'ajout du service de Participant
        serviceParticipant.ajouter(newParticipant);
        String messageText = "Participant ajouté avec succès:\n" +
                "Nom Participant: " + namev + "\n";

        TwilioSMSParticipant.sendCustomMessage("+21626548920", messageText);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Participant ajouté avec succès!");
        alert.showAndWait();
        clearFields();

        // Charger l'interface  AjouterParticipant.fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterParticipant.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'exception, le cas échéant
        }
        // Afficher une confirmation à l'utilisateur
        //Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Participation Ajoute");
        alert.setHeaderText(null);
        alert.setContentText("Vous avez été inscrit avec succès au séminaire.");
        alert.showAndWait();

        // Fermeture de la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

    }*/

   /* @FXML
     void AjouterParticip(ActionEvent event) throws IOException {
        // Vérifier si les champs requis sont vides
        if (name.getText().isEmpty() || last_name.getText().isEmpty() || function.getText().isEmpty() || phone.getText().isEmpty() || comboboxid.getValue() == null ) {
            // Afficher un message d'erreur et empêcher l'ajout du participant si un champ est vide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
            return; // Sortir de la méthode
        }

        // Vérifier si le numéro de téléphone comporte exactement 7 chiffres
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

        // Création d'une nouvelle instance de service de Participant
        ServiceParticipant service = new ServiceParticipant();

        int idseminairev = comboboxid.getValue();
        String namev= name.getText();
        String last_namev = last_name.getText();
        String functionv = function.getText();
        int phonev = Integer.parseInt(phone.getText());

        // Création d'une nouvelle instance de participant avec les valeurs récupérées
        Participant newParticipant = new Participant(idseminairev, namev, last_namev, functionv, phonev );

        // Appel de la méthode d'ajout du service de Participant
        serviceParticipant.ajouter(newParticipant);

        // Charger l'interface  AjouterParticipant.fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterParticipant.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'exception, le cas échéant
        }
        // Afficher une confirmation à l'utilisateur
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Participation Réussie");
        alert.setHeaderText(null);
        alert.setContentText("Vous avez été inscrit avec succès au séminaire.");
        alert.showAndWait();

        // Fermeture de la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

    }*/


    private void clearFields() {
        name.clear();
        last_name.clear();
        function.clear();
        phone.clear();
    }

/*
    @FXML
    void AjouterPartici(ActionEvent event) throws IOException {
        // Création d'une nouvelle instance de service de Participant
        ServiceParticipant service = new ServiceParticipant();


        int idseminairev = comboboxid.getValue();
        String namev= name.getText();
        String last_namev = last_name.getText();
        String functionv = function.getText();
        int phonev = Integer.parseInt(phone.getText());


        // Création d'une nouvelle instance de participant avec les valeurs récupérées
        Participant newParticipant = new Participant(idseminairev, namev, last_namev, functionv, phonev );

        // Appel de la méthode d'ajout du service de Participant
        serviceParticipant.ajouter(newParticipant);

        // Charger l'interface  AjouterParticipant.fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterParticipant.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'exception, le cas échéant
        }


        // Fermeture de la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }*/
}
