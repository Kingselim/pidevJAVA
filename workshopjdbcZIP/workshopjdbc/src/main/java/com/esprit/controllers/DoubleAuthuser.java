package com.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

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
            }
        });






    }

}

