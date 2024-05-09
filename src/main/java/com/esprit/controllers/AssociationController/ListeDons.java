package com.esprit.controllers.AssociationController;

import com.esprit.controllers.LanguageSettings;
import com.esprit.models.Don;
import com.esprit.services.DonService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ListeDons {

    @FXML
    private TableColumn<Don, Date> date_donationCol;

    @FXML
    private TableColumn<Don, String> donateurCol;

    @FXML
    private TableColumn<Don, String> emailCol;

    @FXML
    private TableColumn<Don, Integer> idCol;

    @FXML
    private TableColumn<Don, Integer> id_actionCol;

    @FXML
    private TableColumn<Don, Integer> montantCol1;

    @FXML
    private TableView<Don> list_association;

    private Stage stage;
    private Scene scene;
    private Parent root;

    DonService donService = new DonService();

    @FXML
    void back(MouseEvent event) throws IOException {

        ResourceBundle bundle = ResourceBundle.getBundle("Messages", LanguageSettings.getCurrentLocale());

        Parent root = FXMLLoader.load(getClass().getResource("/AssociationFXML/AdminAssociation.fxml"),bundle);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void initialize() {

        try {

            List<Don> associations = donService.getAll();
            ObservableList<Don> observableList = FXCollections.observableList(associations);

            list_association.setItems(observableList);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            id_actionCol.setCellValueFactory(new PropertyValueFactory<>("id_action"));
            emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
            date_donationCol.setCellValueFactory(new PropertyValueFactory<>("date_donation"));
            donateurCol.setCellValueFactory(new PropertyValueFactory<>("donateur"));
            montantCol1.setCellValueFactory(new PropertyValueFactory<>("montant"));


        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
