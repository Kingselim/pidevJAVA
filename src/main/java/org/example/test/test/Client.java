package org.example.test.test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Client {

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    void add_association(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/test/test/AssociationFXML/AddAssociation.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void add_action(ActionEvent event) throws IOException{

        Parent root = FXMLLoader.load(getClass().getResource("AddAction.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void show_actions(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("show_list_actions.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void show_my_actions(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("myActions.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void show_my_associations(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/org/example/test/test/AssociationFXML/MyAssociations.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void translateToEnglish(MouseEvent event) throws IOException{

        LanguageSettings.setCurrentLocale(new Locale("en"));
        ResourceBundle bundle = ResourceBundle.getBundle("/org/example/test/test/Messages", LanguageSettings.getCurrentLocale());

        Parent root = FXMLLoader.load(getClass().getResource("/org/example/test/test/Client.fxml"),bundle);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void translateToFrench(MouseEvent event) throws IOException{

        LanguageSettings.setCurrentLocale(new Locale("fr"));
        ResourceBundle bundle = ResourceBundle.getBundle("/org/example/test/test/Messages", LanguageSettings.getCurrentLocale());

        Parent root = FXMLLoader.load(getClass().getResource("/org/example/test/test/Client.fxml"),bundle);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
