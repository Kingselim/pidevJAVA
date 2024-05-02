package org.example.test.test.ActionConroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.test.test.LanguageSettings;
import org.example.test.test.models.Action;
import org.example.test.test.models.Association;
import org.example.test.test.services.ActionService;
import org.example.test.test.services.AssociationService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AddAction {

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
    private Text imageTextName;

    @FXML
    private Text errorLabel;

    @FXML
    private Button back_id;

    @FXML
    private ImageView imageBack;




    private final ActionService as = new ActionService();
    private final AssociationService associationService = new AssociationService();

    private Stage stage;
    private Scene scene;
    private Parent root;

    int[] ids ;
    String[] names;
    int id;
    String imageAction;
    File selectedFile;
    String destDirectoryPath = "D:/EasyPHP-Devserver-17/eds-www/pidev2/pidev_givest_symfony/public/frontOffice/assets/img/";


    @FXML
    void back(MouseEvent event) throws IOException {

        ResourceBundle bundle = ResourceBundle.getBundle("/org/example/test/test/Messages", LanguageSettings.getCurrentLocale());
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/test/test/Client.fxml"),bundle);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void ChooseImage(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {

            String fileName = selectedFile.getName();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

            imageAction = fileName ;
            imageTextName.setText(imageAction);

            System.out.println("Selected Image: " + fileName);
            System.out.println("File Extension: " + fileExtension);
        }
    }

    @FXML
    protected void add(ActionEvent event) {

        try {

            if (isValidInput()) {
                LocalDate selectedDate = date_action.getValue();
                Date sqlDate = Date.valueOf(selectedDate);
                as.add(new Action(name_action.getText(), description_action.getText(), sqlDate, location_action.getText(), id, imageAction, organized_for.getText(), Integer.parseInt(target_amount.getText()), category.getText()));

                // copy the image to the storage directory
                Path sourcePath = selectedFile.toPath();
                Path destPath = new File(destDirectoryPath, selectedFile.getName()).toPath();
                Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
                //System.out.println("Image copied successfully to: " + destPath);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Action added successfully!");
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

        LocalDate currentDate = LocalDate.now();

        if (date_action.getValue().isBefore(currentDate)) {
            errorLabel.setText("Please enter a future date.");
            return false;
        }
        if (organized_for.getText().isEmpty()) {
            errorLabel.setText("For whom this action is organized ?");
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

        imageTextName.setText("");

        List<Association> associations = associationService.getDisabledAssociations(1);

        ids = new int[associations.size()];
        names = new String[associations.size()];

        for (int i = 0; i < associations.size(); i++) {
            ids[i] = associations.get(i).getId();
            names[i] = associations.get(i).getName();
            id_association.getItems().add(names[i]);
        }


        id_association.setValue(names[0]);

        id_association.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            int selectedIndex = id_association.getItems().indexOf(newValue);
            id= ids[selectedIndex];
            System.out.println("Selected: " + id);

        });
    }
}
