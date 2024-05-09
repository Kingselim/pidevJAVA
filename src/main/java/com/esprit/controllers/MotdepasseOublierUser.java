package com.esprit.controllers;

import com.esprit.models.User;
import com.esprit.services.UserService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kotlin.reflect.KAnnotatedElement;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public class MotdepasseOublierUser implements Initializable {

    @FXML
    private TextField email;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        email.setText("");


    }
    @FXML
    void retour(ActionEvent event) {
        try {
            // Fermeture de la fenêtre actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            // Chargement de la vue AffcherCompte.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Seconnecter.fxml"));
            Parent root = loader.load();
            // Création de la scène
            Scene scene = new Scene(root);
            // Création de la nouvelle fenêtre pour afficher la vue AffcherCompte.fxml
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Se connecter");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void envoyer(ActionEvent event) {
        UserService service = new UserService();
        String emailv= email.getText();
        User u = new User();
        u= service.recupererUser(emailv);
        String dataToPass =  generateRandomString(5);;
        if(service.userexist(emailv))
        {
            service.envoyermail(u,dataToPass);
            try {
                // Chargement de la vue AffcherCompte.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Authentificateur.fxml"));
                Parent root = loader.load();
                Authentificateur controller = loader.getController();
                controller.setData(dataToPass,u.getId());
                // Création de la scène
                Scene scene = new Scene(root);
                // Création de la nouvelle fenêtre pour afficher la vue AffcherCompte.fxml
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Authentificateur");
                stage.show();
            }catch (IOException e) {
                e.printStackTrace();
            }

        }else
        {
            Alert alertShowInfo = new Alert(Alert.AlertType.ERROR);
            alertShowInfo.setContentText("Le compte n'existe pas !");
            alertShowInfo.showAndWait();

        }
    }

    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }

}
