package com.esprit.services;

import com.esprit.models.DemandeSponsoring;
import com.esprit.models.Sponsoring;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SponsoringService implements IService<Sponsoring> {

    private Connection connection;

    public SponsoringService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Sponsoring sponsoring) {
        String req = "INSERT into sponsoring(budget, datesponsoring, type) values (?, ?, ?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setDouble(1, sponsoring.getBudget());
            pst.setDate(2, sponsoring.getDatesponsoring());
            pst.setString(3, sponsoring.getType());
            pst.executeUpdate();
            System.out.println("sponsoring ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Sponsoring sponsoring) throws SQLException {

    }

    @Override
    public DemandeSponsoring update(Sponsoring sponsoring) throws SQLException {

        return null;
    }

    @Override
    public List<Sponsoring> readAll() throws SQLException {
        return null;
    }

    public void modifier(Sponsoring sponsoring) {
        String req = "UPDATE sponsoring set budget = ?, datesponsoring = ?, type = ? where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setDouble(1, sponsoring.getBudget());
            pst.setDate(2, sponsoring.getDatesponsoring());
            pst.setString(3, sponsoring.getType());
            pst.setInt(4, sponsoring.getId());

            pst.executeUpdate();
            System.out.println("Sponsoring modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void supprimer(Sponsoring sponsoring) {
        String req = "DELETE from sponsoring where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, sponsoring.getId());
            pst.executeUpdate();
            System.out.println("Sponsoring supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Sponsoring> afficher() {
        List<Sponsoring> sponsorings = new ArrayList<>();

        String req = "SELECT * from sponsoring";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                sponsorings.add(new Sponsoring(rs.getInt("id"), rs.getDouble("budget"), rs.getDate("datesponsoring"), rs.getString("type")));
                //sponsorings.add(new Sponsoring(rs.getInt(1), rs.getDouble(2), rs.getDate(3), rs.getString(4)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return sponsorings;
    }


    public Sponsoring rechercheSponsoring(int id) {
        Sponsoring sponsoring = null;
        String req = "SELECT * FROM sponsoring WHERE id = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                sponsoring = new Sponsoring(
                        rs.getInt("id"),
                        rs.getDouble("budget"),
                        rs.getDate("datesponsoring"), // Assuming date_sponsoring is stored as DATE in the database
                        rs.getString("type") // Assuming type is stored as VARCHAR in the database
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sponsoring;
    }
}
