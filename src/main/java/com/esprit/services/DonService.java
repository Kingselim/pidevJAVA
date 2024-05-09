package com.esprit.services;

import com.esprit.models.Don;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonService {

    private Connection connection;

    public  DonService() {
        connection = DataSource.getInstance().getConnection();
    }

    public void add(Don don) {

        String req = "INSERT INTO don(montant, id_action, date_donation,donateur,email) VALUES ('" + don.getMontant() + "', '" + don.getId_action() + "', '" + don.getDate_donation() + "', '" + don.getDonateur()+ "', '" + don.getEmail()  +"');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Don ajouté!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Don> getAll(){

        List<Don> dons = new ArrayList<>();

        String req = "SELECT * FROM don";

        try {
            Statement pst = connection.createStatement();
            ResultSet res = pst.executeQuery(req);
            while (res.next()) {

                Don don = new Don();

                don.setId(res.getInt("id"));
                don.setDate_donation(res.getDate("date_donation"));
                don.setId_action(res.getInt("id_action"));
                don.setMontant(res.getInt("montant"));
                don.setDonateur(res.getString("donateur"));
                don.setEmail(res.getString("email"));


                dons.add(don);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dons;
    }


    public List<Don> getById_action(int id){

        List<Don> dons = new ArrayList<>();

        String req = "SELECT * FROM don WHERE id_action = ?";

        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet res = pst.executeQuery();

            while (res.next()) {

                Don don = new Don();

                don.setId(res.getInt("id"));
                don.setDate_donation(res.getDate("date_donation"));
                don.setId_action(res.getInt("id_action"));
                don.setMontant(res.getInt("montant"));
                don.setDonateur(res.getString("donateur"));
                don.setEmail(res.getString("email"));

                dons.add(don);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dons;
    }
}