package com.esprit.controllers.AssociationController;

import com.esprit.controllers.LanguageSettings;
import com.esprit.models.Association;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class ShowDetailsAssociation {

    @FXML
    private Label nameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label cityLabel;

    @FXML
    private Label stateLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label StatusLabel;

    @FXML
    private Label ownerLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Association myAssociation = new Association();

    @FXML
    void back(MouseEvent event) throws IOException {

        ResourceBundle bundle = ResourceBundle.getBundle("Messages", LanguageSettings.getCurrentLocale());
        Parent root = FXMLLoader.load(getClass().getResource("/AssociationFXML/Show_list_association.fxml"), bundle);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initData(Association association)
    {
        myAssociation = association;
        if (association != null) {
            nameLabel.setText(association.getName());
            cityLabel.setText(association.getCity());
            descriptionLabel.setText(association.getDescription());
            emailLabel.setText(association.getEmail());

            phoneLabel.setText(association.getPhone());
            stateLabel.setText(association.getState());

            if(association.getStatut() == 0){
                StatusLabel.setText("Disabled");
            }else {
                StatusLabel.setText("Active");
            }
            ownerLabel.setText(String.valueOf(association.getId_user()));

        }
    }
}
