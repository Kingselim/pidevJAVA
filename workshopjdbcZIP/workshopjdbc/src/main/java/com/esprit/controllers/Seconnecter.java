package com.esprit.controllers;

import com.esprit.models.Souvenir;
import com.esprit.models.User;
import com.esprit.services.SouvenirService;
import com.esprit.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Seconnecter implements Initializable {

    @FXML
    private TextField email;
    ListView<String> livCountry = new ListView<String>();
    @FXML
    private PasswordField motdepasse;
    @FXML
    private CheckBox boxsouvenir;
    @FXML
    private FlowPane emailpane;
    @FXML
    void membre(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage
        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterUser.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
            newStage.setTitle("Crée Compte");



        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }
    }
    @FXML
    void emailclick(ActionEvent event) {

        System.out.println("Clicked email");
    }


    @FXML
    void oublier(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage
        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/MotdepasseOublierUser.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
            newStage.setTitle("Mot de passe oublié");



        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        SouvenirService souvenirService = new SouvenirService();
        List<Souvenir> souvenirList = new ArrayList<>();
        souvenirList=souvenirService.afficher();


        List<String> listEmailSouvenir = new ArrayList<>();
        for (Souvenir souv:souvenirList) {
            listEmailSouvenir.add(souv.getEmail().toString());
        }
        ObservableList<String> countries = FXCollections.observableList(listEmailSouvenir);
                //observableArrayList(listEmailSouvenir.stream());
        livCountry.setItems(countries);
        livCountry.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        livCountry.setFixedCellSize(22);
        livCountry.setPrefSize(200, 180);

        // si je clique sur une ligne de la liste deroulante des mail de Souvenir
        livCountry.setOnMouseClicked(event -> {
            System.out.println("avec crocher"+livCountry.getSelectionModel().getSelectedItems());
            String valsouvenir =livCountry.getSelectionModel().getSelectedItems().toString();
            if (valsouvenir.startsWith("[") && valsouvenir.endsWith("]")) {
                valsouvenir = valsouvenir.substring(1, valsouvenir.length() - 1);
            }
            System.out.println("sans corcher : "+valsouvenir);
            email.setText(valsouvenir);
            Souvenir OBJsouv = new Souvenir();
            OBJsouv = souvenirService.recupererSouvenir(valsouvenir);
            motdepasse.setText(OBJsouv.getPassword());

        });
        emailpane.getChildren().add(livCountry);

        email.setText("");
        motdepasse.setText("");
        //controle de saisie a implementer ici
        email.setOnMouseClicked(e -> {
            System.out.println("Clicked");
            emailpane.setOpacity(1);


        });
        motdepasse.setOnMouseClicked(e -> {
        System.out.println("Mot de passe Clicked");
        emailpane.setOpacity(0);


    });




    }
    @FXML
    void motdepasseclick(ActionEvent event) {

    }

    @FXML
    void seconnecter(ActionEvent event) {

        //si connOk = 0 l'utilisateur n'existe pas
        //si connOk = 1 le mot de passe est incorrect
        //si connOk = 2 le code ouvre la page Menu Admin
        //si connOk = 3 le code ouvre la page Menu Client
        //si connOk = 4 le compte est bloqué
        UserService service = new UserService();
        int connOK;
        connOK=service.seconnecter(email.getText(), motdepasse.getText());
        System.out.println("connOK ===="+connOK);
        if(connOK==2)
        {
            //ici il s'agit de la connection de l'admin

            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close(); // Close the current stage
            // Load and show the new interface
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/MenuAdmin.fxml"));
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.show();
                newStage.setTitle("Menu");
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exception, if any
            }
            boolean souvbox = boxsouvenir.isSelected();
            if(souvbox)
            {

                System.out.println("la valeur de se souvenir est egale a :"+souvbox);
                SouvenirService souvenirService = new SouvenirService();
                Souvenir newsouvenir = new Souvenir();

                //ici il faut d'abord verifier si l'email est deja dans la table souvenir
                newsouvenir = souvenirService.recupererSouvenir(email.getText());
                if(newsouvenir.getEmail().equals(email.getText()))
                {
                    System.out.println("l'email est deja dans la table de souvenir !");
                }else{
                    // si l'email n'est pas dans la table de souvenir alors on prossède a l'ajout
                    newsouvenir.setEmail(email.getText());
                    newsouvenir.setPassword(motdepasse.getText());
                    newsouvenir.setDate(String.valueOf(java.time.LocalDate.now()));
                    souvenirService.ajouter(newsouvenir);
                }

            }else
            {
                System.out.println("la valeur de se souvenir est egale a :"+souvbox);

            }
        }else if(connOK==3){
            //ici il s'agit de la connection du client
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close(); // Close the current stage
            // Load and show the new interface

            String dataToPass= email.getText();
            SharedData.setVariable(dataToPass);
            try {
               // Parent root = FXMLLoader.load(getClass().getResource("/MenuClient.fxml"));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/MenuClient.fxml"));
                Parent root = loader.load();
                MenuClient controller = loader.getController();
                controller.setData(dataToPass);

                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.show();
                newStage.setTitle("Menu Client");

            } catch (IOException e) {
                e.printStackTrace();
                // Handle exception, if any
            }
            boolean souvbox = boxsouvenir.isSelected();
            if(souvbox)
            {
                System.out.println("la valeur de se souvenir est egale a :"+souvbox);
                SouvenirService souvenirService = new SouvenirService();
                Souvenir newsouvenir = new Souvenir();

                //ici il faut d'abord verifier si l'email est deja dans la table souvenir
                newsouvenir = souvenirService.recupererSouvenir(email.getText());
                if(newsouvenir.getEmail()==null)
                {
                    // si l'email n'est pas dans la table de souvenir alors on prossède a l'ajout
                    System.out.println("l'email n'est pas dans la table de souvenir , le systeme vas l'ajouter !");

                    newsouvenir.setEmail(email.getText());
                    newsouvenir.setPassword(motdepasse.getText());
                    newsouvenir.setDate(String.valueOf(java.time.LocalDate.now()));
                    souvenirService.ajouter(newsouvenir);

                }else{
                    System.out.println("l'email est deja dans la table de souvenir !");
                }
            }else
            {
                System.out.println("la valeur de se souvenir est egale a :"+souvbox);

            }
        }
        else if(connOK==0){
            Alert alertShowInfo = new Alert(Alert.AlertType.WARNING);
            alertShowInfo.setContentText("L'utilisateur n'existe pas !");
            alertShowInfo.showAndWait();

        }
        else if(connOK==1){
            //affiche une alerte si le mdps est incorrect
            Alert alertShowInfo = new Alert(Alert.AlertType.ERROR);
            alertShowInfo.setContentText("Le Mot de passe est incorect !");
            alertShowInfo.showAndWait();
        }
        else if(connOK==4){
            //affiche une alerte si le mdps est incorrect
            Alert alertShowInfo = new Alert(Alert.AlertType.ERROR);
            alertShowInfo.setContentText("Le Compte est Bloqué , veuller contacter GIVEST pour plus d'information!");
            alertShowInfo.showAndWait();
        }
        else
        {
            System.out.println("il y a eu un probleme de connection !");
        }

    }






}
