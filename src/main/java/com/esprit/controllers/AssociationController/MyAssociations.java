package com.esprit.controllers.AssociationController;

import com.esprit.controllers.ActionController.ClientAssociation;
import com.esprit.controllers.LanguageSettings;
import com.esprit.models.Association;
import com.esprit.models.User;
import com.esprit.services.AssociationService;
import com.esprit.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class MyAssociations {

    private Stage stage;
    private Scene scene;
    private Parent root;

    AssociationService as = new AssociationService();
    private final UserService us = new UserService();

    User owner = new User();
    Integer user_id;


    public void SetUserId(int id){

        this.user_id = id;
        owner = us.recupererUserAvecId(this.user_id);

        initialize();

    }

    @FXML
    private Button backid;

    @FXML
    private VBox associationsContainer;

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

    @FXML
    private void showAssociationDetails(Association association) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AssociationFXML/ShowDetailsAssociation2.fxml"));
            Parent root = loader.load();

            ShowDetailsAssociation2 controller = loader.getController();
            controller.initData(association);

            Stage stage = (Stage) backid.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void  OnDeleteAssociation(Association association){
        try {

            as.delete(association.getId());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Deleted !");
            alert.showAndWait();
            initialize();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AssociationFXML/MyAssociations.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) backid.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        }catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    void OnUpdateAssociation(Association association){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AssociationFXML/UpdateAssociation_Admin.fxml"));
            Parent root = loader.load();

            UpdateAssociation controller = loader.getController();

            String direction = "MyAssociations.fxml";
            controller.initData(association.getId(),direction);

            Stage stage = (Stage) backid.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        System.out.println(owner);
        if (owner.getId() != 0){
            List<Association> associations = as.getAssociationByIdUser(this.owner.getId());
            for (Association association : associations) {
                Pane actionPane = createActionPane(association);
                associationsContainer.getChildren().add(actionPane);
            }
        }

    }

    private Pane createActionPane(Association association) {
        Pane pane = new Pane();
        pane.setPrefWidth(585.0);
        pane.setPrefHeight(164.0);
        pane.setStyle("-fx-background-color: #ffffff;  -fx-border-radius: 5; -fx-background-radius: 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");

        Text title = new Text(association.getName());
        title.setLayoutX(220.0);
        title.setLayoutY(60.0);
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(200.0);
        imageView.setFitHeight(160.0);
        imageView.setLayoutX(10.0);
        imageView.setLayoutY(2.0);


        int indice = RandomInt();
        String imagePath = "C:/Users/21694/Desktop/Test - 2/src/main/resources/org/example/test/test/imagesAssociation/" + indice+".png";
        Image image = new Image(new File(imagePath).toURI().toString(), true);
        imageView.setImage(image);

        String partOfDescription =  association.getDescription().length() > 70 ?  association.getDescription().substring(0, 60) :  association.getDescription();
        Text description = new Text(partOfDescription + "...");
        description.setLayoutX(220.0);
        description.setLayoutY(108.0);
        description.setStyle("-fx-font-size: 13px;");

        Button detailsButton = new Button("Voir");
        detailsButton.setLayoutX(220.0);
        detailsButton.setLayoutY(130.0);
        detailsButton.setStyle("-fx-background-color: #43f501; -fx-text-fill: white; -fx-font-weight: bold;");
        detailsButton.setOnAction(e -> showAssociationDetails(association));

        Button deleteButton = new Button("Supprimer");
        deleteButton.setLayoutX(270.0);
        deleteButton.setLayoutY(130.0);
        deleteButton.setStyle("-fx-background-color: #d9042b; -fx-text-fill: white; -fx-font-weight: bold;");
        deleteButton.setOnAction(e -> OnDeleteAssociation(association));

        Button updateButton = new Button("Modifier");
        updateButton.setLayoutX(355.0);
        updateButton.setLayoutY(130.0);
        updateButton.setStyle("-fx-background-color: #264eca; -fx-text-fill: white; -fx-font-weight: bold;");
        updateButton.setOnAction(e -> OnUpdateAssociation(association));

        pane.getChildren().addAll(title,description,imageView, detailsButton, deleteButton, updateButton);
        return pane;
    }

    public static int RandomInt() {
        Random random = new Random();
        return random.nextInt(5) + 1;  // génère un nombre entre 1 et 6
    }

}
