package controllers;

import org.controlsfx.control.Notifications;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import services.PretService;
import entities.Pret;

public class AddPret {

    private final PretService pretService = new PretService();

    @FXML
    private Button addpretbtn;

    @FXML
    private TextField duree;

    @FXML
    private TextField montantPret;

    @FXML
    private TextField nomAssociation;

    @FXML
    private TextField tauxInteret;

    @FXML
    void addpretonadd(ActionEvent event) {
        // Reset text fields' backgrounds to default
        montantPret.setStyle("");
        tauxInteret.setStyle("");
        duree.setStyle("");
        nomAssociation.setStyle("");

        // Validate input
        boolean isValid = true;

        // Validate montantPret
        // Validate montantPret
        String montantText = montantPret.getText();
        if (montantText.isEmpty()) {
            showNotification("Montant prêt est requis", montantPret, NotificationType.ERROR);
            isValid = false;
        } else {
            try {
                float montantValue = Float.parseFloat(montantText);
                if (montantValue > 10000) {
                    showNotification("Le montant du prêt ne peut pas dépasser 10000", montantPret, NotificationType.ERROR);
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                showNotification("Montant prêt doit être un nombre", montantPret, NotificationType.ERROR);
                isValid = false;
            }

            // Additional validation if montantText is not empty and didn't exceed 10000
            try {
                Float.parseFloat(montantText);
            } catch (NumberFormatException e) {
                showNotification("Montant prêt doit être un nombre", montantPret, NotificationType.ERROR);
                isValid = false;
            }
        }


        // Validate tauxInteret
        String tauxText = tauxInteret.getText();
        if (tauxText.isEmpty()) {
            showNotification("Taux d'intérêt est requis", tauxInteret, NotificationType.ERROR);
            isValid = false;
        } else {
            try {
                Float.parseFloat(tauxText);
            } catch (NumberFormatException e) {
                showNotification("Taux d'intérêt doit être un nombre", tauxInteret, NotificationType.ERROR);
                isValid = false;
            }
        }

        // Validate duree (add your validation logic)
        if (duree.getText().isEmpty()) {
            showNotification("Durée est requise", duree, NotificationType.ERROR);
            isValid = false;
        }

        // Validate nomAssociation
        String associationName = nomAssociation.getText();
        if (associationName.isEmpty()) {
            showNotification("Nom de l'association est requis", nomAssociation, NotificationType.ERROR);
            isValid = false;
        } else if (associationName.length() > 20) {
            showNotification("Le nom de l'association ne peut pas dépasser 20 caractères", nomAssociation, NotificationType.ERROR);
            isValid = false;
        } else if (associationName.matches(".*\\d.*")) {
            showNotification("Le nom de l'association ne peut pas contenir de chiffres", nomAssociation, NotificationType.ERROR);
            isValid = false;
        }


        // If all inputs are valid, add the loan
        if (isValid) {
            // Add loan using PretService
            float montant = Float.parseFloat(montantText);
            float taux = Float.parseFloat(tauxText);
            String dureeValue = duree.getText();
            String association = nomAssociation.getText();

            Pret pret = new Pret(montant, taux, dureeValue, association);
            pretService.addPret(pret);

            // Show success notification
            showNotification("Prêt ajouté avec succès!", null, NotificationType.INFORMATION);
        }
    }

    private void showNotification(String message, TextField textField, NotificationType type) {
        Notifications.create()
                .title(type.getTitle())
                .text(message)
                .show();
        if (textField != null) {
           // textField.setStyle("-fx-background-color: pink;");
        }
    }

    private enum NotificationType {
        ERROR("Error"),
        INFORMATION("Information");

        private final String title;

        NotificationType(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}