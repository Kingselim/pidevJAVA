package com.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuClient {

    @FXML
    private Button btnmodif;

    @FXML
    private ImageView imagerate;

    @FXML
    private Label labeldate;

    @FXML
    private Label lastname;

    @FXML
    private Label name;

    @FXML
    void goassociation(ActionEvent event) {

    }

    @FXML
    void gocompte(ActionEvent event) {

    }

    @FXML
    void goout(ActionEvent event) {

    }

    @FXML
    void gopret(ActionEvent event) {

    }

    @FXML
    void goseminair(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage
        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherSeminaire.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }



    }

    @FXML
    void gosponsoring(ActionEvent event) {

    }

}
