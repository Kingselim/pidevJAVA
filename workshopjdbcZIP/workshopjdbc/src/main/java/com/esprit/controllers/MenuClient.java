package com.esprit.controllers;

import com.esprit.models.User;
import com.esprit.models.Wallet;
import com.esprit.services.UserService;
import com.esprit.services.WalletService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.scene.image.ImageView;
public class MenuClient implements Initializable {
    @FXML
    private Label labeldate;
    @FXML
    private Label lastname;
    @FXML
    private Label name;
    @FXML
    private ImageView imagerate;
    @FXML
    private Button btnmodif;
    @FXML
    private ImageView profileimage;
    public void setData(String dataToPass) {
        //label.setText("L'utilisateur avec l'ID : "+dataToPass);
        //System.out.println("la valeur datatopass st"+dataToPass);

        String emailpasse = dataToPass;
        //System.out.println("la valeur datatopass st"+idd);
        UserService service = new UserService();
        User user = new User();

        //System.out.println("la valeur datatopasssssssssssssss st"+idd);
        user=service.recupererUser(emailpasse);

        name.setText(user.getName());
        lastname.setText(user.getLastname());
        //------------ a chaque connection il y a des modification au sein du WALLET---------------------
        int idUserpourlarecherche = user.getId();
        WalletService walletService = new WalletService();
        Wallet wallet = walletService.chercherWallet(idUserpourlarecherche);

        int oldNbrConnection= wallet.getNbrconnection();
        String oldLastconnection = wallet.getLastconnection();



        File fileProfile = new File(user.getImage());
        Image myImageProfile = new Image(String.valueOf(fileProfile));
        profileimage.setImage(myImageProfile);
        profileimage.setFitWidth(100);



        SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateAvant = SimpleDateFormat.parse(oldLastconnection);
            Date dateApres = SimpleDateFormat.parse(String.valueOf(LocalDate.now()));
            long diff = dateApres.getTime() - dateAvant.getTime();
            float res = (diff / (1000*60*60*24));
            System.out.println("Nombre de jours entre les deux dates est: "+res);
            if(res>7)
            {
                wallet.setRate(0);
                oldNbrConnection=0;
                File file = new File("images/rate0.png");
                Image myImage = new Image(String.valueOf(file));
                imagerate.setImage(myImage);
            }
            if(res<7 && wallet.getNbrconnection()<5)
            {
                wallet.setRate(1);
                File file = new File("images/rate1.png");
                Image myImage = new Image(String.valueOf(file));
                imagerate.setImage(myImage);
            }
            if(res<7 && wallet.getNbrconnection()>5 && wallet.getNbrconnection()<10)
            {
                wallet.setRate(3);
                File file = new File("images/rate3.png");
                Image myImage = new Image(String.valueOf(file));
                imagerate.setImage(myImage);
            }
            if(res<7 && wallet.getNbrconnection()>10 )
            {
                wallet.setRate(5);
                File file = new File("images/rate5.png");
                Image myImage = new Image(String.valueOf(file));
                imagerate.setImage(myImage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        int newNbrConnection=oldNbrConnection+1;
        System.out.println("le nombre de connection selevre a :"+newNbrConnection);
        wallet= new Wallet(wallet.getId(),wallet.getRate(), String.valueOf(LocalDate.now()),newNbrConnection, wallet.getIdofuser_id());
        System.out.println("dans le wallet cree nbconn ==="+wallet.getNbrconnection());
        walletService.modifier(wallet);
        //-----------------------FIN implementation du WALLET-------------------------------

        btnmodif.setOnAction(event -> {
            try {
                // Fermeture de la fenêtre actuelle
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();

                //String dataToPass= String.valueOf(user.getId());

                SharedData.setVariable(String.valueOf(idUserpourlarecherche));
                // Chargement de la vue AffcherCompte.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierUserClient.fxml"));
                Parent root = loader.load();

                ModifierUserClient controller = loader.getController();
                controller.setData(String.valueOf(idUserpourlarecherche));

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
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        labeldate.setText(String.valueOf(java.time.LocalDate.now()));


        // Chargez votre nouvelle image

        //imagerate = new ImageView(imProfile);

    }

    @FXML
    void goassociation(ActionEvent event) {

    }


    @FXML
    void gocompte(ActionEvent event) {

    }


    @FXML
    void goout(ActionEvent event) {
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

    @FXML
    void gopret(ActionEvent event) {

    }

    @FXML
    void goseminair(ActionEvent event) {

    }

    @FXML
    void gosponsoring(ActionEvent event) {

    }

}
