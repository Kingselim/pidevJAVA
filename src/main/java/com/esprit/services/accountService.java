package com.esprit.services;

import com.esprit.models.account;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class accountService implements IService<account> {
private final Connection connection;
public accountService(){ connection = DataSource.getInstance().getConnection(); }

    @Override
    public void ajouter(account account)
    {
    String req = "INSERT INTO account(solde, type_account, date_creation, state, id_association_id) VALUES (?,?,?,?,?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(5, account.getId_association_id());
            pst.setInt(4, account.getState());
            pst.setString(3, account.getDate_creation());
            pst.setString(2, account.getType_account());
            pst.setDouble(1, account.getSolde());
            pst.executeUpdate();
            System.out.println("Compte ajoutée avec succée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(account account) {
    String req = "UPDATE account SET id= ?,solde = ?,type_account = ?,date_creation = ?,state = ?,id_association_id = ? WHERE id =?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, account.getId());
            pst.setDouble(2, account.getSolde());
            pst.setString(3, account.getType_account());
            pst.setString(4, account.getDate_creation());
            pst.setInt(5, account.getState());
            pst.setInt(6, account.getId_association_id());
            pst.setInt(7, account.getId());
            pst.executeUpdate();
            System.out.println("Compte modifiée avec succée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(account account) {
    String req = "DELETE FROM account WHERE id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, account.getId());
            pst.executeUpdate();
            System.out.println("Compte supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

   @Override
    public List<account> afficher() {
    List<account> accounts = new ArrayList<>();
    String req = "SELECT * FROM account";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int accountId = rs.getInt("id");
               accountService ac = new accountService();
               account account = ac.rechercheaccount(accountId);
               if(account != null){
                   accounts.add(new account(rs.getInt("id"), rs.getDouble("solde"), rs.getString("type_account"), rs.getString("date_creation"), rs.getInt("state"), rs.getInt("id_association_id")));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }
    //fonction recherche compte en fonction de l'id
    public List<Integer> Recupereridaccount()
    {
        List<Integer> L = new ArrayList<>();
        String req = "SELECT id FROM account";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                L.add(rs.getInt("id"));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return L;
    }

    public account rechercheaccount(int id) {
        account acc = null;
        String req = "SELECT * FROM account WHERE id = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                acc = new account(
                        rs.getInt("id"),
                        rs.getDouble("solde"),
                        rs.getString("type_account"),
                        rs.getString("date_creation"),
                        rs.getInt("state"),
                        rs.getInt("id_association_id")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return acc;
    }
    // Méthode pour mettre à jour le solde du compte
    public void updateSolde(double amount, int accountId) {
        String sql = "UPDATE account SET solde = solde + ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de la base de données
        }
    }

}
