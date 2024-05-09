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

public  class DemandeSponsoringService implements IService<DemandeSponsoring>  {

    private Connection connection;

    public DemandeSponsoringService() {
        connection = DataSource.getInstance().getConnection();
    }

    SponsoringService sponsoringService = new SponsoringService();

    @Override
    public void ajouter(DemandeSponsoring demandesponsoring) {
        String req = "INSERT into demande_sponsoring( idsponsoring_id, user_id, datedebut, datefin, budget, nom_association , autretype) values (?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, demandesponsoring.getId_sponsoring());
            pst.setInt(2, demandesponsoring.getId_user());
            pst.setDate(3, demandesponsoring.getDatedebut());
            pst.setDate(4, demandesponsoring.getDatefin());
            pst.setDouble(5, demandesponsoring.getBudget());
            pst.setString(6, demandesponsoring.getNomassociation());
            pst.setString(7, demandesponsoring.getAutretype());
            pst.executeUpdate();
            System.out.println("Demande Sponsoring ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(DemandeSponsoring demandeSponsoring) throws SQLException {

    }



    public void update(DemandeSponsoring demandeSponsoring) {
        String req = "UPDATE demande_sponsoring SET idsponsoring_id = ?, user_id = ?, datedebut = ?, datefin = ?, budget = ?, nom_association = ?, autretype = ? WHERE id = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, demandeSponsoring.getId_sponsoring());
            pst.setInt(2, demandeSponsoring.getId_user());
            pst.setDate(3, demandeSponsoring.getDatedebut());
            pst.setDate(4, demandeSponsoring.getDatefin());
            pst.setDouble(5, demandeSponsoring.getBudget());
            pst.setString(6, demandeSponsoring.getNomassociation());
            pst.setString(7, demandeSponsoring.getAutretype());
            pst.setInt(8, demandeSponsoring.getId()); // Assuming id is the primary key of demande_sponsoring table
            pst.executeUpdate();
            System.out.println("Demande Sponsoring modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void supprimer(DemandeSponsoring demandesponsoring) {
        String req = "DELETE from demande_sponsoring where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, demandesponsoring.getId());
            pst.executeUpdate();
            System.out.println("Demande Sponsoring supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<DemandeSponsoring> readAll() {
        List<DemandeSponsoring> demandesponsorings = new ArrayList<>();

        String req = "SELECT * from demande_sponsoring";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                DemandeSponsoring demandeSponso = new DemandeSponsoring(rs.getInt(1),rs.getDouble("budget"), rs.getDate("datedebut"), rs.getDate("datefin"),rs.getInt("idsponsoring_id"), rs.getInt("user_id"), rs.getString("nom_association"), rs.getString("autretype"));
                Sponsoring sponso = sponsoringService.rechercheSponsoring(demandeSponso.getId_sponsoring());
                demandeSponso.setSponsoring(sponso);
                demandesponsorings.add(demandeSponso);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(demandesponsorings);
        return demandesponsorings;
    }


}
