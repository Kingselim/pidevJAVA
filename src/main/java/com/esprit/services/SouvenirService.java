package com.esprit.services;

import com.esprit.models.Souvenir;
import com.esprit.models.User;
import com.esprit.utils.DataSource;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SouvenirService implements IService<Souvenir>{
    private Connection connection;

    public SouvenirService() {
        connection = DataSource.getInstance().getConnection();
    }
    public void ajouter(Souvenir souvenir) {
        String req = "INSERT INTO souvenir(email,password,date) VALUES (?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, souvenir.getEmail());
            pst.setString(2, souvenir.getPassword());
            pst.setString(3, souvenir.getDate());

            pst.executeUpdate();
            System.out.println("Souvenir ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Souvenir souvenir) {

    }



    @Override
    public List<Souvenir> afficher() {
        List<Souvenir> souvenir = new ArrayList<>();
        String req = "SELECT * from souvenir";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                souvenir.add(new Souvenir(rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("date")

                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return souvenir;
    }


    public Souvenir recupererSouvenir(String emailverif)
    {
        String req = "SELECT * FROM Souvenir WHERE email=?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, emailverif);
            ResultSet rs = pst.executeQuery();
            Souvenir souvenir = new Souvenir();
            while (rs.next()) {
                souvenir.setEmail(rs.getString("email"));
                souvenir.setPassword( rs.getString("password"));
                souvenir.setDate(rs.getString("date"));
            }
            System.out.println("l'Souvenir est remplir !");
            return souvenir;
        }catch (SQLException e) {

            System.out.println(e.getMessage());
        }
        return null;
    }



    public void supprimer(Souvenir souvenir)
    {

            String req = "DELETE from Souvenir where email = ?;";
            try {
                PreparedStatement pst = connection.prepareStatement(req);
                pst.setString(1, souvenir.getEmail());
                pst.executeUpdate();
                System.out.println("Souvenir supprmiée !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


    }
}
