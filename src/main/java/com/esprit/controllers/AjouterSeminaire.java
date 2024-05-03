package com.esprit.controllers;


        import com.esprit.models.Seminaire;
        import com.esprit.services.ServiceSeminaire;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Alert;
        import javafx.scene.control.Label;
        import javafx.scene.control.TextField;
        import javafx.stage.Stage;

        import java.io.IOException;
        import java.net.URL;
        import java.time.LocalDate;
        import java.util.EventObject;
        import java.util.Random;
        import java.util.ResourceBundle;


public class AjouterSeminaire implements Initializable {

    @FXML
    private TextField date_seminar;

    @FXML
    private TextField description;

    @FXML
    private TextField iduserasso_id;

    @FXML
    private TextField nomassociation;
    private final Seminaire u=new Seminaire();
    private String captchaChallenge;
    @FXML
    private Label captchaLabel;

    @FXML
    private TextField captchaInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        iduserasso_id.setText("");
        date_seminar.setText("");
        description.setText("");
        nomassociation.setText("");
        generateCaptcha();

    }
    @FXML
    private void generateCaptcha() {
        captchaChallenge = generateRandomString(6); // Generate a random string for CAPTCHA
        captchaLabel.setText(captchaChallenge);
    }

    @FXML
    private void validateCaptcha() {
        String userInput = captchaInput.getText().trim(); // Get user input
        if (userInput.equals(captchaChallenge)) {
            System.out.println("CAPTCHA is valid!");
        } else {
            System.out.println("CAPTCHA is invalid!");
        }
    }

    // Method to generate a random string for CAPTCHA challenge
    private String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < length; i++) {
            stringBuilder.append(characters.charAt(random.nextInt(characters.length())));
        }
        return stringBuilder.toString();
    }
    @FXML
    void Ajouter(ActionEvent event) throws IOException {
        // Vérification de la saisie dans tous les champs
        if (iduserasso_id.getText().isEmpty() || date_seminar.getText().isEmpty() || description.getText().isEmpty() || nomassociation.getText().isEmpty() || captchaInput.getText().isEmpty()) {
            // Afficher un message d'erreur si un champ est vide
            showAlert("Erreur de saisie", "Veuillez remplir tous les champs");
            return;
        }

        // Vérification de la validité de la date
        String Date_Seminairev = date_seminar.getText();
        LocalDate seminarDate = LocalDate.parse(Date_Seminairev);

        if (seminarDate.isBefore(LocalDate.now())) {
            // Afficher un message d'erreur si la date est antérieure à la date actuelle
            showAlert("Erreur de saisie", "La date du séminaire doit être égale ou postérieure à la date actuelle");
            return;
        }

        // Vérification si description ne contient que des lettres
        String descriptionInput = description.getText();
        if (!descriptionInput.matches("[a-zA-Z]+")) {
            // Afficher un message d'erreur si la description contient des caractères non alphabétiques
            showAlert("Erreur de saisie", "La description doit contenir uniquement des lettres.");
            return;
        }

        // Vérification si nomassociation ne contient que des lettres
        String nomAssociationInput = nomassociation.getText();
        if (!nomAssociationInput.matches("[a-zA-Z]+")) {
            // Afficher un message d'erreur si le nom de l'association contient des caractères non alphabétiques
            showAlert("Erreur de saisie", "Le nom de l'association doit contenir uniquement des lettres.");
            return;
        }

        // Validate CAPTCHA
        String userInput = captchaInput.getText().trim();
        if (!userInput.equals(captchaChallenge)) {
            showAlert("Erreur de CAPTCHA", "CAPTCHA invalide.");
            return;
        }

        // CAPTCHA is valid, proceed with adding the seminar

        // Création d'une nouvelle instance de service de Séminaire
        ServiceSeminaire service = new ServiceSeminaire();

        // Récupération des valeurs saisies dans les champs
        int id_userv = Integer.parseInt(iduserasso_id.getText());
        String Descriptionv = description.getText();
        String Nom_associationv = nomassociation.getText();

        // Création d'une nouvelle instance de séminaire avec les valeurs récupérées
        Seminaire newSeminaire = new Seminaire(id_userv, Date_Seminairev, Descriptionv, Nom_associationv);

        // Appel de la méthode d'ajout du service de Séminaire
        service.ajouter(newSeminaire);

        // Charger l'interface AfficherSeminaire.fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherSeminaire.fxml"));
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
    }

    /*void Ajouter(ActionEvent event) throws IOException {
        // Vérification de la saisie dans tous les champs
        if (iduserasso_id.getText().isEmpty() || date_seminar.getText().isEmpty() || description.getText().isEmpty() || nomassociation.getText().isEmpty()) {
            // Afficher un message d'erreur si un champ est vide
            showAlert("Erreur de saisie", "Veuillez remplir tous les champs");
            return;
        }

        // Vérification de la validité de la date
        String Date_Seminairev = date_seminar.getText();
        LocalDate seminarDate = LocalDate.parse(Date_Seminairev);

        if (seminarDate.isBefore(LocalDate.now())) {
            // Afficher un message d'erreur si la date est antérieure à la date actuelle
            showAlert("Erreur de saisie", "La date du séminaire doit être égale ou postérieure à la date actuelle");
            return;
        }

        // Vérification si description ne contient que des lettres
        String descriptionInput = description.getText();
        if (!descriptionInput.matches("[a-zA-Z]+")) {
            // Afficher un message d'erreur si la description contient des caractères non alphabétiques
            showAlert("Erreur de saisie", "La description doit contenir uniquement des lettres.");
            return;
        }

        // Vérification si nomassociation ne contient que des lettres
        String nomAssociationInput = nomassociation.getText();
        if (!nomAssociationInput.matches("[a-zA-Z]+")) {
            // Afficher un message d'erreur si le nom de l'association contient des caractères non alphabétiques
            showAlert("Erreur de saisie", "Le nom de l'association doit contenir uniquement des lettres.");
            return;
        }

        // Création d'une nouvelle instance de service de Séminaire
        ServiceSeminaire service = new ServiceSeminaire();

        // Récupération des valeurs saisies dans les champs
        int id_userv = Integer.parseInt(iduserasso_id.getText());
        String Descriptionv = description.getText();
        String Nom_associationv = nomassociation.getText();

        // Création d'une nouvelle instance de séminaire avec les valeurs récupérées
        Seminaire newSeminaire = new Seminaire(id_userv, Date_Seminairev, Descriptionv, Nom_associationv);

        // Appel de la méthode d'ajout du service de Séminaire
        service.ajouter(newSeminaire);

        // Charger l'interface AfficherSeminaire.fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherSeminaire.fxml"));
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

    // Méthode utilitaire pour afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    /*
    void Ajouter(ActionEvent event) throws IOException {
        // Vérification de la saisie dans tous les champs
        if (iduserasso_id.getText().isEmpty() || date_seminar.getText().isEmpty() || description.getText().isEmpty() || nomassociation.getText().isEmpty()) {
            // Afficher un message d'erreur si un champ est vide
            showAlert("Erreur de saisie", "Veuillez remplir tous les champs");
            return;
        }

        // Vérification de la validité de la date
        String Date_Seminairev = date_seminar.getText();
        LocalDate seminarDate = LocalDate.parse(Date_Seminairev);

        if (seminarDate.isBefore(LocalDate.now())) {
            // Afficher un message d'erreur si la date est antérieure à la date actuelle
            showAlert("Erreur de saisie", "La date du séminaire doit être égale ou postérieure à la date actuelle");
            return;
        }

        // Création d'une nouvelle instance de service de Séminaire
        ServiceSeminaire service = new ServiceSeminaire();

        // Récupération des valeurs saisies dans les champs
        int id_userv = Integer.parseInt(iduserasso_id.getText());
        String Descriptionv = description.getText();
        String Nom_associationv = nomassociation.getText();

        // Création d'une nouvelle instance de séminaire avec les valeurs récupérées
        Seminaire newSeminaire = new Seminaire(id_userv, Date_Seminairev, Descriptionv, Nom_associationv);

        // Appel de la méthode d'ajout du service de Séminaire
        service.ajouter(newSeminaire);

        // Charger l'interface AfficherSeminaire.fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherSeminaire.fxml"));
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
    }

    // Méthode utilitaire pour afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }*/



/*
    @FXML
    void Ajouter(ActionEvent event) throws IOException {
            // Création d'une nouvelle instance de service de Seminaire
            ServiceSeminaire service = new ServiceSeminaire();


            int id_userv = Integer.parseInt(iduserasso_id.getText());
            String Date_Seminairev = date_seminar.getText();
            String Descriptionv = description.getText();
            String Nom_associationv = nomassociation.getText();

            // Création d'une nouvelle instance de seminaire avec les valeurs récupérées
            Seminaire newSeminaire = new Seminaire(id_userv, Date_Seminairev, Descriptionv, Nom_associationv );

            // Appel de la méthode d'ajout du service de Seminaire
            service.ajouter(newSeminaire);
        // Charger l'interface AfficherSeminaire.fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherSeminaire.fxml"));
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






