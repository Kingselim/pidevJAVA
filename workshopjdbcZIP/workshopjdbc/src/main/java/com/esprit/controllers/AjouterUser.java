package com.esprit.controllers;

import com.esprit.models.User;
import com.esprit.models.Wallet;
import com.esprit.services.UserService;
import com.esprit.services.WalletService;
import com.github.sarxos.webcam.util.ImageUtils;
import com.google.protobuf.StringValue;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;



import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.mindrot.jbcrypt.BCrypt;


import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.imageio.ImageIO;

public class AjouterUser implements Initializable {

        @FXML
        private TextField adresse;


        @FXML
        private TextField email;

       // @FXML
        //private TextField etat;
       @FXML
       private ComboBox<String> combosexe;

        @FXML
        private TextField mdp;

        @FXML
        private TextField nom;

        @FXML
        private TextField phone;

        @FXML
        private TextField prenom;
    @FXML
    private DatePicker dtb;

        //@FXML
       // private TextField role;
/*
        @FXML
        private TextField sexe;
*/
        @FXML
        private TextField imagefield;
        private final User u = new User();
        public BufferedImage imageAjouter=null;
    public void setData(String dataToPass) {
        imagefield.setText("image/"+dataToPass);

    }
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle)
        {
            nom.setText("");
            prenom.setText("");
            //dtb.setText("");
            email.setText("");
            phone.setText("");
            //role.setText("");
            //sexe.setText("");
            combosexe.setItems(FXCollections.observableArrayList("Homme","Femme"));
            //mdp.setText("");
            //adresse.setText("");
            //etat.setText("");
            //controle de saisie a implementer ici

        }
        @FXML
        void cree(ActionEvent event) throws IOException, WriterException {
             // Création d'une nouvelle instance de service de compte
             UserService service = new UserService();


             String namev= nom.getText();
             String lastnamev=prenom.getText();

             String phonev=phone.getText();
             String emailv=email.getText();
             String addressv=adresse.getText();
             String passwordv=mdp.getText();
             String imagev=imagefield.getText();
             String sexev= combosexe.getValue();

             String rolev="0";            // ici je donne par defaut la valeur de 0 au role pour le rendre client et 1 si c'est un admin
             String statev="1";            // ici je donne par defaut la valeur de 1 au state pour faire comprendre que le compte est actif

            System.out.println("sexev est egale a :"+sexev);



             //------------------------controle de saisie--------------------------

            if(namev.length()>0)
            {
                System.out.println("le nom est rempli");
            }else
            {
                Alert alertShowInfo = new Alert(Alert.AlertType.ERROR);
                alertShowInfo.setContentText("le champ nom est vide !");
                //alertShowInfo.showAndWait();
                Optional<ButtonType> result = alertShowInfo.showAndWait();                              //controle name
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    throw new RuntimeException(" WARNING :le champ nom est vide ");
                }
            }
            if(lastnamev.length()>0)
            {
                System.out.println("lastname est remplir");
            }else {
                Alert alertShowInfo = new Alert(Alert.AlertType.ERROR);
                alertShowInfo.setContentText("Le Prenom est Vide !");
                //alertShowInfo.showAndWait();
                Optional<ButtonType> result = alertShowInfo.showAndWait();                               //controle lastname vide
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    throw new RuntimeException(" WARNING :Le Prenom est Vide !");
                }
            }
            LocalDate date2 = dtb.getValue();
            System.out.println("la date est egale a ==="+date2);
            String format = "yyyy-MM-dd";
            String dtbv ="";
            if(date2!=null)
            {
                dtbv = dtb.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                if(isValidDate(String.valueOf(dtb.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))), format))
                {
                    System.out.println("La date est valide !");
                }
                else
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
            }
            else
            {
                Alert alertShowInfo = new Alert(Alert.AlertType.ERROR);
                alertShowInfo.setContentText("Le Champ de la date n'est pas rempli");
                alertShowInfo.setTitle("Date Vide");
                //alertShowInfo.showAndWait();                                                                    //controle date
                Optional<ButtonType> result = alertShowInfo.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    throw new RuntimeException(" WARNING :le champs de la date est vide ");
                }
            }

            if(phonev.length()==8)
             {
                 System.out.println("longeur di telephone est ok");
             }else
             {
                 Alert alertShowInfo = new Alert(Alert.AlertType.ERROR);
                 alertShowInfo.setContentText("numéro de téléphone est incompatible !");
                 //alertShowInfo.showAndWait();
                 Optional<ButtonType> result = alertShowInfo.showAndWait();                              //controle phone
                 if (result.isPresent() && result.get() == ButtonType.OK) {
                     throw new RuntimeException(" WARNING :le numero de telephone est incorecte");
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
            if(passwordv.length()>4)
            {
                System.out.println("longeur pass est ok !");
            }else
            {
                Alert alertShowInfo = new Alert(Alert.AlertType.ERROR);
                alertShowInfo.setContentText("Le mot de passe et vulnérable !");
                //alertShowInfo.showAndWait();
                Optional<ButtonType> result = alertShowInfo.showAndWait();                               //controle password
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    throw new RuntimeException(" WARNING :le mot de passe est vulnerable");
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




            //------------------------ FIN controle de saisie--------------------------



             //--------------cryptage du password--------------------
            String salt = BCrypt.gensalt(12); // Génère une chaîne de sel avec la version $2y$
            String hashedPassword = BCrypt.hashpw(passwordv, salt);
           // String hashedPassword = BCrypt.hashpw(passwordv, BCrypt.gensalt(12));
            //String hashedPassword = BCrypt.with(BCrypt.Version.VERSION_2Y).hashToChar(6, passwordv.toCharArray());


            // Création d'une nouvelle instance de compte avec les valeurs récupérées
            User newuser = new User(namev, lastnamev, dtbv, phonev, emailv,addressv,hashedPassword,statev,rolev,imagev,sexev);




            // Appel de la méthode d'ajout du service de compte
            service.ajouter(newuser);



            //---------------Partie dediee a la creation du Walet --------------
            newuser = service.recupererUser(emailv);
            WalletService walletService = new WalletService();
            SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();

            String lastconnection=String.valueOf(java.time.LocalDate.now());
            System.out.println(lastconnection);
            int rate =0;
            int nbrconnection=0;
            int idDeUser =newuser.getId();
            Wallet newWalet = new Wallet(rate,lastconnection,nbrconnection,idDeUser);
            System.out.println("Wallet remplit");
            walletService.ajouter(newWalet);
            //------------------------------- FIN Partie dediee a la creation du Walet --------------


            String pathimage ="C:\\Users\\21650\\Desktop\\3A40\\semaistre 2\\pidevJava\\workshopjdbc\\src\\main\\resources\\images\\selfie"+newuser.getId()+".jpg";
            if (imageAjouter!=null)
            {
                try {

                    ImageIO.write(imageAjouter, ImageUtils.FORMAT_JPG, new File(pathimage));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // Création d'une nouvelle instance de compte avec les valeurs récupérée
                String pathpourleUser = "images/selfie"+newuser.getId()+".jpg";
                newuser = new User(newuser.getId(),namev, lastnamev, dtbv, phonev, emailv,addressv,hashedPassword,statev,rolev,pathpourleUser,sexev); //on rectifie le path de l'image
                service.modifier(newuser);
                System.out.println("la rectification de la photo a bien ete effectuee");
            }


            // Affichage d'un SMS de confirmation de creation de compte
            //String SmsToSend ="Bienvenu chey Givest, Votre Compte a bien été crée !";   // decommenter pour faire fonctionner
            //TwilioSMSUser.sendCustomMessage("+21650111079",SmsToSend);



            //-----------------------Generate du QRcode-------------------------------
            GenerateQRCode Qrcode = new GenerateQRCode();
            //data that we want to store in the QR code
            String str= "Voici les données de votre compte:"+"\nID : "+newuser.getId()+"\nNom : "+newuser.getName()+"\nPrenom : "+newuser.getLastname()+"\nEmail : "+newuser.getEmail()+"\nPhone : "+newuser.getPhone()+"\nAdresse : "+newuser.getAddress();
//path where we want to get QR Code
            String path = "C:\\Users\\21650\\Desktop\\3A40\\semaistre 2\\pidevJava\\workshopjdbc\\src\\main\\resources\\images\\Quote.png";
//Encoding charset to be used
            String charset = "UTF-8";
            Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
//generates QR code with Low level(L) error correction capability
            hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//invoking the user-defined method that creates the QR code

//prints if the QR code is generated

            GenerateQRCode.generateQRcode(str, path, charset, hashMap, 200, 200);
            System.out.println("QR Code created successfully.");

            //-----------------------FIN Generate du QRcode-------------------------------





            // Fermeture de la fenêtre actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            try {
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
    public static boolean isValidDate(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false); // Désactiver la tolérance pour les dates invalides

        try {
            // Tentative d'analyse de la date
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            // La date est invalide
            return false;
        }
    }
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }



        @FXML
        void image(ActionEvent event) {
        imageAjouter = null;
            // select a file from the dialog box
            FileChooser fileChooser = new FileChooser();
            // image file extensions
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files",
                            "*.png", "*.jpg", "*.gif"));
            File selectedFile = fileChooser.showOpenDialog(null);
            String xamppHtdocsPath = "C:\\Users\\21650\\Desktop\\3A40\\semaistre 2\\pidevJava\\workshopjdbc\\src\\main\\resources\\images\\";

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
                imagefield.setText("images/"+selectedFile.getName());
            }

        }
    @FXML
    void photo(ActionEvent event) {
        try {
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close(); // Close the current stage


            // Chargement de la vue AffcherCompte.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WebCamAppLauncher.fxml"));
            Parent root = loader.load();
            Stage newStage = new Stage();
            //newStage.setScene(new Scene(root));

            // Création de la scène
            //Scene scene = new Scene(root);
            WebCamAppLauncher cam = new WebCamAppLauncher();
            cam.finalexternalVariable="255";
            cam.start(newStage);

        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }
    }

        @FXML
        void retour(ActionEvent event) throws IOException {
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


