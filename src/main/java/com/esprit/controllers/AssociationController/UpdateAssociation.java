package com.esprit.controllers.AssociationController;

import com.esprit.controllers.LanguageSettings;
import com.esprit.models.Association;
import com.esprit.services.AssociationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class UpdateAssociation  {


    @FXML
    private TextField city;

    @FXML
    private TextArea description;

    @FXML
    private TextField email;

    @FXML
    private TextField facebook_adresse;

    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    @FXML
    private TextField state;

    @FXML
    private TextField user;

    @FXML
    private Text errorLabel;

    private String direction;

    private final AssociationService as = new AssociationService();

    private Association myAssociation = new Association();


    @FXML
    protected void OnUpdate(ActionEvent event) throws IOException {


        Association updatedAssociation = new Association();
        updatedAssociation.setName(name.getText());
        updatedAssociation.setCity(city.getText());
        updatedAssociation.setDescription(description.getText());
        updatedAssociation.setEmail(email.getText());
        updatedAssociation.setFacebook_adresse(facebook_adresse.getText());
        updatedAssociation.setPhone(phone.getText());
        updatedAssociation.setState(state.getText());
        updatedAssociation.setId(myAssociation.getId());


        try {
            if (isValidInput()) {
                as.update(updatedAssociation);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Association updated Successfully !");
                alert.showAndWait();

            }
        } catch (Exception e) {

            System.out.println("Erreur lors de la modification de l'association : " + e.getMessage());
        }
    }

    private Stage stage;
    private Scene scene;
    private Parent root;



    @FXML
    void backClient(MouseEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/AssociationFXML/MyAssociations.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void backAdmin(MouseEvent event) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("Messages", LanguageSettings.getCurrentLocale());
        Parent root = FXMLLoader.load(getClass().getResource("/AssociationFXML/"+direction),bundle);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void initData(int id, String dir) {

        this.direction=dir;
        myAssociation = as.getById(id);
        if (myAssociation != null) {
            name.setText(myAssociation.getName());
            city.setText(myAssociation.getCity());
            description.setText(myAssociation.getDescription());
            email.setText(myAssociation.getEmail());
            facebook_adresse.setText(myAssociation.getFacebook_adresse());
            phone.setText(myAssociation.getPhone());
            state.setText(myAssociation.getState());
            user.setText(String.valueOf(myAssociation.getId_user()));
        }
    }

    private boolean isValidInput() {

        if (name.getText().isEmpty()) {
            errorLabel.setText("Please enter a name.");
            return false;
        }

        if (phone.getText().isEmpty()) {
            errorLabel.setText("Please enter a phone number.");
            return false;
        }

        if (email.getText().isEmpty() || !isValidEmail(email.getText())) {
            errorLabel.setText("Please enter a valid email address.");
            return false;
        }

        if (city.getText().isEmpty()) {
            errorLabel.setText("Please enter a city.");
            return false;
        }

        if (state.getText().isEmpty()) {
            errorLabel.setText("Please enter a state.");
            return false;
        }


        if (description.getText().isEmpty()) {
            errorLabel.setText("Please enter a description.");
            return false;
        }

        if (user.getText().isEmpty() || !user.getText().matches("\\d+")) {
            errorLabel.setText("Please enter a valid user ID (numeric value only).");
            return false;
        }

        if (state.getText().isEmpty()) {
            errorLabel.setText("Please enter a user ID.");
            return false;
        }

        errorLabel.setText("");

        if (!phone.getText().matches("\\d{8}")) {
            errorLabel.setText("Please enter a valid 8-digit phone number.");
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}