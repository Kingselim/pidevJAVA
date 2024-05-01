package com.esprit.controllers;

import com.esprit.models.transaction;
import com.esprit.services.transactionService;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import java.util.List;

public class statTransaction {

    @FXML
    private PieChart statTransaction;

    private transactionService transactionService;

    public statTransaction() {
        transactionService = new transactionService(); // Initialize your service
    }

    @FXML
    public void initialize() {
        // Call the method to get top demanded loans with a specified limit
        List<Integer> topDemandePrets = transactionService.getAllAccountIds(); // You can specify the limit as needed

        // Clear any existing data in the pie chart
        statTransaction.getData().clear();

    }



}
