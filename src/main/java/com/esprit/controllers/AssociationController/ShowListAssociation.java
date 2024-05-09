package com.esprit.controllers.AssociationController;


import com.esprit.controllers.LanguageSettings;
import com.esprit.models.Association;
import com.esprit.services.AssociationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ShowListAssociation {



    @FXML
    private TableColumn<Association, Integer> id_userCol;


    @FXML
    private TableColumn<Association, String> SolcialMedCol;

    @FXML
    private TableColumn<Association, String> adresseCol;

    @FXML
    private TableColumn<Association, String> emailCol;

    @FXML
    private TableColumn<Association, String> nameCol;

    @FXML
    private TableColumn<Association, String> phoneCol;

    @FXML
    private TableView<Association> list_association;

    @FXML
    private TextField KeywordTextFiled;

    private final AssociationService as = new AssociationService();

    private Stage stage;
    private Scene scene;
    private Parent root;

    int myIndex;
    int id;
    int[] ids;

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
    void OnDelete(ActionEvent event) {

        myIndex = list_association.getSelectionModel().getSelectedIndex();

        id = Integer.parseInt(String.valueOf(list_association.getItems().get(myIndex).getId()));
        try {
            as.delete(id);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Association deleted!");
            alert.showAndWait();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AssociationFXML/Show_list_association.fxmll"));
            Parent root = loader.load();
            Scene currentScene = ((Node) event.getSource()).getScene();
            currentScene.setRoot(root);

        }catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void OnUpdate(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AssociationFXML/UpdateAssociation_Admin.fxml"));
        Parent root = loader.load();
        UpdateAssociation controller = loader.getController();
        myIndex = list_association.getSelectionModel().getSelectedIndex();
        id = ids[myIndex];
        String direction = "Show_list_association.fxml";
        controller.initData(id, direction);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    void initialize() {

        try {

            List<Association> associations = as.getDisabledAssociations(1);
            ObservableList<Association> observableList = FXCollections.observableList(associations);

            ids = new int[associations.size()];

            for (int i = 0; i < associations.size(); i++) {
                ids[i] = associations.get(i).getId();
            }

            list_association.setItems(observableList);

            id_userCol.setCellValueFactory(new PropertyValueFactory<>("id_user"));
            SolcialMedCol.setCellValueFactory(new PropertyValueFactory<>("facebook_adresse"));
            adresseCol.setCellValueFactory(new PropertyValueFactory<>("city"));
            emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
            phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

            //Initial filtered list
            FilteredList<Association> filteredList = new FilteredList<>(observableList, p -> true);

            KeywordTextFiled.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(association -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    // Filter matches name or city
                    if (association.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches name
                    } else if (association.getCity().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches city
                    }
                    return false; // Does not match.
                });
            });

            SortedList<Association> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(list_association.comparatorProperty());
            list_association.setItems(sortedList);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        list_association.setRowFactory(tv -> {
            TableRow<Association> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    myIndex = list_association.getSelectionModel().getSelectedIndex();
                    id = Integer.parseInt(String.valueOf(list_association.getItems().get(myIndex).getId()));
                }
            });
            return myRow;
        });
    }



    @FXML
    void onShowDetails(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AssociationFXML/ShowDetailsAssociation.fxml"));


        Parent root = loader.load();

        ShowDetailsAssociation controller = loader.getController();

        myIndex = list_association.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(list_association.getItems().get(myIndex).getId()));
        Association association = as.getById(id);

        controller.initData(list_association.getItems().get(myIndex));

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void translateToEnglish(MouseEvent event) throws IOException{

        LanguageSettings.setCurrentLocale(new Locale("en"));
        ResourceBundle bundle = ResourceBundle.getBundle("Messages", LanguageSettings.getCurrentLocale());

        Parent root = FXMLLoader.load(getClass().getResource("/AssociationFXML/Show_list_association.fxml"),bundle);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void translateToFrench(MouseEvent event) throws IOException{

        LanguageSettings.setCurrentLocale(new Locale("fr"));
        ResourceBundle bundle = ResourceBundle.getBundle("Messages",LanguageSettings.getCurrentLocale());

        Parent root = FXMLLoader.load(getClass().getResource("/AssociationFXML/Show_list_association.fxml"),bundle);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}


