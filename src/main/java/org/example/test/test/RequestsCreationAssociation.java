package org.example.test.test;

import javafx.beans.property.SimpleStringProperty;
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
import org.example.test.test.models.Association;
import org.example.test.test.services.AssociationService;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

public class RequestsCreationAssociation {

    @FXML
    private TableColumn<Association, String> StatusCol;

    @FXML
    private TableColumn<Association, String> adresseCol;


    @FXML
    private TableColumn<Association, String> emailCol;

    @FXML
    private TableColumn<Association, Integer> id_userCol;


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

    @FXML
    void back(MouseEvent event) throws IOException {

        ResourceBundle bundle = ResourceBundle.getBundle("/org/example/test/test/Messages", LanguageSettings.getCurrentLocale());
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/test/test/Admin.fxml"),bundle);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void OnActive(ActionEvent event) {
        Association assoc = as.getById(id);
        try {
            as.ActivateAssociation(assoc);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Activated Association!");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/test/test/AssociationFXML/Requests_creation_association.fxml"));
            Parent root = loader.load();
            Scene currentScene = ((Node) event.getSource()).getScene();
            currentScene.setRoot(root);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void OnDelete(ActionEvent event) {
        try {
            as.delete(id);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Request deleted !");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/test/test/AssociationFXML/Requests_creation_association.fxml"));
            Parent root = loader.load();
            Scene currentScene = ((Node) event.getSource()).getScene();
            currentScene.setRoot(root);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onShowDetails(ActionEvent actionEvent) {

    }

    @FXML
    void initialize() {

        try {

            List<Association> associations = as.getDisabledAssociations(0)   ;

            ObservableList<Association> observableList = FXCollections.observableList(associations);

            list_association.setItems(observableList);

            id_userCol.setCellValueFactory(new PropertyValueFactory<>("id_user"));
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

        list_association.setRowFactory( tv -> {
            TableRow<Association> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty()))
                {
                    myIndex =  list_association.getSelectionModel().getSelectedIndex();

                    id = Integer.parseInt(String.valueOf(list_association.getItems().get(myIndex).getId()));

                    /*txtName.setText(table.getItems().get(myIndex).getName());
                    txtMobile.setText(table.getItems().get(myIndex).getMobile());
                    txtCourse.setText(table.getItems().get(myIndex).getCourse());*/
                }
            });

            return myRow;
        });
    }
}
