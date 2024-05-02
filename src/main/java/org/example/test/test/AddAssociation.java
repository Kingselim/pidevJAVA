package org.example.test.test;

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
import org.example.test.test.models.Association;
import org.example.test.test.services.AssociationService;

import java.io.IOException;
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


    @FXML
    protected void add(ActionEvent event) {

        try {
            if(isValidInput()){
                as.add(new Association(name.getText(), phone.getText(), email.getText(),city.getText(),state.getText(),description.getText(),0 , Integer.parseInt(user.getText()), facebook_adresse.getText()));

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

        ResourceBundle bundle = ResourceBundle.getBundle("/org/example/test/test/Messages", LanguageSettings.getCurrentLocale());
        Parent root = FXMLLoader.load(getClass().getResource("Client.fxml"),bundle);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
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

        if (user.getText().isEmpty() || !user.getText().matches("\\d+")) {
            errorLabel.setText("Please enter a valid user ID (numeric value only).");
            return false;
        }

        if (state.getText().isEmpty()) {
            errorLabel.setText("Please enter a user ID.");
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
