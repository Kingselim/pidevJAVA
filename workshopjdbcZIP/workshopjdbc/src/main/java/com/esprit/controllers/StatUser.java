package com.esprit.controllers;

import com.esprit.models.User;
import com.esprit.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
//D894U9UYPUMRMNJPD16AVJEZ
//e86858212bda9ac1ee8cc5744e69d286
public class StatUser implements Initializable {

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserService userService = new UserService();
       // User user = new User();
        List<User> userl = new ArrayList<>();
        userl = userService.afficherUserBanni();
        int nbrbanni=0;
        for (User user:userl
             ) {
                nbrbanni++;

        }

        userl = userService.afficher();
        int nbrnonBanni=0;
        for (User user:userl
        ) {
            nbrnonBanni++;

        }


        XYChart.Series set1 = new XYChart.Series<>();
        XYChart.Series set2 = new XYChart.Series<>();
        set1.getData().add(new XYChart.Data("Compte Bannis",nbrbanni));
        set2.getData().add(new XYChart.Data("Compte Actifs",nbrnonBanni));


        barChart.getData().addAll(set1,set2);
    }
}
