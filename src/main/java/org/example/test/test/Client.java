package org.example.test.test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.test.test.models.Action;
import org.example.test.test.models.Association;
import org.example.test.test.models.Don;
import org.example.test.test.services.ActionService;
import org.example.test.test.services.AssociationService;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Client {

    @FXML
    private Text nbr_actions;

    @FXML
    private Text nbr_associations;

    @FXML
    private Text nbr_utilisateur;

    @FXML
    private Button label_CreerAction;

    @FXML
    private Button label_CreerAssociation;

    @FXML
    private Button label_MesAction;

    @FXML
    private Button label_MesAssociation;

    @FXML
    private Button label_VoirListeActions;

    @FXML
    private Text label_titre;

    @FXML
    private Label label_utilisateurs;

    private Stage stage;
    private Scene scene;
    private Parent root;

    AssociationService associationService = new AssociationService();
    ActionService actionService = new ActionService();

    @FXML
    void initialize() {

        Locale currentLocale = LanguageSettings.getCurrentLocale();
        System.out.println(currentLocale.getLanguage());
        List<Association> associations = associationService.getAll();
        List<Action> actions = actionService.getAll();

        nbr_utilisateur.setText("6");
        nbr_associations.setText(String.valueOf(associations.size()));
        nbr_actions.setText(String.valueOf(actions.size()));


        if(currentLocale.getLanguage()=="en"){
            try {
                label_titre.setText(Translator.translate(label_titre.getText(),"fr","en"));
                label_CreerAction.setText(Translator.translate(label_CreerAction.getText(),"fr","en"));
                label_CreerAssociation.setText(Translator.translate(label_CreerAssociation.getText(),"fr","en"));
                label_MesAction.setText(Translator.translate(label_MesAction.getText(),"fr","en"));
                label_MesAssociation.setText(Translator.translate(label_MesAssociation.getText(),"fr","en"));
                label_VoirListeActions.setText(Translator.translate(label_VoirListeActions.getText(),"fr","en"));
                label_utilisateurs.setText(Translator.translate(label_utilisateurs.getText(),"fr","en"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if(currentLocale.getLanguage()=="fr"){
            try {
                label_titre.setText(Translator.translate(label_titre.getText(),"en","fr"));
                label_titre.setText(Translator.translate(label_titre.getText(),"en","fr"));
                label_CreerAction.setText(Translator.translate(label_CreerAction.getText(),"en","fr"));
                label_CreerAssociation.setText(Translator.translate(label_CreerAssociation.getText(),"en","fr"));
                label_MesAction.setText(Translator.translate(label_MesAction.getText(),"en","fr"));
                label_MesAssociation.setText(Translator.translate(label_MesAssociation.getText(),"en","fr"));
                label_VoirListeActions.setText(Translator.translate(label_VoirListeActions.getText(),"en","fr"));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }


    @FXML
    void add_association(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/test/test/AssociationFXML/AddAssociation.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void add_action(ActionEvent event) throws IOException{

        Parent root = FXMLLoader.load(getClass().getResource("AddAction.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void show_actions(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("show_list_actions.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void show_my_actions(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("myActions.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void show_my_associations(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/org/example/test/test/AssociationFXML/MyAssociations.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void translateToEnglish(MouseEvent event) throws IOException{

        LanguageSettings.setCurrentLocale(new Locale("en"));
        ResourceBundle bundle = ResourceBundle.getBundle("/org/example/test/test/Messages", LanguageSettings.getCurrentLocale());

        Parent root = FXMLLoader.load(getClass().getResource("/org/example/test/test/Client.fxml"),bundle);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void translateToFrench(MouseEvent event) throws IOException{

        LanguageSettings.setCurrentLocale(new Locale("fr"));
        ResourceBundle bundle = ResourceBundle.getBundle("/org/example/test/test/Messages", LanguageSettings.getCurrentLocale());

        Parent root = FXMLLoader.load(getClass().getResource("/org/example/test/test/Client.fxml"),bundle);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
