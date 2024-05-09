package com.esprit.controllers.ActionController;

import com.esprit.models.Action;
import com.esprit.models.Association;
import com.esprit.services.ActionService;
import com.esprit.services.AssociationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class UpdateAction {

    @FXML
    private TextField category;

    @FXML
    private DatePicker date_action;

    @FXML
    private TextArea description_action;

    @FXML
    private ChoiceBox<String> id_association;

    @FXML
    private TextField name_action;

    @FXML
    private TextField organized_for;

    @FXML
    private TextField target_amount;

    @FXML
    private TextField location_action;

    @FXML
    private Text errorLabel;

    private final ActionService as = new ActionService();
    private final AssociationService associationService = new AssociationService();
    Action action = new Action();
    Association assoc = new Association();

    private Stage stage;
    private Scene scene;


    int[] ids ;
    String[] names;
    int id;
    int j;
    String name_of_association;


    public void initData(Action act) {

        action = act;

        category.setText(action.getCategory());
        name_action.setText(action.getName_action());
        organized_for.setText(action.getOrganized_for());
        target_amount.setText(String.valueOf(action.getTarget_amount()));
        location_action.setText(action.getLocation());
        description_action.setText(action.getDescription_action());

        List<Association> associations = associationService.getAll();

        for (int i = 0; i < associations.size(); i++) {

            if(associations.get(i).getId() == action.getId_association()) {
                name_of_association = associations.get(i).getName().toString();
            }
        }
        id_association.setValue(name_of_association);

        Date dateFromDatabase = (Date) action.getDate_action();
        // convert java.sql.Date to java.util.Date
        Date utilDate = new Date(dateFromDatabase.getTime());
        // convert java.util.Date to LocalDate
        LocalDate localDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        date_action.setValue(localDate);

    }

    @FXML
    void back(MouseEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/ActionFXML/myActions.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void OnUpdate(ActionEvent event) {

        Action updatedaction = new Action();
        updatedaction.setCategory(category.getText());
        updatedaction.setName_action(name_action.getText());
        updatedaction.setOrganized_for(organized_for.getText());
        updatedaction.setTarget_amount(Integer.parseInt(target_amount.getText()));

        LocalDate selectedDate = date_action.getValue();
        java.sql.Date sqlDate = java.sql.Date.valueOf(selectedDate);
        updatedaction.setDate_action( sqlDate);

        updatedaction.setLocation(location_action.getText());
        updatedaction.setDescription_action(description_action.getText());
        updatedaction.setId_association(id);
        updatedaction.setId(action.getId());

        try {
            if (isValidInput()) {
                as.update(updatedaction);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Action updated Successfully !");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private boolean isValidInput() {
        if (name_action.getText().isEmpty()) {
            errorLabel.setText("Please enter a name.");
            return false;
        }

        if (target_amount.getText().isEmpty() || !target_amount.getText().matches("\\d+")) {
            errorLabel.setText("Please enter a proper amount.");
            return false;
        }

        if (location_action.getText().isEmpty()) {
            errorLabel.setText("Please enter a location.");
            return false;
        }
        if (description_action.getText().isEmpty()) {
            errorLabel.setText("Please enter a description.");
            return false;
        }
        if (date_action.getValue() == null) {
            errorLabel.setText("Please enter a date.");
            return false;
        }
        if (organized_for.getText().isEmpty()) {
            errorLabel.setText("Please for whom organized.");
            return false;
        }
        if (category.getText().isEmpty()) {
            errorLabel.setText("Please enter a category.");
            return false;
        }

        errorLabel.setText("");

        return true;
    }

    public void initialize() {

        List<Association> associations = associationService.getDisabledAssociations(1);

        ids = new int[associations.size()];
        names = new String[associations.size()];


        for (int i = 0; i < associations.size(); i++) {
            if(associations.get(i).getId() == action.getId()) {
                j=i;
            }
            ids[i] = associations.get(i).getId();
            names[i] = associations.get(i).getName();
            id_association.getItems().add(names[i]);
        }

        id_association.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            int selectedIndex = id_association.getItems().indexOf(newValue);
            id= ids[selectedIndex];

        });
    }
}