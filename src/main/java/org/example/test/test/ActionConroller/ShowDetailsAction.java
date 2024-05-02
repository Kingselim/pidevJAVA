package org.example.test.test.ActionConroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.test.test.models.Action;
import org.example.test.test.models.Association;
import org.example.test.test.services.AssociationService;

import java.io.File;
import java.io.IOException;

public class ShowDetailsAction {

    @FXML
    private Hyperlink LinkAssociation;

    @FXML
    private ImageView actionImage;

    @FXML
    private Text actionName1;

    @FXML
    private Text actionName2;

    @FXML
    private TextArea descriptionAction;

    @FXML
    private Text organizedFor;

    @FXML
    private Text targetAmount;


    private Stage stage;
    private Scene scene;
    private Parent root;

    Action action = new Action();
    Association association = new Association();
    AssociationService associationService = new AssociationService();


    public void initData(Action act) {
        action = act;
        association = associationService.getById(action.getId_association());

        LinkAssociation.setText(association.getFacebook_adresse());



        String imagePath = "D:/EasyPHP-Devserver-17/eds-www/pidev2/pidev_givest_symfony/public/frontOffice/assets/img/" + action.getImage();
        setImage(imagePath);

        actionName1.setText(action.getName_action());
        actionName2.setText(action.getName_action());
        descriptionAction.setText(action.getDescription_action());
        organizedFor.setText(action.getOrganized_for());
        targetAmount.setText(String.valueOf(action.getTarget_amount()));



    }

    private void setImage(String imagePath) {
        try {
            Image image = new Image(new File(imagePath).toURI().toString());
            actionImage.setImage(image);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());

        }
    }

    @FXML
    void back(MouseEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/org/example/test/test/Client.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
