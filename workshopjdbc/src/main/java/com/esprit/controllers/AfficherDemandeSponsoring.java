package com.esprit.controllers;

import com.esprit.models.DemandeSponsoring;
import com.esprit.services.DemandeSponsoringService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherDemandeSponsoring {

    @FXML
    private GridPane gridPane;

    @FXML
    private Button buttonajout;

    private final DemandeSponsoringService demandeSponsoringService = new DemandeSponsoringService();
    private DemandeSponsoring demandeSponsoring;



    @FXML
    public void initialize() {
        try {
            refreshGrid();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void refreshGrid() throws SQLException {
        gridPane.getChildren().clear(); // Clear existing content
        List<DemandeSponsoring> demandes = demandeSponsoringService.readAll();

        // Add column headers
        gridPane.add(createLabel("ID"), 0, 0);
        gridPane.add(createLabel("Budget"), 1, 0);
        gridPane.add(createLabel("Date Début"), 2, 0);
        gridPane.add(createLabel("Date Fin"), 3, 0);
        gridPane.add(createLabel("User ID"), 4, 0);
        gridPane.add(createLabel("Sponsoring ID"), 5, 0);
        gridPane.add(createLabel("Nom Association"), 6, 0);

        int rowIndex = 1;
        for (DemandeSponsoring demande : demandes) {
            Button editButton = createEditButton(demande);
            Button deleteButton = createDeleteButton(demande);

            gridPane.add(editButton, 7, rowIndex);
            gridPane.add(deleteButton, 8, rowIndex);
            gridPane.add(createLabel(String.valueOf(demande.getId())), 0, rowIndex);
            gridPane.add(createLabel(String.valueOf(demande.getBudget())), 1, rowIndex);
            gridPane.add(createLabel(String.valueOf(demande.getDatedebut())), 2, rowIndex);
            gridPane.add(createLabel(String.valueOf(demande.getDatefin())), 3, rowIndex);
            gridPane.add(createLabel(String.valueOf(demande.getId_user())), 4, rowIndex);
            if(demande.getSponsoring().getType().equals("Autre")){
                gridPane.add(createLabel(String.valueOf(demande.getAutretype())), 5, rowIndex);
            }
            else
            {
                gridPane.add(createLabel(String.valueOf(demande.getSponsoring().getType())), 5, rowIndex);

            }
            gridPane.add(createLabel(String.valueOf(demande.getNomassociation())), 6, rowIndex);
            rowIndex++;
        }
    }

    private Button createEditButton(DemandeSponsoring demande) {
        this.demandeSponsoring = demande;
        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> {
            openEditInterface(demande);
        });
        return editButton;
    }

    private void openEditInterface(DemandeSponsoring demandeSponsoring) {
        try {
            // Load the ModifierDemandeSponsoring FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionEditDSponsoring.fxml"));
            Parent root = loader.load();

            // Get the controller of the ModifierDemandeSponsoring
            ModifierDemandeSponsoring modifierController = loader.getController();

            // Pass the selected DemandeSponsoring object to the ModifierDemandeSponsoring controller
            modifierController.initData(demandeSponsoring,this);

            // Create a new stage for the ModifierDemandeSponsoring
            Stage editStage = new Stage();
            editStage.initModality(Modality.APPLICATION_MODAL);
            editStage.setScene(new Scene(root));
            editStage.showAndWait(); // Show the ModifierDemandeSponsoring and wait for it to close

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private javafx.scene.control.Label createLabel(String text) {
        javafx.scene.control.Label label = new javafx.scene.control.Label(text);
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: #333333;");
        return label;
    }

    private Button createDeleteButton(DemandeSponsoring demandeSponsoring) {
        Button editButton = new Button("Delete");
        editButton.setOnAction(event -> {
            try {
                demandeSponsoringService.supprimer(demandeSponsoring);
                refreshGrid(); // Refresh the grid after deletion

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return editButton;
    }

    public void ajoutdemande(javafx.event.ActionEvent actionEvent) {
        //loadPage("GestionDemandeSponsoring");
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


    public void gouser(ActionEvent actionEvent) {
    }

    public void btnpdf(ActionEvent actionEvent) {
    }

    public void retour(ActionEvent actionEvent) {
    }
}
