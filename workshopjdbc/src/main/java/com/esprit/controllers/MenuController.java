/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.fxml.FXMLLoader.load;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class MenuController implements Initializable {

    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane AP;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // loadPage("CategorieInterface");
        loadPage("AfficherSponsoring");
    }    
    
    private void loadPage(String page){          
        Parent root = null;
        try {
        root = load(getClass().getResource("/"+page+".fxml"));

        } catch (Exception ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE,null,ex);
        }
        bp.setCenter(root);
    }

    @FXML
    void InterAfficher(ActionEvent event) {
        loadPage("AfficherSponsoring");
    }

    @FXML
    void InterAjouter(ActionEvent event) {
        loadPage("AjouterSponsoring");
    }

    public void InterAjouterDemande(ActionEvent actionEvent) {
        loadPage("GestionDemandeSponsoring");
    }

    public  void InterAfficherD(ActionEvent actionEvent){
        loadPage("GestionDemandeSponsoringAff");
    }
}
