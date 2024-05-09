package com.esprit.controllers.AssociationController;

import com.esprit.controllers.LanguageSettings;
import com.esprit.controllers.Translator;
import com.esprit.models.Association;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

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
    private Label label_adresse;

    @FXML
    private Label label_email;

    @FXML
    private Label label_telephone;

    @FXML
    private Text phoneText;


    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    void back(MouseEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/AssociationFXML/MyAssociations.fxml"));
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

        Locale currentLocale = LanguageSettings.getCurrentLocale();
        if(currentLocale.getLanguage()=="en"){
            try {
                adresseText.setText(association.getCity());
                associationName.setText(Translator.translate(association.getName(),"fr","en"));
                descriptionText.setText(Translator.translate(association.getDescription(),"fr","en"));
                emailText.setText(Translator.translate(association.getEmail(),"fr","en"));
                phoneText.setText(association.getPhone());

                label_adresse.setText(Translator.translate(label_adresse.getText(),"fr","en"));
                label_email.setText(Translator.translate(label_email.getText(),"fr","en"));
                label_telephone.setText(Translator.translate(label_telephone.getText(),"fr","en"));


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if(currentLocale.getLanguage()=="fr"){
            try {
                adresseText.setText(association.getCity());
                associationName.setText(Translator.translate(association.getName(),"en","fr"));
                descriptionText.setText(Translator.translate(association.getDescription(),"en","fr"));
                emailText.setText(Translator.translate(association.getEmail(),"en","fr"));
                phoneText.setText(association.getPhone());

                label_adresse.setText(Translator.translate(label_adresse.getText(),"en","fr"));
                label_email.setText(Translator.translate(label_email.getText(),"en","fr"));
                label_telephone.setText(Translator.translate(label_telephone.getText(),"en","fr"));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }
}

