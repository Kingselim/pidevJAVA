package com.esprit.controllers;

import com.esprit.models.Souvenir;
import com.esprit.models.User;
import com.esprit.services.SouvenirService;
import com.esprit.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.Optional;

public class ResetPassUser {

    @FXML
    private PasswordField code1;

    @FXML
    private PasswordField code2;

    @FXML
    private Button envoyer;

    public void setData(int idtopass) {
        User user = new User();
        UserService userService = new UserService();
        user = userService.recupererUserAvecId(idtopass);
        User finalUser = user;
        envoyer.setOnAction(event -> {
            String tcode1= code1.getText();
            String tcode2= code2.getText();

            if(tcode1.isEmpty())
            {
                System.out.println("les champs sont manquant");
                Alert alertShowInfo = new Alert(Alert.AlertType.ERROR);
                alertShowInfo.setContentText("Les champs sont manquant!");
                //alertShowInfo.showAndWait();
                Optional<ButtonType> result = alertShowInfo.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    throw new RuntimeException(" WARNING :les champs sont manquant");
                }
            }
            if(tcode1.equals(tcode2))
            {
                System.out.println("les deux code sont correct !!!!!");
                String salt = BCrypt.gensalt(12); // Génère une chaîne de sel avec la version $2y$
                String hashedPassword = BCrypt.hashpw(tcode2, salt);
                finalUser.setPassword(hashedPassword);
                userService.modifier(finalUser);
                //apres la modification du mot de passe de l'utilisateur il faut supprimer se dernier de la table Souvenir pour ne pas avoir de confusion
                SouvenirService souvenirService = new SouvenirService();
                Souvenir souvenir = new Souvenir();
                souvenir = souvenirService.recupererSouvenir(finalUser.getEmail());
                souvenirService.supprimer(souvenir);
                // Fermeture de la fenêtre actuelle
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();


            }else
            {
                System.out.println("le authcode est pas correct");
                Alert alertShowInfo = new Alert(Alert.AlertType.ERROR);
                alertShowInfo.setContentText("Les deux codes ne sont pas identique!");
                //alertShowInfo.showAndWait();
                Optional<ButtonType> result = alertShowInfo.showAndWait();                               //controle de sexe
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    throw new RuntimeException(" WARNING :Les deux codes ne sont pas identique");
                }
            }

        });
    }
}
