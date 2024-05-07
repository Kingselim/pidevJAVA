package com.esprit.controllers;

import com.esprit.services.transactionService;

import com.esprit.models.transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StatTransaction implements Initializable {

    @FXML
    private PieChart pieChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //ici c'est le code pour la PieChart
        List<transaction> Tpret = new ArrayList<>();
        List<transaction> Tcollect = new ArrayList<>();
        List<transaction> Tautre = new ArrayList<>();
        transactionService transactionService = new transactionService();
        Tpret=transactionService.recupererTransactionPret();
        Tcollect=transactionService.recupererTransactionCollect();
        Tautre=transactionService.recupererTransactionAutre();
        int nbrPret=0;
        for (transaction transaction:Tpret
        ) {
            nbrPret++;

        }
        int nbrCollect=0;
        for (transaction transaction:Tcollect
        ) {
            nbrCollect++;

        }
        int nbrAutre=0;
        for (transaction transaction:Tautre
        ) {
            nbrAutre++;

        }

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Pret", nbrPret),
                        new PieChart.Data("Collect", nbrCollect),
        new PieChart.Data("Autre", nbrAutre));




//        pieChartData.forEach(data ->
//                data.nameProperty().bind(
//                        Bindings.concat(
//                                data.getName(), " amount: ", data.pieValueProperty()
//                        )
//                )
//        );
        //Setting the title of the Pie chart

        pieChart.getData().addAll(pieChartData);


    }
}