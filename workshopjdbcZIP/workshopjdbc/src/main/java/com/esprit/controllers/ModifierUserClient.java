package com.esprit.controllers;

import com.esprit.models.User;
import com.esprit.services.UserService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.esprit.controllers.AjouterUser.isValidDate;
import static com.esprit.controllers.AjouterUser.isValidEmail;

public class ModifierUserClient implements Initializable {
    private int idd;
    public void setData(String dataToPass) {
        //label.setText("L'utilisateur avec l'ID : "+dataToPass);
        //System.out.println("la valeur datatopass st"+dataToPass);
        idd= Integer.valueOf(dataToPass);
        //System.out.println("la valeur datatopass st"+idd);
        UserService service = new UserService();
        User user = new User();
        System.out.println(idd);
        //System.out.println("la valeur datatopasssssssssssssss st"+idd);
        user=service.recupererUserAvecId(idd);

        name.setText(user.getName());
        lastname.setText(user.getLastname());
        dtb.setText(user.getDtb());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());
        role.setText(user.getRole());
        sexe.setText(user.getSexe());
        password.setText(user.getPassword());
        address.setText(user.getAddress());
        state.setText(user.getState());
        image.setText(user.getImage());
        id.setText(String.valueOf(user.getId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {


        //controle de saisie a implementer ici

    }

    @FXML
    private TextField address;

    @FXML
    private TextField dtb;

    @FXML
    private TextField email;

    @FXML
    private TextField id;

    @FXML
    private TextField image;

    @FXML
    private TextField lastname;

    @FXML
    private TextField name;

    @FXML
    private TextField password;

    @FXML
    private TextField phone;

    @FXML
    private TextField role;

    @FXML
    private TextField sexe;

    @FXML
    private TextField state;

    @FXML
    void modifier(ActionEvent event) {
// Création d'une nouvelle instance de service de compte
        UserService service = new UserService();
        String namev= name.getText();
        String lastnamev=lastname.getText();
        String dtbv=dtb.getText();
        String phonev=phone.getText();
        String emailv=email.getText();
        String addressv=address.getText();
        String passwordv=password.getText();
        String statev=state.getText();
        String rolev=role.getText();
        String imagev=image.getText();
        //String sexev=sexe.getText();
        String sexev=sexe.getText();
        int iduser = idd;
        //String rolev="0"; // ici je donne par defaut la valeur de 0 au role pour le rendre client et 1 si c'est un admin
        //String statev="1";

        //------------------------controle de saisie--------------------------
        if(phonev.length()<8)
        {
            Alert alertShowInfo = new Alert(Alert.AlertType.ERROR);
            alertShowInfo.setContentText("numéro de téléphone est incompatible !");
            //alertShowInfo.showAndWait();
            Optional<ButtonType> result = alertShowInfo.showAndWait();                              //controle phone
            if (result.isPresent() && result.get() == ButtonType.OK) {
                throw new RuntimeException(" WARNING :le numero de telephone est incorecte");
            }
        }
        if(passwordv.length()<4)
        {
            Alert alertShowInfo = new Alert(Alert.AlertType.ERROR);
            alertShowInfo.setContentText("Le mot de passe et vulnérable !");
            //alertShowInfo.showAndWait();
            Optional<ButtonType> result = alertShowInfo.showAndWait();                               //controle password
            if (result.isPresent() && result.get() == ButtonType.OK) {
                throw new RuntimeException(" WARNING :le mot de passe est vulnerable");
            }
        }
        String format = "yyyy-MM-dd";
        if(isValidDate(dtbv, format))
        {
            System.out.println("La date est valide !");
        }else
        {
            Alert alertShowInfo = new Alert(Alert.AlertType.ERROR);
            alertShowInfo.setContentText("Le format de la date est invalide , veuiller respercter le format :yyyy-MM-dd");
            alertShowInfo.setTitle("Date Invalide");
            //alertShowInfo.showAndWait();                                                                    //controle date
            Optional<ButtonType> result = alertShowInfo.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                throw new RuntimeException(" WARNING :le format de la date est invalide ");
            }
        }
        if (isValidEmail(emailv)) {
            System.out.println("L'e-mail est valide !");
        } else {
            Alert alertShowInfo = new Alert(Alert.AlertType.ERROR);
            alertShowInfo.setContentText("Le format de l'Email est invalide ");
            alertShowInfo.setTitle("Email Invalide");
            //alertShowInfo.showAndWait();                                                                       //controle email
            Optional<ButtonType> result = alertShowInfo.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                throw new RuntimeException(" WARNING :le format de l'email est invalide ");
            }
        }

        //------------------------ FIN controle de saisie--------------------------

        //--------------cryptage du password--------------------
        //le cryptage a lieur si et seulement si le mot de passe a ete changer
        String hashedPassword="";
        if(passwordv.length()<10)
        {
            hashedPassword = BCrypt.hashpw(passwordv, BCrypt.gensalt());
        }else{
            hashedPassword = passwordv;
        }


        // Création d'une nouvelle instance de compte avec les valeurs récupérées
        User newuser = new User(idd,namev, lastnamev, dtbv, phonev, emailv,addressv,hashedPassword,statev,rolev,imagev,sexev);





        // Appel de la méthode d'ajout du service de compte
        service.modifier(newuser);

        // Affichage d'un message de confirmation ou de traitement supplémentaire si nécessaire

        // Fermeture de la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        try {
            // Chargement de la vue AffcherCompte.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MenuClient.fxml"));
            Parent root = loader.load();
            MenuClient controller = loader.getController();
            controller.setData(newuser.getEmail());
            // Création de la scène
            Scene scene = new Scene(root);
            // Création de la nouvelle fenêtre pour afficher la vue AffcherCompte.fxml
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Menu User");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void retour(ActionEvent event) {
        // Fermeture de la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        try {
            // Chargement de la vue AffcherCompte.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MenuUser.fxml"));
            Parent root = loader.load();
            // Création de la scène
            Scene scene = new Scene(root);
            // Création de la nouvelle fenêtre pour afficher la vue AffcherCompte.fxml
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Menu User");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private Label label;
    @FXML
    void imgbtn(ActionEvent event) {
        // select a file from the dialog box
        FileChooser fileChooser = new FileChooser();
        // image file extensions
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        String xamppHtdocsPath = "C:/xampp/htdocs/Image/";

        File destinationFile = new File(xamppHtdocsPath + selectedFile.getName());
        try {
            // Copy the selected file to the htdocs directory
            Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied successfully to: " + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error copying file: " + e.getMessage());
        }

        if (destinationFile != null) {
            String imageFile = destinationFile.toURI().toString();
            imageFile = imageFile.substring(8);
            image.setText(imageFile);
        }
    }
}