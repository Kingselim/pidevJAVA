package com.esprit.controllers;

import com.esprit.models.User;
import com.esprit.models.Wallet;
import com.esprit.services.UserService;
import com.esprit.services.WalletService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifierWallet implements Initializable {

    public void setData(String dataToPass) {
        WalletService walletService = new WalletService();
        Wallet wallet = new Wallet();
        wallet = walletService.chercherWalletAvecId(Integer.valueOf(dataToPass));
        System.out.println(dataToPass);
        id.setText(String.valueOf(wallet.getId()));
        iduser.setText(String.valueOf(wallet.getIdofuser_id()));
        lastconnection.setText(wallet.getLastconnection());
        nbrconnection.setText(String.valueOf(wallet.getNbrconnection()));
        rate.setText(String.valueOf(wallet.getRate()));

        Wallet finalWallet = wallet;
        modifier.setOnAction(event -> {
            try {
                finalWallet.setRate(Integer.parseInt(rate.getText()));
                finalWallet.setNbrconnection(Integer.parseInt(nbrconnection.getText()));
                finalWallet.setLastconnection(lastconnection.getText());
                finalWallet.setId(Integer.parseInt(id.getText()));
                finalWallet.setIdofuser_id(Integer.parseInt(iduser.getText()));
                walletService.modifier(finalWallet);
                // Fermeture de la fenêtre actuelle
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();

                String dataToPasss = String.valueOf(finalWallet.getIdofuser_id());
                SharedData.setVariable(dataToPasss);

                // Chargement de la vue AffcherCompte.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierUser.fxml"));
                Parent root = loader.load();

                ModifierUser controller = loader.getController();
                controller.setData(dataToPasss);

                // Création de la scène
                Scene scene = new Scene(root);
                // Création de la nouvelle fenêtre pour afficher la vue AffcherCompte.fxml
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Page de Modification");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private TextField id;

    @FXML
    private TextField iduser;

    @FXML
    private TextField lastconnection;

    @FXML
    private TextField nbrconnection;

    @FXML
    private TextField rate;
    @FXML
    private Button modifier;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {


        //controle de saisie a implementer ici

    }


    @FXML
    void retour(ActionEvent event) {
        // Fermeture de la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        try {
            // Chargement de la vue AffcherCompte.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUser.fxml"));
            Parent root = loader.load();
            // Création de la scène
            Scene scene = new Scene(root);
            // Création de la nouvelle fenêtre pour afficher la vue AffcherCompte.fxml
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Se connecter");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

