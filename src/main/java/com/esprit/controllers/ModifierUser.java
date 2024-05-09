package com.esprit.controllers;

import com.esprit.models.User;
import com.esprit.models.Wallet;
import com.esprit.services.UserService;
import com.esprit.services.WalletService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

public class ModifierUser implements Initializable {
    private int idd;
    public void setData(String dataToPass) {
        //label.setText("L'utilisateur avec l'ID : "+dataToPass);
        //System.out.println("la valeur datatopass st"+dataToPass);
        idd= Integer.valueOf(dataToPass);
        UserService service = new UserService();
        User user = new User();
        System.out.println(idd);
        user=service.recupererUserAvecId(idd);  //ici j'ai recuperer le user avec idd comme ID

        name.setText(user.getName());
        lastname.setText(user.getLastname());
        dtb.setText(user.getDtb());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());
        combosexe.setItems(FXCollections.observableArrayList("Homme","Femme"));
        if(user.getSexe().equals("0"))
        {
            combosexe.setValue("Femme");
        }
        if(user.getSexe().equals("1"))
        {
            combosexe.setValue("Homme");
        }

        address.setText(user.getAddress());
        image.setText(user.getImage());
        id.setText(String.valueOf(user.getId()));

        WalletService walletService = new WalletService();
        Wallet walletamodifier = new Wallet();
        walletamodifier = walletService.chercherWallet(user.getId());

        Wallet finalWalletamodifier = walletamodifier;
        modifwallet.setOnAction(event -> {
            try {
                // Fermeture de la fenêtre actuelle
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();

                String dataToPasss = String.valueOf(finalWalletamodifier.getId());
                SharedData.setVariable(dataToPasss);

                // Chargement de la vue AffcherCompte.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierWallet.fxml"));
                Parent root = loader.load();

                ModifierWallet controller = loader.getController();
                controller.setData(dataToPasss);

                // Création de la scène
                Scene scene = new Scene(root);
                // Création de la nouvelle fenêtre pour afficher la vue AffcherCompte.fxml
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Page de Modification");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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
    private TextField phone;

    @FXML
    private ComboBox<String> combosexe;



    @FXML
    private Button modifwallet;

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
        String imagev=image.getText();
        String sexev=combosexe.getValue();
        int iduser = idd;

        //String rolev="0"; // ici je donne par defaut la valeur de 0 au role pour le rendre client et 1 si c'est un admin
        //String statev="1";

        //------------------------controle de saisie--------------------------
        if(phonev.length()!=8)
        {
            Alert alertShowInfo = new Alert(Alert.AlertType.ERROR);
            alertShowInfo.setContentText("numéro de téléphone est incompatible !");
            //alertShowInfo.showAndWait();
            Optional<ButtonType> result = alertShowInfo.showAndWait();                              //controle phone
            if (result.isPresent() && result.get() == ButtonType.OK) {
                throw new RuntimeException(" WARNING :le numero de telephone est incorecte");
            }
        }
        if(sexev!=null)
        {
            if(sexev.equals("Homme"))
            {
                sexev="1";
            } else if (sexev.equals("Femme")) {
                sexev="0";
            }
        }
        else{
            Alert alertShowInfo = new Alert(Alert.AlertType.ERROR);
            alertShowInfo.setContentText("Le Sexe n'est pas reseignée !");
            //alertShowInfo.showAndWait();
            Optional<ButtonType> result = alertShowInfo.showAndWait();                               //controle de sexe
            if (result.isPresent() && result.get() == ButtonType.OK) {
                throw new RuntimeException(" WARNING :la valeur de Sexe n'est pas remplie");
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
        if(addressv.length()>1)
        {
            System.out.println("longeur address est ok !");
        }else
        {
            Alert alertShowInfo = new Alert(Alert.AlertType.ERROR);
            alertShowInfo.setContentText("L'adresse n'est pas rensegnée !");
            //alertShowInfo.showAndWait();
            Optional<ButtonType> result = alertShowInfo.showAndWait();                               //controle address
            if (result.isPresent() && result.get() == ButtonType.OK) {
                throw new RuntimeException(" WARNING :le champ de l'adresse est manquant !");
            }
        }

        //------------------------ FIN controle de saisie--------------------------


        User user = new User();
        user=service.recupererUserAvecId(idd);  //ici j'ai recuperer le user avec idd comme ID

        // Création d'une nouvelle instance de compte avec les valeurs récupérées
        User newuser = new User(idd,namev, lastnamev, dtbv, phonev, emailv,addressv,user.getPassword(), user.getState(), user.getRole(), imagev,sexev);

        // Appel de la méthode d'ajout du service de compte
        service.modifier(newuser);

        // Affichage d'un message de confirmation ou de traitement supplémentaire si nécessaire

        // Fermeture de la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        try {
            // Chargement de la vue AffcherCompte.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUser.fxml"));
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

    @FXML
    void retour(ActionEvent event) {
        // Fermeture de la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        try {
            // Chargement de la vue AffcherCompte.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUser.fxml"));
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
