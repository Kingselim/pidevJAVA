package com.esprit.controllers;

import com.esprit.models.User;
import com.esprit.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class Authentificateur {

    @FXML
    private TextField authCodeCheck;
    @FXML
    private Button envoyer;

    public void setData(String dataToPass,int idtopass) {

        String data =dataToPass;
        System.out.println(data);

        envoyer.setOnAction(event -> {
            String authcodetest = authCodeCheck.getText();

                if(authcodetest.equals(data))
                {
                    System.out.println("le Auth code est correct !!!!!");
                    User user = new User();
                    UserService userService = new UserService();
                    user = userService.recupererUserAvecId(idtopass);
                    try {
                        // Fermeture de la fenêtre actuelle
                        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        currentStage.close();
                        // Chargement de la vue AffcherCompte.fxml
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ResetPassUser.fxml"));
                        Parent root = loader.load();
                        ResetPassUser controller = loader.getController();
                        controller.setData(idtopass);
                        // Création de la scène
                        Scene scene = new Scene(root);
                        // Création de la nouvelle fenêtre pour afficher la vue AffcherCompte.fxml
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("ResetPassword");
                        stage.show();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }else
                {
                    System.out.println("le authcode est pas correct");
                    Alert alertShowInfo = new Alert(Alert.AlertType.ERROR);
                    alertShowInfo.setContentText("Le Code de verification est faux!");
                    //alertShowInfo.showAndWait();
                    Optional<ButtonType> result = alertShowInfo.showAndWait();                               //controle de sexe
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        throw new RuntimeException(" WARNING :Le Code de verification est faux");
                    }
                }

        });
    }





}
