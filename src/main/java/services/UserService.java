/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;


import entities.User;
import utilities.Myconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author hassa
 */
public class UserService {
    private Connection cnx;

    public UserService() {
        cnx = Myconnection.getInstance().getCnx();
    }

    // Méthode pour ajouter un utilisateur
    public void addUser(User user) {
        String query = "INSERT INTO User (id) VALUES (?)";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setLong(1, user.getId());

            pst.executeUpdate();
            System.out.println("Utilisateur ajouté avec succès");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'ajout de l'utilisateur : " + ex.getMessage());
        }
    }

    // Méthode pour mettre à jour un utilisateur
    public void updateUser(User user) {
        String query = "UPDATE User SET id=? WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setLong(1, user.getId());
            pst.setLong(2, user.getId());

            pst.executeUpdate();
            System.out.println("Utilisateur mis à jour avec succès");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour de l'utilisateur : " + ex.getMessage());
        }
    }

    // Méthode pour supprimer un utilisateur par son ID
    public void deleteUser(long userId) {
        String query = "DELETE FROM User WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setLong(1, userId);

            pst.executeUpdate();
            System.out.println("Utilisateur supprimé avec succès");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression de l'utilisateur : " + ex.getMessage());
        }
    }

    // Méthode pour récupérer tous les utilisateurs
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM User";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("id"));
                    // Ajouter d'autres attributs si nécessaire
                    users.add(user);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des utilisateurs : " + ex.getMessage());
        }
        return users;
    }

    // Méthode pour récupérer un utilisateur par son ID
    public User getUserById(long userId) {
        String query = "SELECT * FROM User WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setLong(1, userId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("id"));
                    // Ajouter d'autres attributs si nécessaire
                    return user;
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération de l'utilisateur : " + ex.getMessage());
        }
        return null;
    }
}
