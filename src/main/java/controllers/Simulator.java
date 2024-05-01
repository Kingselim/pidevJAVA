package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Simulator {
    @FXML
    private TextField loanAmountField;

    @FXML
    private TextField interestRateField;

    @FXML
    private TextField loanTermField;

    @FXML
    private Label resultLabel;

    @FXML
    private void calculateMonthlyPayment() {
        try {
            double loanAmount = Double.parseDouble(loanAmountField.getText());
            double interestRate = Double.parseDouble(interestRateField.getText()) / 100;
            int loanTerm = Integer.parseInt(loanTermField.getText());

            double monthlyInterestRate = interestRate / 12;
            int numberOfPayments = loanTerm * 12;

            double monthlyPayment = (loanAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));

            resultLabel.setText(String.format("Paiement mensuel : %.2f", monthlyPayment));
        } catch (NumberFormatException e) {
            resultLabel.setText("Veuillez saisir des valeurs numériques valides.");
        }
    }

    @FXML
    private Button goback;


    private void loadFXML(String fxmlFilePath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) goback.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goback(ActionEvent actionEvent) {
        loadFXML("/Dashboard.fxml");
    }
}
