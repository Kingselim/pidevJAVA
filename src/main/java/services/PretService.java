/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

/**
 *
 * @author hassa
 */




import entities.Pret;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.Myconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PretService {

    private Connection cnx;

    public PretService() {
        cnx = Myconnection.getInstance().getCnx();
    }

    // Méthode pour ajouter un prêt
    public void addPret(Pret pret) {
        String query = "INSERT INTO Pret (montant_pret, taux_interet, duree, nom_association) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setFloat(1, pret.getMontantPret());
            pst.setFloat(2, pret.getTauxInteret());
            pst.setString(3, pret.getDuree());
            pst.setString(4, pret.getNomAssociation());

            pst.executeUpdate();
            System.out.println("Pret ajouté avec succès !");
        } catch (SQLException ex) {
            // Gestion des exceptions SQL
            System.err.println("Erreur lors de l'ajout du prêt : " + ex.getMessage());
        }
    }

    // Méthode pour mettre à jour un prêt
    public void updatePret(Pret pret) {
        if (pret.getId() == null) {
            System.err.println("Cannot update Pret with null ID.");
            return;
        }
        String query = "UPDATE Pret SET montant_pret=?, taux_interet=?, duree=?, nom_association=? WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setObject(1, pret.getMontantPret()); // Use setObject to handle null values
            pst.setObject(2, pret.getTauxInteret());
            pst.setString(3, pret.getDuree());
            pst.setString(4, pret.getNomAssociation());
            pst.setLong(5, pret.getId());

            pst.executeUpdate();
            System.out.println("Pret mis à jour avec succès");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour du prêt : " + ex.getMessage());
        }
    }

    // Méthode pour supprimer un prêt par son ID
    public void deletePret(long pretId) {
        String query = "DELETE FROM Pret WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setLong(1, pretId);

            pst.executeUpdate();
            System.out.println("Pret supprimé avec succès");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression du prêt : " + ex.getMessage());
        }
    }

    public ObservableList<Pret> getAllPrets() {
        ObservableList<Pret> prets = FXCollections.observableArrayList();
        String query = "SELECT * FROM Pret";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Pret pret = new Pret();
                    pret.setId(rs.getLong("id"));
                    pret.setMontantPret(rs.getFloat("montant_pret"));
                    pret.setTauxInteret(rs.getFloat("taux_interet"));
                    pret.setDuree(rs.getString("duree"));
                    pret.setNomAssociation(rs.getString("nom_association"));

                    prets.add(pret);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des prêts : " + ex.getMessage());
        }
        return prets;
    }


    // Méthode pour récupérer un prêt par son ID
    public Pret getPretById(long pretId) {
        String query = "SELECT * FROM Pret WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setLong(1, pretId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Pret pret = new Pret();
                    pret.setId(rs.getLong("id"));
                    pret.setMontantPret(rs.getFloat("montant_pret"));
                    pret.setTauxInteret(rs.getFloat("taux_interet"));
                    pret.setDuree(rs.getString("duree"));
                    pret.setNomAssociation(rs.getString("nom_association"));

                    return pret;
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération du prêt : " + ex.getMessage());
        }
        return null;
    }

    public List<Pret> getTopDemandePrets(int limit) {
        String query = "SELECT p.id, p.montant_pret, p.taux_interet, p.duree, p.nom_association, COUNT(d.id) AS demandeCount " +
                "FROM Pret p " +
                "LEFT JOIN Demande d ON p.id = d.id_pret_id " +
                "GROUP BY p.id, p.montant_pret, p.taux_interet, p.duree, p.nom_association " +
                "ORDER BY demandeCount DESC " +
                "LIMIT ?";

        List<Pret> topDemandePrets = new ArrayList<>();

        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, limit);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Pret pret = new Pret();
                    pret.setId(rs.getLong("id"));
                    pret.setMontantPret(rs.getFloat("montant_pret"));
                    pret.setTauxInteret(rs.getFloat("taux_interet"));
                    pret.setDuree(rs.getString("duree"));
                    pret.setNomAssociation(rs.getString("nom_association"));

                    topDemandePrets.add(pret);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des prêts les plus demandés : " + ex.getMessage());
        }

        return topDemandePrets;
    }

    public List<Pret> getTop3PretsAvecTauxInteretMax() {
        String query = "SELECT * FROM (SELECT * FROM Pret ORDER BY taux_interet DESC LIMIT 3) AS Top3Prets";
        List<Pret> top3Prets = new ArrayList<>();

        try (PreparedStatement pst = cnx.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Pret pret = new Pret();
                pret.setId(rs.getLong("id"));
                pret.setMontantPret(rs.getFloat("montant_pret"));
                pret.setTauxInteret(rs.getFloat("taux_interet"));
                pret.setDuree(rs.getString("duree"));
                pret.setNomAssociation(rs.getString("nom_association"));

                top3Prets.add(pret);
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des 3 prêts avec les taux d'intérêt maximaux : " + ex.getMessage());
        }

        return top3Prets;
    }


    public List<Pret> searchPrets(String searchTerm) {
        String query = "SELECT * FROM Pret WHERE montant_pret LIKE ? OR nom_association LIKE ?";
        List<Pret> results = new ArrayList<>();

        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            String searchPattern = "%" + searchTerm + "%";
            pst.setString(1, searchPattern);
            pst.setString(2, searchPattern);

            try (ResultSet rs = pst.executeQuery()) {
                results = Stream.generate(() -> {
                            try {
                                if (rs.next()) {
                                    Pret pret = new Pret();
                                    pret.setId(rs.getLong("id"));
                                    pret.setMontantPret(rs.getFloat("montant_pret"));
                                    pret.setTauxInteret(rs.getFloat("taux_interet"));
                                    pret.setDuree(rs.getString("duree"));
                                    pret.setNomAssociation(rs.getString("nom_association"));
                                    return pret;
                                } else {
                                    return null;
                                }
                            } catch (SQLException ex) {
                                System.err.println("Error while processing ResultSet: " + ex.getMessage());
                                return null;
                            }
                        })
                        // Use Stream.takeWhile to stop when no more elements are available
                        .takeWhile(Objects::nonNull)
                        // Collect the stream into a List
                        .collect(Collectors.toList());
            }
        } catch (SQLException ex) {
            System.err.println("Error during SQL query execution: " + ex.getMessage());
        }

        return results;
    }



}

