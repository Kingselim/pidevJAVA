package com.esprit.services;

import com.esprit.models.User;
import com.esprit.models.Wallet;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WalletService implements IService<Wallet> {
    private Connection connection;

    public WalletService() {
        connection = DataSource.getInstance().getConnection();
    }

    public void ajouter(Wallet wallet)
    {

            String req = "INSERT INTO walet(rate,nbrconnection,lastconnection,idofuser_id) VALUES (?,?,?,?)";
            try {
                PreparedStatement pst = connection.prepareStatement(req);
                pst.setInt(1, wallet.getRate());
                pst.setInt(2, wallet.getNbrconnection());
                pst.setString(3, wallet.getLastconnection());
                pst.setInt(4,wallet.getIdofuser_id());
                pst.executeUpdate();
                System.out.println("Wallet ajoutée !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }



    }
    public void modifier(Wallet wallet)
    {

        String req = "UPDATE walet SET rate=?,nbrconnection=?,lastconnection=?,idofuser_id=? WHERE id=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, wallet.getRate());
            pst.setInt(2, wallet.getNbrconnection());
            pst.setString(3, wallet.getLastconnection());
            pst.setInt(4,wallet.getIdofuser_id());
            pst.setInt(5,wallet.getId());
            pst.executeUpdate();
            System.out.println("Wallet modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void supprimer(Wallet wallet)
    {
        String req = "DELETE from walet where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, wallet.getId());
            pst.executeUpdate();
            System.out.println("Wallet supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public void supprimerSelonIdUser(int id)
    {
        String req = "DELETE from walet where idofuser_id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Wallet supprmiée Selon l'id du User !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public List<Wallet> afficher()
    {
        List<Wallet> wlist = new ArrayList<>();


        String req = "SELECT * from walet";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                wlist.add(new Wallet(rs.getInt("id"),
                        rs.getInt("rate"),
                        rs.getString("lastconnestion"),
                        rs.getInt("nbrconnection"),
                        rs.getInt("idofuserId")


                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return wlist;
    }

    public Wallet chercherWallet(int id)
    {
        String req = "SELECT * from walet WHERE idofuser_id=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            Wallet wallet = new Wallet();
            while (rs.next()) {
                wallet.setId(rs.getInt("id"));
                wallet.setRate(rs.getInt("rate"));
                wallet.setLastconnection(rs.getString("lastconnection"));
                wallet.setIdofuser_id(rs.getInt("idofuser_id"));
                wallet.setNbrconnection(rs.getInt("nbrconnection"));

            }
            System.out.println("le wallet est remplir !");
            return wallet;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Wallet chercherWalletAvecId(int id)
    {
        String req = "SELECT * from walet WHERE id=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            Wallet wallet = new Wallet();
            while (rs.next()) {
                wallet.setId(rs.getInt("id"));
                wallet.setRate(rs.getInt("rate"));
                wallet.setLastconnection(rs.getString("lastconnection"));
                wallet.setIdofuser_id(rs.getInt("idofuser_id"));
                wallet.setNbrconnection(rs.getInt("nbrconnection"));

            }
            System.out.println("le wallet est remplir !");
            return wallet;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
