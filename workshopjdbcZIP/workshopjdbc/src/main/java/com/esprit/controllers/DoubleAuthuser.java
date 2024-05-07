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
                Node source = (Node) event.getSource();
                Stage currentStage = (Stage) source.getScene().getWindow();
                currentStage.close(); // Close the current stage
                // Load and show the new interface
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/MenuAdmin.fxml"));
                    Parent root = loader.load();


                    Stage newStage = new Stage();
                    newStage.setScene(new Scene(root));
                    newStage.show();
                    newStage.setTitle("Menu Admin");
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle exception, if any
                }
            }
        });






    }

}

