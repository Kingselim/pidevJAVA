package com.esprit.controllers.ActionController;

import com.esprit.controllers.Translator;
import com.esprit.models.Action;
import com.esprit.models.Association;
import com.esprit.models.Don;
import com.esprit.services.AssociationService;
import com.esprit.services.DonService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ShowDetailsAction {

    @FXML
    private Hyperlink LinkAssociation;

    @FXML
    private ImageView actionImage;

    @FXML
    private Text actionName1;

    @FXML
    private Text actionName2;

    @FXML
    private TextArea descriptionAction;

    @FXML
    private Text organizedFor;

    @FXML
    private Text targetAmount;

    @FXML
    private ImageView backid;

    @FXML
    private Text jour_restants;

    @FXML
    private Label montant_total;


    @FXML
    private Text participants;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private Text montant_restant;

    Integer montant_collected = 0;
    String direction;

    private Stage stage;
    private Scene scene;
    private Parent root;

    Action action = new Action();
    Association association = new Association();
    AssociationService associationService = new AssociationService();
    DonService donService = new DonService();
    Translator translator = new Translator();


    public void initData(Action act, String direction) throws Exception {
        this.direction = direction;
        action = act;
        association = associationService.getById(action.getId_association());

        LinkAssociation.setText(association.getFacebook_adresse());

        String imagePath = "D:/EasyPHP-Devserver-17/eds-www/pidev2/pidev_givest_symfony/public/frontOffice/assets/img/" + action.getImage();
        setImage(imagePath);


        String translatedName = Translator.translate(action.getName_action(), "en", "fr");

        System.out.println("Translated name: " + translatedName);
        actionName1.setText(translatedName);
        //actionName1.setText(action.getName_action());
        actionName2.setText(action.getName_action());
        descriptionAction.setText(action.getDescription_action());
        organizedFor.setText(action.getOrganized_for());
        targetAmount.setText(String.valueOf(action.getTarget_amount())+ " DT");

        List<Don> dons = donService.getById_action(act.getId());

        for (Don don : dons) {
            montant_collected =  montant_collected + don.getMontant();
        }
        montant_total.setText(String.valueOf(montant_collected) + " DT");
        Integer montantRestant  = action.getTarget_amount() - montant_collected;

        if (montantRestant < 0 || montantRestant == 0) {
            montant_restant.setText("Reste à collecter : 0 T");
        }else{
            montant_restant.setText("Reste à collecter : " + String.valueOf(montantRestant) + "DT");
        }

        participants.setText(String.valueOf(dons.size()));

        LocalDate dateAujourdhui = LocalDate.now();

        java.util.Date dateFromDatabase = (java.util.Date) action.getDate_action();
        // convert java.sql.Date to java.util.Date
        java.util.Date utilDate = new java.util.Date(dateFromDatabase.getTime());
        // convert java.util.Date to LocalDate
        LocalDate localDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        long differenceEnJours = ChronoUnit.DAYS.between(dateAujourdhui,localDate);

        if(differenceEnJours > 0){
            jour_restants.setText(String.valueOf(differenceEnJours));
        }else{
            jour_restants.setText("0");
        }

        Integer amount =  action.getTarget_amount();
        double montant_collected_double = (double) montant_collected;
        double Target_amount_double = (double) amount;

        double pourcentage = (montant_collected_double / Target_amount_double) * 100.0;
        progressIndicator.setProgress(pourcentage / 100.0);


    }

    private void setImage(String imagePath) {
        try {
            Image image = new Image(new File(imagePath).toURI().toString());
            actionImage.setImage(image);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());

        }
    }

    @FXML
    void back(MouseEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/ActionFXML/"+direction));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void faireUnDon(ActionEvent event)  throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ActionFXML/Faire_un_don.fxml"));
        Parent root = loader.load();

        FaireUnDon controller = loader.getController();
        controller.initData(action,direction);

        Stage stage = (Stage) backid.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


}