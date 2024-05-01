package com.esprit.controllers;

import com.esprit.models.User;
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
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserArchive implements Initializable {

    @FXML
    private FlowPane flowpaneaff;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserService service = new UserService();
        WalletService walletService = new WalletService();
        List<User> userl = new ArrayList<>();
        userl = service.afficherUserBanni();
        System.out.println(userl.get(0));
        int nbruser = (int) userl.stream().count();
        int i = 0;
        flowpaneaff.setHgap(10);
        flowpaneaff.setVgap(20);
        for (User user : userl) {
            Label labelid = new Label(String.valueOf(user.getId()));
            labelid.setPrefWidth(100);


            Label label = new Label(user.getName());
            label.setPrefWidth(100);

            Label labelPrenom = new Label(user.getLastname());
            labelPrenom.setPrefWidth(100);

            Label labelEmail = new Label(user.getEmail());
            labelEmail.setPrefWidth(100);


            Button buttonBan = new Button("Amnistrer");
                buttonBan.setOnAction(event -> {
                    //walletService.supprimerSelonIdUser(user.getId());
                    //service.supprimer(user);
                    user.setState("1");
                    service.modifier(user);
                    flowpaneaff.getChildren().clear();
                    initialize(url, resourceBundle);


                });


            flowpaneaff.getChildren().add(labelid);
            flowpaneaff.getChildren().add(label);
            flowpaneaff.getChildren().add(labelPrenom);
            flowpaneaff.getChildren().add(labelEmail);
            flowpaneaff.getChildren().add(buttonBan);




        }
    }
    @FXML
    void logout(ActionEvent event) {

    }



}
