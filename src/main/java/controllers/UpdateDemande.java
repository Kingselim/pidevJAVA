package controllers;

import entities.Demande;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import services.DemandeService;
import services.UserService;

public class UpdateDemande {

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
    private TextField typePretTextField;
    @FXML
    private Label typeerr;
    private DemandeService demandeService = new DemandeService();
    UserService userService = new UserService();
    private Demande demande;

    public void initData(Demande demande) {
        this.demande = demande;
        // Initialize fields with existing demand data
        idUserTextField.setText(String.valueOf(demande.getIdUser()));
        motifPretTextField.setText(demande.getMotifPret());
        stateTextField.setText(demande.getState());
        typePretTextField.setText(demande.getTypePret());
    }

    @FXML
    void updateDemand(ActionEvent event) {
        // Reset error labels
        idusererr.setText("");
        motiferr.setText("");
        stateerr.setText("");
        typeerr.setText("");

        boolean isValid = true;

        String idUserText = idUserTextField.getText();
        if (idUserText.isEmpty()) {
            idusererr.setText("ID user est requis");
            idusererr.setTextFill(Color.RED);
            isValid = false;
        }

        String motifText = motifPretTextField.getText();
        if (motifText.isEmpty()) {
            motiferr.setText("Motif pret est requis");
            motiferr.setTextFill(Color.RED);
            isValid = false;
        }

        String stateText = stateTextField.getText();
        if (stateText.isEmpty()) {
            stateerr.setText("State est requis");
            stateerr.setTextFill(Color.RED);
            isValid = false;
        }

        String typePretText = typePretTextField.getText();
        if (typePretText.isEmpty()) {
            typeerr.setText("Type pret est requis");
            typeerr.setTextFill(Color.RED);
            isValid = false;
        }

        if (isValid) {
            try {
                long userId = Long.parseLong(idUserText);

                User user = userService.getUserById(userId);

                if (user != null) {
                    demande.setIdUser(user);
                    demande.setMotifPret(motifText);
                    demande.setState(stateText);
                    demande.setTypePret(typePretText);

                    demandeService.updateDemande(demande);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Demande mise à jour avec succès!");
                    alert.showAndWait();
                } else {
                    idusererr.setText("Utilisateur introuvable");
                    idusererr.setTextFill(Color.RED);
                }
            } catch (NumberFormatException e) {
                idusererr.setText("ID utilisateur invalide");
                idusererr.setTextFill(Color.RED);
            }
        }
    }
    @FXML
    private Button backtolistview;

    @FXML
    void backtolistviewon(ActionEvent event) {
        try {
            // Load the afficherdemande.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherDemande.fxml"));
            Parent root = loader.load();

            // Get the stage from the button's scene
            Stage stage = (Stage) backtolistview.getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
