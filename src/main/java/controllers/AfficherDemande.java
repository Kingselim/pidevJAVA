package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import services.DemandeService;
import entities.Demande;

import java.io.IOException;
import java.util.List;

public class AfficherDemande {


    @FXML
    private Button btnsearch;
    @FXML
    private TextField inputsearch;
    @FXML
    private ListView<Demande> demandelistview;

    private final DemandeService demandeService = new DemandeService();

    @FXML
    void initialize() {
        loadDemandes();

        ContextMenu contextMenu = new ContextMenu();

        MenuItem deleteMenuItem = new MenuItem("Supprimer la demande");
        deleteMenuItem.setOnAction(event -> deleteSelectedDemande());

        MenuItem updateMenuItem = new MenuItem("Modifier la demande");
        updateMenuItem.setOnAction(event -> updateSelectedDemande());

        contextMenu.getItems().addAll(deleteMenuItem, updateMenuItem);

        demandelistview.setContextMenu(contextMenu);

        // Listen for right-click event on demand in ListView
        demandelistview.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(demandelistview, event.getScreenX(), event.getScreenY());
            }
        });
    }

    private void updateSelectedDemande() {
        Demande selectedDemande = demandelistview.getSelectionModel().getSelectedItem();
        if (selectedDemande != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateDemande.fxml"));
                Parent root = loader.load();

                UpdateDemande controller = loader.getController();
                controller.initData(selectedDemande);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

                // Close the context menu
                demandelistview.getContextMenu().hide();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    private void loadDemandes() {
        demandelistview.getItems().clear();


        demandelistview.getItems().addAll(demandeService.getAllDemandes());
    }

    private void deleteSelectedDemande() {
        Demande selectedDemande = demandelistview.getSelectionModel().getSelectedItem();

        if (selectedDemande != null) {
            demandeService.deleteDemande(selectedDemande.getId());

            ObservableList<Demande> items = demandelistview.getItems();
            items.remove(selectedDemande);
        }
    }



    @FXML
    void btnsearchonaction(ActionEvent event) {
        String searchCriteria = inputsearch.getText().trim();
        if (!searchCriteria.isEmpty()) {
            List<Demande> searchResults = demandeService.searchDemandes(searchCriteria);
            if (!searchResults.isEmpty()) {
                demandelistview.getItems().clear();
                demandelistview.getItems().addAll(searchResults);
                // Notification for successful search
                Notifications.create()
                        .title("Search Successful")
                        .text("Found " + searchResults.size() + " results.")
                        .showInformation();
            } else {
                // Notification for no search results found
                Notifications.create()
                        .title("No Results Found")
                        .text("No results found for the search criteria.")
                        .showWarning();
            }
        } else {
            loadDemandes();
        }
    }
    @FXML
    void refrechbtnon(ActionEvent event) {
        loadDemandes(); // Reload all demands
    }

    @FXML
    private Button gotodash;





    public void gotodashon(ActionEvent actionEvent) {
        try {
            // Load the afficherdemande.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
            Parent root = loader.load();

            // Get the stage from the button's scene
            Stage stage = (Stage) gotodash.getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }

