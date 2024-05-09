package com.esprit.controllers.ActionController;

import com.esprit.models.Action;
import com.esprit.models.Association;
import com.esprit.models.Don;
import com.esprit.services.AssociationService;
import com.esprit.services.DonService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FaireUnDon {


    @FXML
    private TextField CVC;

    @FXML
    private Hyperlink LinkAssociation;

    @FXML
    private TextField MMAA;

    @FXML
    private Text actionName;

    @FXML
    private CheckBox checkBox;

    @FXML
    private TextField email;

    @FXML
    private TextField montant;

    @FXML
    private TextField nom;

    @FXML
    private TextField nomComplet;

    @FXML
    private TextField numCarte;

    @FXML
    private TextField prenom;

    @FXML
    private Text targetAmount;

    @FXML
    private ImageView backid;

    @FXML
    private Label montant_restant;

    @FXML
    private Label montant_total;

    @FXML
    private Label errorLabel;

    Integer montant_collected = 0;

    @FXML
    private Text jour_restants;

    @FXML
    private Text participants;

    @FXML
    private ProgressIndicator progressIndicator;

    String direction;
    static {

        Stripe.apiKey = "sk_test_51OrrWBJ3lBwB7CtqozlezRqdXHm2e91aS5Sh4pSWjOMDA8nAZea1ZM1Gi5IZCZGmrt4Is2rICnAZDos155VsTMlj00OAdQRxxU";
    }


    Action action = new Action();
    Association association = new Association();
    AssociationService associationService = new AssociationService();
    DonService donService = new DonService();


    public void initData(Action act,String dir) {
        direction =dir;
        action = act;
        association = associationService.getById(action.getId_association());

        LinkAssociation.setText(association.getFacebook_adresse());
        actionName.setText(action.getName_action());
        targetAmount.setText(String.valueOf(action.getTarget_amount()) + " DT");

        List<Don> dons = donService.getById_action(act.getId());

        for (Don don : dons) {
            montant_collected =  montant_collected + don.getMontant();
        }
        montant_total.setText(String.valueOf(montant_collected) + " DT");
        Integer montantRestant  = action.getTarget_amount() - montant_collected;

        /*if (montantRestant < 0 || montantRestant == 0) {
            montant_restant.setText("Reste à collecter : 0 T");
        }else{
            montant_restant.setText("Reste à collecter : " + String.valueOf(montantRestant));
        }*/

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
    @FXML
    void back(MouseEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ActionFXML/Show_details_action.fxml"));
            Parent root = loader.load();

            ShowDetailsAction controller = loader.getController();
            controller.initData(action,direction);

            Stage stage = (Stage) backid.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void faireDon(ActionEvent event) {
        if(isValidInput()){
            try {
                String testToken = "tok_visa";

                Map<String, Object> chargeParams = new HashMap<>();
                chargeParams.put("amount",Integer.parseInt(montant.getText()));
                chargeParams.put("currency", "usd");
                chargeParams.put("source", testToken);
                Charge charge = Charge.create(chargeParams);


                LocalDate date = LocalDate.now();
                Date sqlDate = Date.valueOf( date);
                String donateur = nom.getText() + prenom.getText();
                donService.add(  new Don(action.getId(), Integer.parseInt(montant.getText()), sqlDate,  donateur,email.getText()) );

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Merci pour votre donation ❤ !");
                alert.showAndWait();

            } catch (StripeException e) {
                e.printStackTrace();
            }
        }

    }


    private boolean isValidInput() {


        if (nom.getText().isEmpty()) {
            errorLabel.setText("Please enter a last name.");
            return false;
        }

        if (prenom.getText().isEmpty()) {
            errorLabel.setText("Please enter a first name.");
            return false;
        }


        if (email.getText().isEmpty() || !isValidEmail(email.getText())) {
            errorLabel.setText("Please enter a valid email address.");
            return false;
        }


        if (montant.getText().isEmpty() || !montant.getText().matches("\\d+(\\.\\d{1,2})?")) {
            errorLabel.setText("Please enter a valid amount (number).");
            return false;
        }


        if (numCarte.getText().isEmpty() || !numCarte.getText().matches("\\d{16}")) {
            errorLabel.setText("Please enter a valid 16-digit number.");
            return false;
        }


        if (MMAA.getText().isEmpty() || !isValidExpirationDate(MMAA.getText())) {
            errorLabel.setText("Please enter a valid expiration date in MM/YY format.");
            return false;
        }

        if (CVC.getText().isEmpty() || !CVC.getText().matches("\\d{3}")) {
            errorLabel.setText("Please enter a valid 3-digit number.");
            return false;
        }

        if (nomComplet.getText().isEmpty()) {
            errorLabel.setText("Please enter a full name.");
            return false;
        }


        if (!checkBox.isSelected()) {
            errorLabel.setText("Please agree to the terms.");
            return false;
        }

        errorLabel.setText("");
        return true;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private boolean isValidExpirationDate(String date) {

        if (!date.matches("(0[1-9]|1[0-2])/\\d{2}")) {
            return false;
        }

        String[] parts = date.split("/");
        int year = Integer.parseInt("20" + parts[1]);
        int month = Integer.parseInt(parts[0]);


        java.util.Calendar today = java.util.Calendar.getInstance();
        java.util.Calendar expDate = java.util.Calendar.getInstance();
        expDate.set(java.util.Calendar.YEAR, year);
        expDate.set(java.util.Calendar.MONTH, month - 1);


        return today.before(expDate);
    }

}
