package com.esprit.controllers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class helloimageview extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Création de l'ImageView avec une image initiale
        ImageView imageView = new ImageView(new Image("logouser.png"));

        // Création d'un StackPane pour placer l'ImageView
        StackPane root = new StackPane();
        root.getChildren().add(imageView);

        // Création de la scène
        Scene scene = new Scene(root, 400, 300);

        // Affichage de la scène
        primaryStage.setScene(scene);
        primaryStage.setTitle("ImageView Example");
        primaryStage.show();

        // Changement de l'image après un certain temps (par exemple, 5 secondes)
        /*new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        // Modification de l'image de l'ImageView
                        imageView.setImage(new Image("https://example.com/updated_image.png"));
                    }
                },
                5000
        );

         */
    }

    public static void main(String[] args) {
       // launch(args);
    }
}
