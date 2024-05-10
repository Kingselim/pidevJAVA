package com.esprit.controllers;

import com.esprit.models.Sponsoring;
import com.esprit.services.SponsoringService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AfficherSponsoring {

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField recherche;

    @FXML
    private BarChart<String, Number> barChart;
    private final SponsoringService sponsoringService = new SponsoringService();


    @FXML
    public void initialize() {
        try {
            // Load data from the service and populate the GridPane
            Stat();

            populateGrid();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void populateGrid() throws SQLException {
        List<Sponsoring> sponsorings = sponsoringService.afficher();

        // Add column headers
        gridPane.add(createLabel("ID"), 0, 0);
        gridPane.add(createLabel("Budget"), 1, 0);
        gridPane.add(createLabel("Date Sponsoring"), 2, 0);
        gridPane.add(createLabel("Type"), 3, 0);
        gridPane.add(createLabel(""), 4, 0); // Empty column for the Edit button

        // Add data rows
        int rowIndex = 1;
        for (Sponsoring sponsoring : sponsorings) {
            Button editButton = createEditButton(sponsoring);
            Button deleteButton = createDeleteButton(sponsoring);
            gridPane.add(editButton, 4, rowIndex);
            gridPane.add(deleteButton, 5, rowIndex);
            gridPane.add(createLabel(String.valueOf(sponsoring.getId())), 0, rowIndex);
            gridPane.add(createLabel(String.valueOf(sponsoring.getBudget())), 1, rowIndex);
            gridPane.add(createLabel(String.valueOf(sponsoring.getDatesponsoring())), 2, rowIndex);
            gridPane.add(createLabel(sponsoring.getType()), 3, rowIndex);
            rowIndex++;
        }
    }

    private javafx.scene.control.Label createLabel(String text) {
        javafx.scene.control.Label label = new javafx.scene.control.Label(text);
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: #333333;");
        return label;
    }

        private Button createEditButton(Sponsoring sponsoring) {
            Button editButton = new Button("Edit");
            editButton.setOnAction(event -> {
                openEditInterface(sponsoring);
            });
            return editButton;
        }
    private Button createDeleteButton(Sponsoring sponsoring) {
        Button editButton = new Button("Delete");
        editButton.setOnAction(event -> {
            try {
                sponsoringService.supprimer(sponsoring);
                gridPane.getChildren().clear(); // Clear existing content
                populateGrid(); // Repopulate the grid pane with updated data

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return editButton;
    }

    private void openEditInterface(Sponsoring sponsoring) {
        try {
            // Load the Edit Interface FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierSponsoring.fxml"));
            Parent root = loader.load();

            // Get the controller of the Edit Interface
            ModifierSponsoring modifierSponsoring = loader.getController();

            // Pass the selected Sponsoring object to the Edit Interface controller
            modifierSponsoring.initData(sponsoring,this);

            // Create a new stage for the Edit Interface
            Stage editStage = new Stage();
            editStage.initModality(Modality.APPLICATION_MODAL);
            editStage.setScene(new Scene(root));
            editStage.showAndWait(); // Show the Edit Interface and wait for it to close

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void refreshGrid() {
        try {
            // Clear the grid and repopulate it with updated data
            gridPane.getChildren().clear();
            populateGrid();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void loadPage(String page) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/"+ page + ".fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) recherche.getScene().getWindow();

            // Set the scene to the new FXML file
            stage.setScene(new Scene(root));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void AjouterSponsoringg(ActionEvent event) {
        loadPage("AjouterSponsoring");
    }

    public void btnpdf(ActionEvent actionEvent) {
    }

    public void retour(ActionEvent actionEvent) {
    }

//    private void showAlert(String message) {
//        Alert alert = new Alert(Alert.AlertType.WARNING);
//        alert.setTitle("Avertissement");
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }

    public void Stat() throws SQLException {
        barChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Répartition des Sponsoring");

        try {
            Map<Integer, Integer> sponsoringCounts = sponsoringService.getSponsoringCounts();

            // Add data for each sponsoring
            for (Map.Entry<Integer, Integer> entry : sponsoringCounts.entrySet()) {
                int sponsoringId = entry.getKey();
                int count = entry.getValue();

                // Retrieve sponsoring name from database based on ID
                String sponsoringName = sponsoringService.getSponsoringNameById(sponsoringId);

                // Add data point to series
                series.getData().add(new XYChart.Data<>(sponsoringName, count));
            }

            barChart.getData().add(series);
        } catch (SQLException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }


    public void gouser(ActionEvent actionEvent) {
    }

    public void goassociation(ActionEvent actionEvent) {
    }

    public void gocompte(ActionEvent actionEvent) {
    }


    public void gopret(ActionEvent actionEvent) {
    }


    public void goseminaire(ActionEvent actionEvent) {
    }


    public void gosponsoring(ActionEvent actionEvent) {
    }


    public void logout(ActionEvent actionEvent) {
    }
}
