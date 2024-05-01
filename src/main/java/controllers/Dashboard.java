package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Dashboard {

    @FXML
    private Button Demande;

    @FXML
    private Button Pret;

    @FXML
    void Demande(ActionEvent event) {
        // Close the current window
       closeWindow();
        loadFXML("/AfficherDemande.fxml");
    }

    @FXML
    void Pret(ActionEvent event) {
        // Close the current window
        closeWindow();
        // Load the Pret page
        loadFXML("/AfficherPret.fxml");
    }

    private void loadFXML(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) Demande.getScene().getWindow();
        stage.close();
    }


    @FXML
    void simulatoron(ActionEvent event) {
        closeWindow();
        // Load the Pret page
        loadFXML("/simulator.fxml");
    }
    @FXML
    private Button statistique;
    @FXML
    void statistiqueon(ActionEvent event) {
        loadFXML("/stat.fxml");
    }
}
