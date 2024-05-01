package com.esprit.controllers;

import com.esprit.models.account;
import com.esprit.services.accountService;
import com.esprit.services.transactionService;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.cert.PolicyNode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AffichageCompteController {
    @FXML
    private FlowPane flowPlane;

    @FXML
    private TextField recherche;


    @FXML
    private ComboBox<account> select;
    private accountService accountService = new accountService();
    private com.esprit.services.transactionService transactionService;


    @FXML
    void initialize() {
        try {
            // Initialiser accountService
            accountService = new accountService();
            // Initialiser transactionService
            transactionService = new transactionService();
            // Charger les données depuis la base de données lors de l'initialisation de la fenêtre
            loadAccounts();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //chagement de puis a base de donnée des informations
    private void loadAccounts() throws SQLException {
        List<account> accounts = accountService.afficher();

        for (account account : accounts) {
            Label label = createAccountLabel(account);
            PolicyNode flowPane;
            flowPlane.getChildren().add(label);
        }
    }

    private Label createAccountLabel(account account) {
        Label label = new Label("ID: " + account.getId() +"\n"+
                " Solde: " + account.getSolde() +"\n"+
                " Type de compte: " + account.getType_account() + "\n"+
                " Etat: " + account.getState() +"\n"+
                " Date de création: " + account.getDate_creation() +"\n"+
                " Association ID: " + account.getId_association_id());

        label.setOnMouseClicked(event -> openEditOrDeleteDialog(account));

        return label;
    }

    private void openEditOrDeleteDialog(account account) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choisir une action");
        alert.setHeaderText("Que souhaitez-vous faire avec ce compte ?");
        alert.setContentText("Choisissez votre option :");

        ButtonType editButton = new ButtonType("Modifier");
        ButtonType deleteButton = new ButtonType("Supprimer");
        ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(editButton, deleteButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == editButton) {
                // Ouvrir l'interface de modification
                openEditInterface(account);
            } else if (result.get() == deleteButton) {
                // Supprimer le compte
                deleteAccount(account);
            }
        }
    }

    //Ouverture de l'interface modifier
    private void openEditInterface(account account) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCompte.fxml"));
            Parent root = loader.load();

            ModifierCompteController controller = loader.getController();
            controller.initData(account);

            Stage currentStage = (Stage) flowPlane.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Modifier Compte");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void effectuer_transaction(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterTransaction.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }


    @FXML
    void Return(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MenuAdmin.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }


    @FXML
    void RedirectToAjouterCompte(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCompte.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) flowPlane.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Ajouter Compte");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur de chargement de la vue AjouterCompte.fxml
        }
    }

    @FXML
    void select(ActionEvent event) {
        // Récupérer le compte sélectionné dans le ComboBox
        account selectedAccount = select.getValue();

        // Vérifier si un compte est sélectionné
        if (selectedAccount != null) {
            // Afficher les détails du compte sélectionné (ou effectuer une action)
            System.out.println("Compte sélectionné : " + selectedAccount.toString());
        } else {
            // Aucun compte sélectionné
            System.out.println("Aucun compte sélectionné.");
        }
    }

    @FXML
    void deleteAccount(account account) {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation de suppression");
        confirmationDialog.setHeaderText("Êtes-vous sûr de vouloir supprimer ce compte ?");
        confirmationDialog.setContentText("Cette action est irréversible.");

        Optional<ButtonType> result = confirmationDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Supprimer les transactions associées à ce compte
                transactionService.deleteTransactionsByAccountId(account.getId());
                // Appeler la méthode supprimer de votre service
                accountService.supprimer(account);
                // Actualiser l'affichage des comptes après la suppression
                loadAccounts();
                refreshCompte();
                showAlert("Le compte a été supprimé avec succès.");
            } catch (SQLException e) {
                showAlert("Une erreur s'est produite lors de la suppression du compte : " + e.getMessage());
            }
        }
    }

    @FXML
    void searchAccount(ActionEvent event) {
        String searchText = recherche.getText();
        if (!searchText.isEmpty()) {
            try {
                int id = Integer.parseInt(searchText);
                account account = accountService.rechercheaccount(id);
                if (account != null) {
                    // Afficher les détails du compte trouvé
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Résultat de la recherche");
                    alert.setHeaderText(null);
                    alert.setContentText("ID: " + account.getId() + "\n" +
                            "Solde: " + account.getSolde() + "\n" +
                            "Type de compte: " + account.getType_account() + "\n" +
                            "Etat: " + account.getState() + "\n" +
                            "Date de création: " + account.getDate_creation() + "\n" +
                            "Association ID: " + account.getId_association_id());
                    alert.showAndWait();
                } else {
                    // Aucun compte trouvé avec l'ID spécifié
                    showAlert("Aucun compte trouvé avec l'ID " + id);
                }
            } catch (NumberFormatException e) {
                showAlert("Veuillez saisir un ID valide.");
            }
        } else {
            showAlert("Veuillez saisir un ID pour rechercher.");
        }
    }

    // Méthode utilitaire pour afficher une alerte
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Avertissement");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void refreshCompte() {
        try {
            // Charger à nouveau les données depuis la base de données
            flowPlane.getChildren().clear(); // Effacer les transactions actuellement affichées
            loadAccounts(); // Charger les transactions mises à jour
        } catch (SQLException e) {
            showAlert("Erreur lors du rafraîchissement des transactions : " + e.getMessage());
        }
    }
}

