package com.esprit.controllers.AssociationController;

import com.esprit.controllers.ActionController.ClientAssociation;
import com.esprit.controllers.LanguageSettings;
import com.esprit.controllers.Translator;
import com.esprit.models.Action;
import com.esprit.models.Association;
import com.esprit.models.User;
import com.esprit.services.AssociationService;
import com.esprit.services.UserService;
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
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddAssociation {

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

    private final AssociationService as = new AssociationService();
    private final UserService us = new UserService();

    User owner = new User();
    Integer user_id;


    public void SetUserId(int id){

        this.user_id = id;
        owner = us.recupererUserAvecId(this.user_id);

        initialize();

    }

    @FXML
    void initialize() {
        if (owner != null) {

            user.setText(owner.getName() + " " + owner.getLastname());
        }
    }

    @FXML
    protected void add(ActionEvent event) {

        try {
            if(isValidInput()){
                as.add(new Association(name.getText(), phone.getText(), email.getText(),city.getText(),state.getText(),description.getText(),0 , this.user_id, facebook_adresse.getText()));

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Association created successfully !");
                alert.showAndWait();

            }
        } catch (Exception e) {

        }
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void back(MouseEvent event) throws IOException {

        ResourceBundle bundle = ResourceBundle.getBundle("Messages", LanguageSettings.getCurrentLocale());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ActionFXML/ClientAssociation.fxml"));
        Parent root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        ClientAssociation controller = loader.getController();
        controller.SetUserId(this.user_id);

        stage.show();
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


        errorLabel.setText("");

        // Validate phone number
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
