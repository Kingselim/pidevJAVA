package com.esprit.controllers;

import com.esprit.models.DemandeSponsoring;
import com.esprit.services.DemandeSponsoringService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.glxn.qrgen.QRCode;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AfficherDemandeSponsoring {

    @FXML
    private GridPane gridPane;

    @FXML
    private Button buttonajout;

//    @FXML
//    private ImageView qrCodeImageView;
    @FXML
    private ImageView imageQr;
    @FXML
    private TextField recherche;

    @FXML
    private TextField searchdemande;

    private final DemandeSponsoringService demandeSponsoringService = new DemandeSponsoringService();
    private DemandeSponsoring demandeSponsoring;

    private List<DemandeSponsoring> demandes;



    @FXML
    public void initialize() {
//        DemandeSponsoring dem = new DemandeSponsoring();
//        try {
//            QRcode(dem);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        try {
            demandes = demandeSponsoringService.readAll();
            refreshGrid();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void refreshGrid() throws SQLException {
        gridPane.getChildren().clear(); // Clear existing content

        // Get the search keyword from the text field
        //String searchKeyword = recherche.getText().trim();

        List<DemandeSponsoring> demandes = demandeSponsoringService.readAll();

        // Check if a search keyword is provided
      //  if (searchKeyword.isEmpty()) {
            // If no keyword provided, retrieve all data
    //        demandes = demandeSponsoringService.readAll();
    //    } else {
            // If keyword provided, search by association name
    //       demandes = demandeSponsoringService.searchByAssociationName(searchKeyword);
    //    }

        // Add column headers
        gridPane.add(createLabel("ID"), 0, 0);
        gridPane.add(createLabel("Budget"), 1, 0);
        gridPane.add(createLabel("Date Début"), 2, 0);
        gridPane.add(createLabel("Date Fin"), 3, 0);
        gridPane.add(createLabel("User ID"), 4, 0);
        gridPane.add(createLabel("Sponsoring ID"), 5, 0);
        gridPane.add(createLabel("Nom Association"), 6, 0);

        int rowIndex = 1;
        for (DemandeSponsoring demande : demandes) {
            Button editButton = createEditButton(demande);
            Button deleteButton = createDeleteButton(demande);
            Button showQr = createQRButton(demande);

            gridPane.add(editButton, 7, rowIndex);
            gridPane.add(deleteButton, 8, rowIndex);
            gridPane.add(showQr, 9, rowIndex);
            gridPane.add(createLabel(String.valueOf(demande.getId())), 0, rowIndex);
            gridPane.add(createLabel(String.valueOf(demande.getBudget())), 1, rowIndex);
            gridPane.add(createLabel(String.valueOf(demande.getDatedebut())), 2, rowIndex);
            gridPane.add(createLabel(String.valueOf(demande.getDatefin())), 3, rowIndex);
            gridPane.add(createLabel(String.valueOf(demande.getId_user())), 4, rowIndex);
            if (demande.getSponsoring().getType().equals("Autre")) {
                gridPane.add(createLabel(String.valueOf(demande.getAutretype())), 5, rowIndex);
            } else {
                gridPane.add(createLabel(String.valueOf(demande.getSponsoring().getType())), 5, rowIndex);
            }
            gridPane.add(createLabel(String.valueOf(demande.getNomassociation())), 6, rowIndex);
            rowIndex++;
        }
    }








    private Button createEditButton(DemandeSponsoring demande) {
        this.demandeSponsoring = demande;
        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> {
            openEditInterface(demande);
        });
        return editButton;
    }

    private void openEditInterface(DemandeSponsoring demandeSponsoring) {
        try {
            // Load the ModifierDemandeSponsoring FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierDemandeSponsoring.fxml"));
            Parent root = loader.load();

            // Get the controller of the ModifierDemandeSponsoring
            ModifierDemandeSponsoring modifierController = loader.getController();

            // Pass the selected DemandeSponsoring object to the ModifierDemandeSponsoring controller
            modifierController.initData(demandeSponsoring,this);

            // Create a new stage for the ModifierDemandeSponsoring
            Stage editStage = new Stage();
            editStage.initModality(Modality.APPLICATION_MODAL);
            editStage.setScene(new Scene(root));
            editStage.showAndWait(); // Show the ModifierDemandeSponsoring and wait for it to close

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private javafx.scene.control.Label createLabel(String text) {
        javafx.scene.control.Label label = new javafx.scene.control.Label(text);
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: #333333;");
        return label;
    }

    private Button createDeleteButton(DemandeSponsoring demandeSponsoring) {
        Button editButton = new Button("Delete");
        editButton.setOnAction(event -> {
            try {
                demandeSponsoringService.supprimer(demandeSponsoring);
                refreshGrid(); // Refresh the grid after deletion

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return editButton;
    }
    private Button createQRButton(DemandeSponsoring demandeSponsoring) {
        Button editButton = new Button("Qr");
        editButton.setOnAction(event -> {
            try {
                QRcode(demandeSponsoring);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            File f = new File("src/main/java/com/esprit/qr/" + demandeSponsoring.getId_sponsoring()+ ".jpg");
            Image image = new Image(f.toURI().toString());

            imageQr.setImage(image);
        });
        return editButton;
    }

    @FXML
    public void handleSearch() {
        String searchText = searchdemande.getText().toLowerCase(); // Get the search text
        List<DemandeSponsoring> filteredList = new ArrayList<>(); // Create a new list to store filtered items

        // Iterate through the original list and add items that match the search criteria
        for (DemandeSponsoring demande : demandes) {
            if (demande.getNomassociation().toLowerCase().contains(searchText)) {
                filteredList.add(demande);
            }
        }

        // Update the grid with the filtered list
        try {
            updateGrid(filteredList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateGrid(List<DemandeSponsoring> filteredList) throws SQLException {
        gridPane.getChildren().clear(); // Clear existing content

        // Add column headers
        gridPane.add(createLabel("ID"), 0, 0);
        gridPane.add(createLabel("Budget"), 1, 0);
        gridPane.add(createLabel("Date Début"), 2, 0);
        gridPane.add(createLabel("Date Fin"), 3, 0);
        gridPane.add(createLabel("User ID"), 4, 0);
        gridPane.add(createLabel("Sponsoring ID"), 5, 0);
        gridPane.add(createLabel("Nom Association"), 6, 0);

        int rowIndex = 1;
        for (DemandeSponsoring demande : filteredList) {
            // Add buttons and labels for each demande
            rowIndex++;
        }
    }
    public void ajoutdemande(javafx.event.ActionEvent actionEvent) {
        try {
            // Fermeture de la fenêtre actuelle



            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterDemandeSponsoring.fxml"));
            Parent root = loader.load();
            // Création de la scène
            Scene scene = new Scene(root);
            // Création de la nouvelle fenêtre pour afficher la vue AffcherCompte.fxml
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Afficher demande sponsoring");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




    public void btnpdf(ActionEvent actionEvent) {



    }



    public void retour(ActionEvent actionEvent) {
    }

    public void gouser(ActionEvent actionEvent) {
    }

    public void gocompte(ActionEvent actionEvent) {
    }

    public void gopret(ActionEvent actionEvent) {
    }

    public void goseminair(ActionEvent actionEvent) {
    }

    public void goassociation(ActionEvent actionEvent) {
    }

//    public void generateQr(ActionEvent actionEvent) {
//       // QRcode("salem");
//    }
    public static String projectPath = System.getProperty("user.dir").replace("\\", "/");
    private void QRcode(DemandeSponsoring demandeSponsoring) throws FileNotFoundException, IOException {
        String contenue =  demandeSponsoring.toString();
        ByteArrayOutputStream out = QRCode.from(contenue).to(net.glxn.qrgen.image.ImageType.JPG).stream();
        File f = new File("src/main/java/com/esprit/qr/" + demandeSponsoring.getId_sponsoring()+ ".jpg");
        FileOutputStream fos = new FileOutputStream(f); //creation du fichier de sortie
        fos.write(out.toByteArray()); //ecrire le fichier du sortie converter
        fos.flush(); // creation final

    }


}
