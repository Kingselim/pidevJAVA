package com.esprit.services;
import com.esprit.utils.DataSource;
import com.esprit.models.Seminaire;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceSeminaire implements IService<Seminaire> {

    private Connection cnx;

    public ServiceSeminaire() {
        cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Seminaire seminaire) {
        String query = "INSERT INTO seminar(iduserasso_id, date_seminar, description, nomassociation) VALUES (?,?,?,?);";
        try {
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, seminaire.getIduserasso_id());
            pst.setString(2, seminaire.getDate_seminar());
            pst.setString(3, seminaire.getDescription());
            pst.setString(4, seminaire.getNomassociation());
            pst.executeUpdate();
            System.out.println("Seminaire ajoute avec succee");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public void modifier(Seminaire seminaire) {
        String query = "UPDATE seminar SET id=?,iduserasso_id=?,date_seminar=?,description=?,nomassociation=? WHERE id=?;";
        try {
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, seminaire.getId());
            pst.setInt(2, seminaire.getIduserasso_id());
            pst.setString(3, seminaire.getDate_seminar());
            pst.setString(4, seminaire.getDescription());
            pst.setString(5, seminaire.getNomassociation());
            pst.setInt(6, seminaire.getId());
            pst.executeUpdate();
            System.out.println("Seminaire modifie");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Seminaire seminaire) {
        String query = "DELETE FROM seminar WHERE id = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(query);

            pst.setInt(1, seminaire.getId());
            pst.executeUpdate();
            System.out.println("Seminaire supprime");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Seminaire> afficher() {
        List<Seminaire> lu = new ArrayList<>();
        try {
            String query = "SELECT * FROM seminar";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Seminaire seminaire = new Seminaire();
                seminaire.setId(rs.getInt("id"));
                seminaire.setIduserasso_id(rs.getInt("iduserasso_id"));
                seminaire.setDate_seminar(rs.getString("date_seminar"));
                seminaire.setDescription(rs.getString("description"));
                seminaire.setNomassociation(rs.getString("nomassociation"));
                lu.add(seminaire);


            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lu;
    }
    // Méthode pour récupérer tous les idseminar de la table Seminaire

    public List<Integer> getAllSeminarIds() {
        List<Integer> seminarIds = new ArrayList<>();
        String query = "SELECT id FROM seminar";
        try {
            try (PreparedStatement statement = cnx.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int idv = resultSet.getInt("id");
                        seminarIds.add(idv);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seminarIds;
    }
}


