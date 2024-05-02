package org.example.test.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        ResourceBundle bundle = ResourceBundle.getBundle("/org/example/test/test/Messages", LanguageSettings.getCurrentLocale());


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("scene1.fxml"),bundle);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setScene(scene);
        stage.setTitle("Givest");
        stage.show();



    }

    public static void main(String[] args) {
        launch();
    }
}