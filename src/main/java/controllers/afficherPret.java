package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import entities.Pret;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.PretService;

import java.io.IOException;

public class afficherPret {

    @FXML
    private ListView<Pret> listview;
    @FXML
    private Button searchbtn;

    @FXML
    private TextField searchinput;

    private final PretService pretService = new PretService();

    @FXML
    public void initialize() {
        // Retrieve the list of loans from the PretService
        ObservableList<Pret> loans = pretService.getAllPrets();

        // Set the items in the ListView
        listview.setItems(loans);

        // Create a context menu
        ContextMenu contextMenu = new ContextMenu();

        // Create a MenuItem for deleting an item
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(event -> deleteSelectedItem(null));

        // Create a MenuItem for editing an item
        MenuItem editMenuItem = new MenuItem("Edit");
        editMenuItem.setOnAction(this::editSelectedItem);

        // Create a MenuItem for adding a demand
        MenuItem addDemandMenuItem = new MenuItem("Add Demand");
        addDemandMenuItem.setOnAction(event -> {
            Pret selectedPret = listview.getSelectionModel().getSelectedItem();
            if (selectedPret != null) {
                navigateToAddDemande(selectedPret.getId());
            }
        });

        // Add the MenuItems to the ContextMenu
        contextMenu.getItems().addAll(deleteMenuItem, editMenuItem, addDemandMenuItem);

        // Set the ContextMenu to the ListView
        listview.setContextMenu(contextMenu);
    }
    private void navigateToAddDemande(Long pretId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddDemande.fxml"));
            Parent root = loader.load();

            // Pass the pretId to the AddDemande controller
            AddDemande controller = loader.getController();
            controller.initData(pretId);

            // Set the scene
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteSelectedItem(ActionEvent event) {
        Pret selectedItem = listview.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            pretService.deletePret(selectedItem.getId());
            listview.getItems().remove(selectedItem);
        }
    }
    private Long pretId;



    public void editSelectedItem(ActionEvent actionEvent) {
        Pret selectedPret = listview.getSelectionModel().getSelectedItem();
        if (selectedPret != null) {
            try {
                // Load the UpdatePret.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditPret.fxml"));
                Parent root = loader.load();

                // Pass the selected pret's ID to the controller responsible for updating
                UpdatePret controller = loader.getController();
                controller.initData(selectedPret.getId());

                // Set the scene
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    void searchbtnonaction(ActionEvent event) {
        String searchTerm = searchinput.getText().trim();

        ObservableList<Pret> searchResults = FXCollections.observableArrayList(pretService.searchPrets(searchTerm));
        listview.setItems(searchResults);
    }

    private void refreshListView() {
        ObservableList<Pret> loans = pretService.getAllPrets();
        listview.setItems(loans);


    }    @FXML
    void refrechonaction(ActionEvent event) {
        ObservableList<Pret> loans = pretService.getAllPrets();
        listview.setItems(loans);    }


    @FXML
    private Button goback;

    @FXML
    void gobackon(ActionEvent event) {
        loadFXML("/Dashboard.fxml", (Node) event.getSource());
    }

    private void loadFXML(String fxmlFilePath, Node node) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(scene);
           // stage.close(); // Close the current window
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private Button gotoadd;

    @FXML
    void gotoaddon(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddPret.fxml"));
            Parent root = loader.load();

            // Pass the pretId to the AddDemande controller
            AddPret controller = loader.getController();

            // Set the scene
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }    }
}
