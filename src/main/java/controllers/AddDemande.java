package controllers;

import entities.Demande;
import entities.Pret;
import entities.TwilioSMS;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import services.DemandeService;

public class AddDemande {

    @FXML
    private TextField idUserTextField;

    @FXML
    private Label idusererr;

    @FXML
    private TextField motifPretTextField;

    @FXML
    private Label motiferr;

    @FXML
    private TextField stateTextField;

    @FXML
    private Label stateerr;
    @FXML
    private Label  typeerr ;

    @FXML
    private TextField typePretTextField;

    private Long pretId;

    public void initData(Long pretId) {
        this.pretId = pretId;
    }

    private final DemandeService demandeService = new DemandeService();

    @FXML
    void addDemand(ActionEvent event) {
        // Reset error labels and set text fill color to red
        idusererr.setTextFill(Color.RED);
        motiferr.setTextFill(Color.RED);
        stateerr.setTextFill(Color.RED);
        typeerr.setTextFill(Color.RED);
        // Retrieve input values from text fields
        String idUser = idUserTextField.getText().trim();
        String motifPret = motifPretTextField.getText().trim();
        String state = stateTextField.getText().trim();
        String typePret = typePretTextField.getText().trim();

        // Validate input fields
        boolean isValid = true;
        if (idUser.isEmpty()) {
            idusererr.setText("User ID is required");
            isValid = false;
        }
        if (motifPret.isEmpty()) {
            motiferr.setText("Pret motif is required");
            isValid = false;
        }
        if (typePret.isEmpty()) {
            typeerr.setText("Pret type is required");
            isValid = false;
        }
        if (state.isEmpty()) {
            stateerr.setText("State is required");
            isValid = false;
        }

        // If all fields are valid, add the demand
        if (isValid) {
            // Create a new demand object
            User user = new User();
            user.setId(Long.parseLong(idUser)); // Assuming idUser is a valid long value
            Pret pret = new Pret();
            pret.setId(pretId); // Assuming pretId is already set
            Demande demande = new Demande(state, typePret, motifPret, user, pret);

            // Call the service to add the demand
            demandeService.addDemande(demande);
            String messageText = "Demande ajoutée avec succès:\n" +
                    "ID Utilisateur: " + idUser + "\n" +
                    "Motif Prêt: " + motifPret + "\n" +
                    "État: " + state + "\n" +
                    "Type Prêt: " + typePret;

            // Send message using Twilio
            TwilioSMS.sendCustomMessage("+21648171175", messageText);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Prêt ajouté avec succès!");
            alert.showAndWait();
            clearFields();
        }
    }

    // Method to clear input fields
    private void clearFields() {
        idUserTextField.clear();
        motifPretTextField.clear();
        stateTextField.clear();
        typePretTextField.clear();
    }

}
