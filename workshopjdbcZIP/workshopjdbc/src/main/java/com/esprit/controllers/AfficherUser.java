package com.esprit.controllers;
import com.esprit.models.User;
import com.esprit.services.UserService;
import com.esprit.services.WalletService;
import com.google.protobuf.StringValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherUser implements Initializable {

    @FXML
    private FlowPane flowpaneaff;
    @FXML
    private Button rafraichir;

    @FXML
    private TextField recherche;
    public void initialize(URL url, ResourceBundle resourceBundle)  {

        UserService service = new UserService();
        WalletService walletService =new WalletService();
        List<User> userl = new ArrayList<>();
        userl = service.afficher();
        System.out.println(userl.get(0));
        int nbruser = (int) userl.stream().count();
        int i=0;
        flowpaneaff.setHgap(10);
        flowpaneaff.setVgap(10);
        for (User user: userl) {
            Label labelid = new Label(String.valueOf(user.getId()));
            labelid.setPrefWidth(100);

            Label label = new Label(user.getName());
            label.setPrefWidth(100);

            Label labelPrenom = new Label(user.getLastname());
            labelPrenom.setPrefWidth(100);

            Label labelEmail = new Label(user.getEmail());
            labelEmail.setPrefWidth(100);


            Button buttonBan = new Button("Bannir");
                buttonBan.setOnAction(event -> {
                    //walletService.supprimerSelonIdUser(user.getId());
                    //service.supprimer(user);
                    user.setState("0");
                    service.modifier(user);
                    flowpaneaff.getChildren().clear();
                    initialize(url,resourceBundle);
                });
            rafraichir.setOnAction(event -> {
                flowpaneaff.getChildren().clear();
                initialize(url,resourceBundle);
            });

            Button buttonmodif = new Button("Modifier");
            buttonmodif.setOnAction(event -> {
                try {
                    // Fermeture de la fenêtre actuelle
                    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    currentStage.close();

                    String dataToPass= String.valueOf(user.getId());
                    SharedData.setVariable(dataToPass);
                    // Chargement de la vue AffcherCompte.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierUser.fxml"));
                    Parent root = loader.load();

                    ModifierUser controller = loader.getController();
                    controller.setData(dataToPass);

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
            flowpaneaff.getChildren().add(label);
            flowpaneaff.getChildren().add(labelPrenom);
            flowpaneaff.getChildren().add(labelEmail);
            flowpaneaff.getChildren().add(buttonBan);
            flowpaneaff.getChildren().add(buttonmodif);
        }

        /*
        while (i > 0) {
            // Création d'un label pour chaque donnée récupérée
            Label label = new Label(userl[i]);
            flowpaneaff.getChildren().add(label);
        }
*/

    }

    @FXML
    void ajouter(ActionEvent event) {
        try {
            // Fermeture de la fenêtre actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            // Chargement de la vue AffcherCompte.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterUser.fxml"));
            Parent root = loader.load();
            // Création de la scène
            Scene scene = new Scene(root);
            // Création de la nouvelle fenêtre pour afficher la vue AffcherCompte.fxml
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Menu Admin");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void chercher(ActionEvent event) {

    }

    @FXML
    void retour(ActionEvent event) {
        try {
            // Fermeture de la fenêtre actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            // Chargement de la vue AffcherCompte.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MenuAdmin.fxml"));
            Parent root = loader.load();
            // Création de la scène
            Scene scene = new Scene(root);
            // Création de la nouvelle fenêtre pour afficher la vue AffcherCompte.fxml
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Menu Admin");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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

    @FXML
    void ouvrirstat(ActionEvent event) {
        try {
            // Fermeture de la fenêtre actuelle
//            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            currentStage.close();

            // Chargement de la vue AffcherCompte.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/statUser.fxml"));
            Parent root = loader.load();
            // Création de la scène
            Scene scene = new Scene(root);
            // Création de la nouvelle fenêtre pour afficher la vue AffcherCompte.fxml
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Statistique des Utilisateurs");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void archive(ActionEvent event) {
        try {

            // Chargement de la vue AffcherCompte.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserArchive.fxml"));
            Parent root = loader.load();
            // Création de la scène
            Scene scene = new Scene(root);
            // Création de la nouvelle fenêtre pour afficher la vue AffcherCompte.fxml
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Archive des utilisateur Bannis");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnpdf(ActionEvent event) {
//-----------------------Generate du PDF-----------------------------------
        try {
            // Création d'un nouveau document PDF
            PDDocument document = new PDDocument();

            // Ajout d'une nouvelle page
            PDPage page = new PDPage();
            document.addPage(page);

            // Création d'un objet PDPageContentStream pour écrire du contenu sur la page
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Définition de la police et de la taille du texte
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);
            int heightPDF =700;
            // Positionnement du texte sur la page
            contentStream.beginText();
            contentStream.newLineAtOffset(100,heightPDF); // Position X=100, Y=700
            contentStream.showText("Bonjour, ceci est un exemple de PDF généré avec Apache PDFBox ");
            //contentStream.endText();
            // Fermeture du PDPageContentStream
            contentStream.endText();

            heightPDF =680;
            contentStream.beginText();
            contentStream.newLineAtOffset(100,heightPDF); // Position X=100, Y=700
            contentStream.showText("Id               Nom             Prenom             Email");
            contentStream.endText();
            UserService service = new UserService();
            List<User> userl = new ArrayList<>();
            userl = service.afficher();
            for (User user: userl) {
                heightPDF=heightPDF-20;
                System.out.println(heightPDF);

                contentStream.beginText();
                contentStream.newLineAtOffset(100, heightPDF); // Position du deuxième texte (sous le premier)

                contentStream.showText(user.getId()+"               "+user.getName()+"          "+user.getLastname()+"          "+user.getEmail());
                contentStream.endText();
                //

            }
            contentStream.close();
            // Sauvegarde du document PDF
            document.save("C:\\Users\\21650\\Desktop\\3A40\\semaistre 2\\pidevJava\\workshopjdbc\\src\\main\\resources\\images\\exemple.pdf");
            Notification notif = new Notification();
            // Create an informational notification
            Notifications.create()
                    .title("Sauvegarde PDF")
                    .text("Votre document PDF a bien été Sauvegardé.")
                    .showInformation();
            // Fermeture du document
            document.close();

            System.out.println("PDF généré avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //----------------------- FIN Generate du PDF-----------------------------------
    }

}
