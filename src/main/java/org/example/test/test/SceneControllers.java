package org.example.test.test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.test.test.models.Association;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class SceneControllers {

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    void goAdmin(ActionEvent event) throws IOException{

        ResourceBundle bundle = ResourceBundle.getBundle("/org/example/test/test/Messages", LanguageSettings.getCurrentLocale());

        Parent root = FXMLLoader.load(getClass().getResource("/org/example/test/test/Admin.fxml"),bundle);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void goClient(ActionEvent event)  throws IOException{

        ResourceBundle bundle = ResourceBundle.getBundle("/org/example/test/test/Messages", LanguageSettings.getCurrentLocale());

        Parent root = FXMLLoader.load(getClass().getResource("/org/example/test/test/Client.fxml"),bundle);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


}



