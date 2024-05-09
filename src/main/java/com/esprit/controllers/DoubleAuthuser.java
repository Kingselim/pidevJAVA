package com.esprit.controllers;

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

public class DoubleAuthuser {

    @FXML
    private TextField codeAverifier;

    @FXML
    private Button verifier;

    public void setData(String datatopass) {
        String codeDuSysteme = datatopass;


        System.out.println("le code a double auth est : "+codeDuSysteme);
        verifier.setOnAction(event -> {
            String codeinserer = codeAverifier.getText();
            System.out.println("ntb verifier est appuyer");
            if(codeinserer.isEmpty())
            {
                System.out.println("les champs sont manquant");
                Alert alertShowInfo = new Alert(Alert.AlertType.ERROR);
                alertShowInfo.setContentText("Les champs sont manquant!");
                //alertShowInfo.showAndWait();
                Optional<ButtonType> result = alertShowInfo.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    throw new RuntimeException(" WARNING :les champs sont manquant");
                }
            } else if (codeDuSysteme.equals(codeinserer)) {
                System.out.println("lee code est bon");
                try {
                    // Fermeture de la fenêtre actuelle
                    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    currentStage.close();

                    // Chargement de la vue AffcherCompte.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/MenuAdmin.fxml"));
                    Parent root = loader.load();
                    // Création de la scène
                    Scene scene = new Scene(root);
                    // Création de la nouvelle fenêtre pour afficher la vue AffcherCompte.fxml
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Menu Admin");
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else
            {
                System.out.println("le code est faux");
                Alert alertShowInfo = new Alert(Alert.AlertType.ERROR);
                alertShowInfo.setContentText("Le code est faux!");
                //alertShowInfo.showAndWait();
                Optional<ButtonType> result = alertShowInfo.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    throw new RuntimeException(" WARNING :le code dest faux");
                }            }
        });






    }

}

