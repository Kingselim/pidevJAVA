package com.esprit.controllers;

import com.esprit.models.account;
import com.esprit.services.TwilioSMSTransaction;
import com.esprit.models.transaction;
import com.esprit.services.EmailSender;
import com.esprit.services.accountService;
import com.esprit.services.transactionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;


//import nécessaire pour la double authentification
import java.util.Random;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;

public class AjouterTransactionController implements Initializable {

    @FXML
    private TextField account_debited;

    @FXML
    private TextField account_destination;

    @FXML
    private TextField date_transaction;

    @FXML
    private TextField description;

    @FXML
    private TextField montant;

    @FXML
    private TextField type_transaction;
/////////
    @FXML
    private TextField id_account_id;
//////////
    @FXML
    private ComboBox<Integer> comboBoxAccountId;
    private final transaction tr = new transaction();

    // Déclarez une variable pour stocker le code de confirmation
    private String codeConfirmation;
    private static final String ACCOUNT_SID = "ACefb722efcfce928681735e22d82e7fad";
    private static final String AUTH_TOKEN = "843072bbb4ede8d875736fbdbd96cfd1";
    private static final String TWILIO_PHONE_NUMBER = "+12184132483";




    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        transactionService trService = new transactionService();
        List<Integer> accountIds = trService.getAllAccountIds();
        comboBoxAccountId.getItems().addAll(accountIds);

        // Ajout d'un écouteur de focus pour chaque champ de texte
        account_debited.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // L'utilisateur a quitté le champ de texte
                validerSaisie(account_debited.getText());
            }
        });

        account_destination.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validerSaisie(account_destination.getText());
            }
        });

        date_transaction.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validerSaisie(date_transaction.getText());
            }
        });

        type_transaction.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validerSaisie(type_transaction.getText());
            }
        });

        description.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validerSaisie(description.getText());
            }
        });

    }
    //fonction valider Montant n'ai pas encore fonctionnelle !!!

    private boolean isValidMontant(String montantText) {
        try {
            // Convertir la saisie en double
            double montantValue = Double.parseDouble(montantText);

            // Vérifier si le montant est positif
            if (montantValue < 0) {
                return false;
            }

            // Si aucune exception n'est levée et que le montant est positif, la saisie est valide
            return true;
        } catch (NumberFormatException e) {
            // La saisie ne peut pas être convertie en double, donc elle n'est pas un nombre réel
            return false;
        }
    }

    private void validerSaisie(String texte) {
        if (texte.isEmpty()) {
            showAlert("Le champ ne peut pas être vide.");
        }
    }

    @FXML
void Effectuer_transaction(ActionEvent event) throws IOException {
        // Obtenir la date actuelle
        LocalDate dateActuelle = LocalDate.now();

        // Générer et envoyer le code de confirmation par SMS
        codeConfirmation = genererCodeConfirmation();
        envoyerCodeConfirmationParSMS(codeConfirmation);

        // Vérifier si la date saisie par l'utilisateur est au bon format "dd/mm/yyyy"
        String dateSaisie = date_transaction.getText();
        if (!isValidDateFormat(dateSaisie)) {
            showAlert("Le format de la date doit être dd/mm/yyyy.");
            return; // Arrêter le traitement de la transaction
        }


        // Vérifier si le montant est valide
        if (!isValidMontant(montant.getText())) {
            showAlert("Le montant saisi n'est pas valide.");
            return; // Arrêter le traitement de la transaction
        }


        // Afficher une boîte de dialogue pour que l'utilisateur saisisse le code de confirmation
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Confirmation de la transaction");
        dialog.setHeaderText("Veuillez saisir le code de confirmation reçu par SMS :");
        dialog.setContentText("Code de confirmation :");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            String codeSaisi = result.get();

            // Vérifier si le code saisi correspond au code de confirmation
            if (codeSaisi.equals(codeConfirmation)) {
                // Code de confirmation correct, effectuer la transaction

                // Récupération des valeurs des champs et conversion si nécessaire
                double montantValue = Double.parseDouble(montant.getText());
                String accountDebited = account_debited.getText();
                String accountDestination = account_destination.getText();
                String dateTransaction = date_transaction.getText();
                String typeTransaction = type_transaction.getText();
                String descriptionValue = description.getText();

                // Récupération de l'ID du compte à partir du ComboBox
                int idAccountId = comboBoxAccountId.getValue();


                // Création de l'objet transaction avec les valeurs récupérées
                transaction tr = new transaction(montantValue, accountDebited, accountDestination, dateTransaction, typeTransaction, descriptionValue, idAccountId);

                // Ajout de la transaction
                transactionService trService = new transactionService();
                trService.ajouter(tr);

                // Mettre à jour le solde du compte de l'association
                accountService accService = new accountService();
                account accAssociation = accService.rechercheaccount(idAccountId);
                if (accAssociation != null) {
                    accService.updateSolde(montantValue, idAccountId);
                }


                // Affichage d'une alerte de succès
                Alert alerte = new Alert(Alert.AlertType.INFORMATION);
                alerte.setTitle("Transaction effectuée");
                alerte.setContentText("Transaction effectuée avec succès");
                alerte.show();

                // Effacement des champs après la transaction
                account_debited.setText("");
                account_destination.setText("");
                date_transaction.setText("");
                description.setText("");
                montant.setText("");

                // Envoi de l'e-mail de notification à l'administrateur
                trService.envoyermail(tr);


                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherTransaction.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                showAlert("Le code de confirmation saisi est incorrect.");
            }

        }

}

    private boolean isValidDateFormat(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }


    private String genererCodeConfirmation() {
        // Générez un code aléatoire à 6 chiffres
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Pour obtenir un nombre entre 100000 et 999999
        return String.valueOf(code);
    }
    private void envoyerCodeConfirmationParSMS(String code) {
        // Code pour envoyer le code de confirmation par SMS
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String numeroUtilisateur = "+21692643302"; // Remplacez par le numéro de téléphone de l'utilisateur

        Message message = Message.creator(
                        new PhoneNumber(numeroUtilisateur),
                        new PhoneNumber(TWILIO_PHONE_NUMBER),
                        "Votre code de confirmation est : " + code)
                .create();

        System.out.println("Message SID : " + message.getSid());

    }


    @FXML
    void retour(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MenuClient.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
