package services;

import entities.Demande;
import entities.User;
import entities.Pret;
import utilities.Myconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DemandeService {

    private Connection cnx;

    public DemandeService() {
        cnx = Myconnection.getInstance().getCnx();
    }

    // Méthode pour ajouter une demande
    public void addDemande(Demande demande) {
        String query = "INSERT INTO Demande (state, type_pret, motif_pret, id_user_id, id_pret_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, demande.getState());
            pst.setString(2, demande.getTypePret());
            pst.setString(3, demande.getMotifPret());
            pst.setLong(4, demande.getIdUser().getId());
            pst.setLong(5, demande.getIdPret().getId());

            pst.executeUpdate();
            System.out.println("Demande ajoutée avec succès");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'ajout de la demande : " + ex.getMessage());
        }
    }

    // Méthode pour mettre à jour une demande
    public void updateDemande(Demande demande) {
        String query = "UPDATE Demande SET state=?, type_pret=?, motif_pret=?, id_user_id=?, id_pret_id=? WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, demande.getState());
            pst.setString(2, demande.getTypePret());
            pst.setString(3, demande.getMotifPret());
            pst.setLong(4, demande.getIdUser().getId());
            pst.setLong(5, demande.getIdPret().getId());
            pst.setLong(6, demande.getId());

            pst.executeUpdate();
            System.out.println("Demande mise à jour avec succès");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour de la demande : " + ex.getMessage());
        }
    }

    // Méthode pour supprimer une demande par son ID
    public void deleteDemande(long demandeId) {
        String query = "DELETE FROM Demande WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setLong(1, demandeId);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Demande supprimée avec succès");
            } else {
                System.out.println("Aucune demande trouvée avec l'ID : " + demandeId);
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression de la demande : " + ex.getMessage());
        }
    }


    // Méthode pour récupérer toutes les demandes
    public List<Demande> getAllDemandes() {
        List<Demande> demandes = new ArrayList<>();
        String query = "SELECT * FROM Demande";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Demande demande = new Demande();
                    demande.setId(rs.getLong("id"));
                    demande.setState(rs.getString("state"));
                    demande.setTypePret(rs.getString("type_pret"));
                    demande.setMotifPret(rs.getString("motif_pret"));

                    // Récupération de l'utilisateur associé à la demande
                    UserService userService = new UserService();
                    User user = userService.getUserById(rs.getLong("id_user_id"));
                    demande.setIdUser(user);

                    // Récupération du prêt associé à la demande
                    PretService pretService = new PretService();
                    Pret pret = pretService.getPretById(rs.getLong("id_pret_id"));
                    demande.setIdPret(pret);

                    demandes.add(demande);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des demandes : " + ex.getMessage());
        }
        return demandes;
    }

    // Méthode pour récupérer une demande par son ID
    public Demande getDemandeById(long demandeId) {
        String query = "SELECT * FROM Demande WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setLong(1, demandeId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Demande demande = new Demande();
                    demande.setId(rs.getLong("id"));
                    demande.setState(rs.getString("state"));
                    demande.setTypePret(rs.getString("type_pret"));
                    demande.setMotifPret(rs.getString("motif_pret"));

                    // Récupération de l'utilisateur associé à la demande
                    UserService userService = new UserService();
                    User user = userService.getUserById(rs.getLong("id_user_id"));
                    demande.setIdUser(user);

                    // Récupération du prêt associé à la demande
                    PretService pretService = new PretService();
                    Pret pret = pretService.getPretById(rs.getLong("id_pret_id"));
                    demande.setIdPret(pret);

                    return demande;
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération de la demande : " + ex.getMessage());
        }
        return null;
    }
    // Méthode pour rechercher des demandes basées sur des critères de recherche
    public List<Demande> searchDemandes(String searchCriteria) {
        List<Demande> demandes = new ArrayList<>();
        String query = "SELECT * FROM Demande WHERE state LIKE ? OR type_pret LIKE ? OR motif_pret LIKE ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, "%" + searchCriteria + "%");
            pst.setString(2, "%" + searchCriteria + "%");
            pst.setString(3, "%" + searchCriteria + "%");

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Demande demande = new Demande();
                    demande.setId(rs.getLong("id"));
                    demande.setState(rs.getString("state"));
                    demande.setTypePret(rs.getString("type_pret"));
                    demande.setMotifPret(rs.getString("motif_pret"));

                    // Récupération de l'utilisateur associé à la demande
                    UserService userService = new UserService();
                    User user = userService.getUserById(rs.getLong("id_user_id"));
                    demande.setIdUser(user);

                    // Récupération du prêt associé à la demande
                    PretService pretService = new PretService();
                    Pret pret = pretService.getPretById(rs.getLong("id_pret_id"));
                    demande.setIdPret(pret);

                    demandes.add(demande);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la recherche des demandes : " + ex.getMessage());
        }
        return demandes;
    }

}
