package controllers;

import entities.Pret;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.controlsfx.control.Notifications;
import services.PretService;

public class UpdatePret {

    @FXML
    private Button updatepretbtn;

    @FXML
    private TextField duree;

    @FXML
    private TextField montantPret;

    @FXML
    private TextField nomAssociation;

    @FXML
    private TextField tauxInteret;

    private PretService pretService = new PretService();

    private Long pretId;

    public void initData(Long id) {
        this.pretId = id;

        Pret pret = pretService.getPretById(id);
        if (pret != null) {
            montantPret.setText(String.valueOf(pret.getMontantPret()));
            tauxInteret.setText(String.valueOf(pret.getTauxInteret()));
            duree.setText(pret.getDuree());
            nomAssociation.setText(pret.getNomAssociation());
        } else {
            Notifications.create()
                    .title("Error")
                    .text("Pret not found.")
                    .showError();
        }
    }

    // Method to handle update button action
    @FXML
    void updatepret(ActionEvent event) {
        boolean isValid = true;

        String montantText = montantPret.getText();
        if (montantText.isEmpty()) {
            isValid = false;
            Notifications.create()
                    .title("Error")
                    .text("Montant pret est requis")
                    .showError();
        }

        String tauxText = tauxInteret.getText();
        if (tauxText.isEmpty()) {
            isValid = false;
            Notifications.create()
                    .title("Error")
                    .text("Taux d'intérêt est requis")
                    .showError();
        }

        if (duree.getText().isEmpty()) {
            isValid = false;
            Notifications.create()
                    .title("Error")
                    .text("Durée est requise")
                    .showError();
        }

        if (nomAssociation.getText().isEmpty()) {
            isValid = false;
            Notifications.create()
                    .title("Error")
                    .text("Nom de l'association est requis")
                    .showError();
        }

        if (isValid) {
            float montant = Float.parseFloat(montantText);
            float taux = Float.parseFloat(tauxText);
            String dureeValue = duree.getText();
            String association = nomAssociation.getText();

            if (pretId != null) {
                Pret updatedPret = new Pret(pretId, montant, taux, dureeValue, association);
                pretService.updatePret(updatedPret);

                Notifications.create()
                        .title("Information")
                        .text("Prêt mis à jour avec succès!")
                        .showInformation();
            } else {
                Notifications.create()
                        .title("Error")
                        .text("PretId is null. Unable to update pret.")
                        .showError();
            }
        }
    }
}
