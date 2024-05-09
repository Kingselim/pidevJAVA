package com.esprit.controllers.ActionController;

import com.esprit.controllers.LanguageSettings;
import com.esprit.models.Action;
import com.esprit.models.Association;
import com.esprit.models.User;
import com.esprit.services.ActionService;
import com.esprit.services.AssociationService;
import com.esprit.services.UserService;
import com.twilio.rest.flexapi.v1.FlexFlow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MyActions {

    @FXML
    private Text description;

    @FXML
    private Text organizedfor;

    @FXML
    private Text title;

    @FXML
    private Button backid;

    @FXML
    private TextField KeywordTextFiled;

    ActionService as = new ActionService();

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private VBox actionsContainer;

    AssociationService associationService = new AssociationService();
    ActionService actionService = new ActionService();
    private final UserService us = new UserService();

    User owner = new User();
    Integer user_id;


    public void SetUserId(int id){

        this.user_id = id;
        owner = us.recupererUserAvecId(this.user_id);

        initialize();

    }

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
    private void showActionDetails(Action action) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ActionFXML/Show_details_action.fxml"));
            Parent root = loader.load();

            ShowDetailsAction controller = loader.getController();
            String direction = "myActions.fxml";
            controller.initData(action,direction);

            Stage stage = (Stage) backid.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void  OnDeleteAction(Action action){
        try {
            actionService.delete(action.getId());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Action deleted Successfully!");
            alert.showAndWait();

        }catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    void OnUpdateAction(Action action){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ActionFXML/UpdateAction.fxml"));
            Parent root = loader.load();

            UpdateAction controller = loader.getController();
            controller.initData(action);

            Stage stage = (Stage) backid.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {

        if (owner.getId() != 0){
            List<Association> associations = associationService.getAssociationByIdUser(this.owner.getId());
            Integer id_association = associations.get(0).getId();
            // Fetch all actions
            List<Action> actions = as.getActionByIdAssociation(id_association);

            // Initialize the filteredActions list as a normal List<Action>
            List<Action> filteredActions = new ArrayList<>(actions);

            // Add listener to TextField
            KeywordTextFiled.textProperty().addListener((observable, oldValue, newValue) -> {
                String lowerCaseFilter = newValue.toLowerCase();
                filteredActions.clear(); // Clear existing filtered actions

                // Filter matches name or description
                for (Action action : actions) {
                    if (action.getName_action().toLowerCase().contains(lowerCaseFilter)) {
                        filteredActions.add(action); // Add to filtered list if matches
                    }
                }

                // Populate the actions container with filtered actions
                populateActionsContainer(filteredActions);
            });

            // Populate the actions container initially with all actions
            populateActionsContainer(filteredActions);
        }

    }


    private void populateActionsContainer(List<Action> actions) {
        actionsContainer.getChildren().clear(); // Clear existing content

        // Add filtered actions to the container
        for (Action action : actions) {
            Pane actionPane = createActionPane(action);
            actionsContainer.getChildren().add(actionPane);
        }
    }

    private Pane createActionPane(Action action) {
        Pane pane = new Pane();
        pane.setPrefWidth(585.0);
        pane.setPrefHeight(164.0);
        pane.setStyle("-fx-background-color: #ffffff; -fx-border-color: #d3d3d3;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(200.0);
        imageView.setFitHeight(160.0);
        imageView.setLayoutX(10.0);
        imageView.setLayoutY(2.0);

        String imagePath = "D:/EasyPHP-Devserver-17/eds-www/pidev2/pidev_givest_symfony/public/frontOffice/assets/img/" + action.getImage();
        Image image = new Image(new File(imagePath).toURI().toString(), true);
        imageView.setImage(image);

        Text title = new Text(action.getName_action());
        title.setLayoutX(220.0);
        title.setLayoutY(50.0);
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Text organizedFor = new Text("Organisée pour : " + action.getOrganized_for());
        organizedFor.setLayoutX(220.0);
        organizedFor.setLayoutY(80.0);
        organizedFor.setUnderline(true);
        organizedFor.setStyle("-fx-font-size: 14px;");

        String partOfDescription = action.getDescription_action().length() > 70 ? action.getDescription_action().substring(0, 60) : action.getDescription_action();
        Text description = new Text(partOfDescription + "...");
        description.setLayoutX(220.0);
        description.setLayoutY(108.0);
        description.setStyle("-fx-font-size: 13px;");

        Button detailsButton = new Button("Voir");
        detailsButton.setLayoutX(220.0);
        detailsButton.setLayoutY(130.0);
        detailsButton.setStyle("-fx-background-color:  #43f501; -fx-text-fill: white; -fx-font-weight: bold;");
        detailsButton.setOnAction(e -> showActionDetails(action));

        Button deleteButton = new Button("Supprimer");
        deleteButton.setLayoutX(270.0);
        deleteButton.setLayoutY(130.0);
        deleteButton.setStyle("-fx-background-color: #d9042b; -fx-text-fill: white; -fx-font-weight: bold;"); // Tomato red color
        deleteButton.setOnAction(e -> OnDeleteAction(action));

        Button updateButton = new Button("Modifier");
        updateButton.setLayoutX(355.0);
        updateButton.setLayoutY(130.0);
        updateButton.setStyle("-fx-background-color:  #264eca; -fx-text-fill: white; -fx-font-weight: bold;"); // Steel blue color
        updateButton.setOnAction(e -> OnUpdateAction(action));

        pane.getChildren().addAll(imageView, title, organizedFor, description, detailsButton,deleteButton,updateButton);
        return pane;
    }

}
