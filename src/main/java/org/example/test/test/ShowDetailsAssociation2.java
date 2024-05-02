package org.example.test.test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import org.example.test.test.models.Association;

import java.io.IOException;

public class ShowDetailsAssociation2 {

    @FXML
    private Text adresseText;

    @FXML
    private Text associationName;

    @FXML
    private TextArea descriptionText;

    @FXML
    private Text emailText;

    @FXML
    private Text phoneText;


    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    void back(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/org/example/test/test/AssociationFXML/MyAssociations.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initData(Association association)
    {

        if (association != null) {
            adresseText.setText(association.getCity());
            associationName.setText(association.getName());
            descriptionText.setText(association.getDescription());
            emailText.setText(association.getEmail());
            phoneText.setText(association.getPhone());

        }
    }
}
