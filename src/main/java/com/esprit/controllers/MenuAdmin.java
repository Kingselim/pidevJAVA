package com.esprit.controllers;

import com.esprit.models.User;
import com.esprit.services.UserService;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MenuAdmin implements Initializable{
    @FXML
    private BarChart<?, ?> barchart;

    @FXML
    private CategoryAxis x;
    @FXML
    private Label numberofassociations;

    @FXML
    private Label numberofseminar;

    @FXML
    private Label numberofsponsoring;
    @FXML
    private Label numberofusers;



    @FXML
    private NumberAxis y;
    @FXML
    private PieChart pieChart;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserService userService = new UserService();
        List<User> userl = new ArrayList<>();
        // User user = new User();




        //------------stats user banni / non banni------------

        userl = userService.afficherUserBanni();
        int nbrbanni=0;
        for (User user:userl
        ) {
            nbrbanni++;

        }

        userl = userService.afficher();
        int nbrnonBanni=0;
        for (User user:userl
        ) {
            nbrnonBanni++;

        }
        // nombre total d'utilisateurs
        int nbrTotalUser=0;
        nbrTotalUser=nbrbanni+nbrnonBanni;
        numberofusers.setText(String.valueOf(nbrTotalUser));
        //------------stats user homme / femme ------------

//ici c'est le code pour la PieChart
        List<User> userlH = new ArrayList<>();
        List<User> userlF = new ArrayList<>();
        userlF=userService.recupererUseFemme();
        int nbrfemme=0;
        for (User user:userlF
        ) {
            nbrfemme++;

        }
        userlH=userService.recupererUseHomme();
        int nbrhomme=0;
        for (User user:userlH
        ) {
            nbrhomme++;

        }
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Femme", nbrfemme),
                        new PieChart.Data("Homme", nbrhomme));



        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), "", data.pieValueProperty()
                        )
                )
        );
        //Setting the title of the Pie chart

        pieChart.getData().addAll(pieChartData);


        XYChart.Series set1 = new XYChart.Series<>();
        XYChart.Series set2 = new XYChart.Series<>();
        set1.getData().add(new XYChart.Data("Compte Bannis",nbrbanni));
        set2.getData().add(new XYChart.Data("Compte Actifs",nbrnonBanni));


        barchart.getData().addAll(set1,set2);










    }

    @FXML
    void goassociation(ActionEvent event) throws IOException {
        Stage stage;
        Scene scene;

        ResourceBundle bundle = ResourceBundle.getBundle("Messages", LanguageSettings.getCurrentLocale());
        Parent root = FXMLLoader.load(getClass().getResource("/AssociationFXML/AdminAssociation.fxml"),bundle);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    void gocompte(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage
        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterUser.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }
    }

    @FXML
    void gopret(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage
        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterUser.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }
    }

    @FXML
    void goseminaire(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage
        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterUser.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }
    }

    @FXML
    void gosponsoring(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterUser.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            WebCamAppLauncher cam = new WebCamAppLauncher();
            cam.start(newStage);
           // newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }



    }

    @FXML
    void gouser(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage
        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherUser.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }
    }
    @FXML
    void logout(ActionEvent event) {
        try {
            // Fermeture de la fenêtre actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            // Chargement de la vue AffcherCompte.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Seconnecter.fxml"));
            Parent root = loader.load();
            // Création de la scène
            Scene scene = new Scene(root);
            // Création de la nouvelle fenêtre pour afficher la vue AffcherCompte.fxml
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Se connecter");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
