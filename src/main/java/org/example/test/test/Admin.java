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

public class Admin {

    private Stage stage;
    private Scene scene;
    private Parent root;



    @FXML
    void show_associations(ActionEvent event) throws IOException {

        ResourceBundle bundle = ResourceBundle.getBundle("/org/example/test/test/Messages", LanguageSettings.getCurrentLocale());
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/test/test/AssociationFXML/Show_list_association.fxml"),bundle);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void show_requests(ActionEvent event) throws IOException {

        ResourceBundle bundle = ResourceBundle.getBundle("/org/example/test/test/Messages", LanguageSettings.getCurrentLocale());
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/test/test/AssociationFXML/Requests_creation_association.fxml"),bundle);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void translateToEnglish(MouseEvent event) throws IOException{

        LanguageSettings.setCurrentLocale(new Locale("en"));
        ResourceBundle bundle = ResourceBundle.getBundle("/org/example/test/test/Messages", LanguageSettings.getCurrentLocale());

        Parent root = FXMLLoader.load(getClass().getResource("/org/example/test/test/Admin.fxml"),bundle);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void translateToFrench(MouseEvent event) throws IOException{

        LanguageSettings.setCurrentLocale(new Locale("fr"));
        ResourceBundle bundle = ResourceBundle.getBundle("/org/example/test/test/Messages", LanguageSettings.getCurrentLocale());

        Parent root = FXMLLoader.load(getClass().getResource("/org/example/test/test/Admin.fxml"),bundle);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
