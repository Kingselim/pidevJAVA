package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import services.PretService;
import entities.Pret;

import java.io.IOException;
import java.util.List;

public class Stat {

    @FXML
    private PieChart piechartt;

    private PretService pretService;

    public Stat() {
        pretService = new PretService(); // Initialize your service
    }

    @FXML
    public void initialize() {
        // Call the method to get top demanded loans with a specified limit
        List<Pret> topDemandePrets = pretService.getTopDemandePrets(3); // You can specify the limit as needed

        // Clear any existing data in the pie chart
        piechartt.getData().clear();

        // Populate the pie chart with the retrieved data
        for (Pret pret : topDemandePrets) {
            // Add each association as a slice in the pie chart
            piechartt.getData().add(new PieChart.Data(pret.getNomAssociation(), 1)); // You can set any value for the pie chart, e.g., 1
        }
    }

    @FXML
    private Button goback;

    @FXML
    void gobackon(ActionEvent event) {
        loadFXML("/Dashboard.fxml");
    }

    private void loadFXML(String fxmlFilePath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) goback.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
