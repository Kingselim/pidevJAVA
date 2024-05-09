package com.esprit.controllers.AssociationController;

import com.esprit.controllers.ActionController.UserIDSettings;
import com.esprit.controllers.LanguageSettings;
import com.esprit.models.Action;
import com.esprit.models.Association;
import com.esprit.models.Don;
import com.esprit.services.ActionService;
import com.esprit.services.AssociationService;
import com.esprit.services.DonService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class AdminAssociation {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Text nbr_actions;

    @FXML
    private Text nbr_associations;

    @FXML
    private Text nbr_dons;

    AssociationService associationService = new AssociationService();
    ActionService actionService = new ActionService();
    DonService donService = new DonService();


    Integer user_id;

    public void SetUserId(int id){

        UserIDSettings.setId_user(id);
        user_id = id;

    }

    @FXML
    void initialize() {


        Locale currentLocale = LanguageSettings.getCurrentLocale();
        System.out.println(currentLocale.getLanguage());
        List<Association> associations = associationService.getAll();
        List<Action> actions = actionService.getAll();
        List<Don> dons = donService.getAll();

        nbr_associations.setText(String.valueOf(associations.size()));
        nbr_actions.setText(String.valueOf(actions.size()));
        nbr_dons.setText(String.valueOf(dons.size()));
    }

    @FXML
    void back(MouseEvent event) throws IOException {

        //ResourceBundle bundle = ResourceBundle.getBundle("Messages", LanguageSettings.getCurrentLocale());
        Parent root = FXMLLoader.load(getClass().getResource("/MenuAdmin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void show_associations(ActionEvent event) throws IOException {

        ResourceBundle bundle = ResourceBundle.getBundle("Messages", LanguageSettings.getCurrentLocale());
        Parent root = FXMLLoader.load(getClass().getResource("/AssociationFXML/Show_list_association.fxml"),bundle);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void show_requests(ActionEvent event) throws IOException {

        ResourceBundle bundle = ResourceBundle.getBundle("Messages", LanguageSettings.getCurrentLocale());
        Parent root = FXMLLoader.load(getClass().getResource("/AssociationFXML/Requests_creation_association.fxml"),bundle);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void show_dons(ActionEvent event) throws IOException {

        ResourceBundle bundle = ResourceBundle.getBundle("Messages", LanguageSettings.getCurrentLocale());
        Parent root = FXMLLoader.load(getClass().getResource("/AssociationFXML/Liste_dons.fxml"),bundle);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void translateToEnglish(MouseEvent event) throws IOException{

        LanguageSettings.setCurrentLocale(new Locale("en"));
        ResourceBundle bundle = ResourceBundle.getBundle("Messages", LanguageSettings.getCurrentLocale());

        Parent root = FXMLLoader.load(getClass().getResource("/AssociationFXML/AdminAssociation.fxml"),bundle);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void translateToFrench(MouseEvent event) throws IOException{

        LanguageSettings.setCurrentLocale(new Locale("fr"));
        ResourceBundle bundle = ResourceBundle.getBundle("Messages", LanguageSettings.getCurrentLocale());

        Parent root = FXMLLoader.load(getClass().getResource("/AssociationFXML/AdminAssociation.fxml"),bundle);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
