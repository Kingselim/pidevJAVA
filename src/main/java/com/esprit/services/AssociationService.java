package com.esprit.services;

import com.esprit.models.Association;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssociationService  implements IAssociationAction<Association> {

    private Connection connection;

    public AssociationService() {
        connection = DataSource.getInstance().getConnection();
    }

    public void add(Association association) {

        String req = "INSERT INTO association(name, phone, email, city, state, description, statut, id_user, facebook_adresse) VALUES ('" + association.getName() + "', '" + association.getPhone() + "', '" + association.getEmail() + "', '" + association.getCity() + "', '" + association.getState() + "', '" + association.getDescription() + "', '" + association.getStatut() + "', '" + association.getId_user() + "', '" +association.getFacebook_adresse()  +"');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Association ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void delete(int id) {


        String req = "DELETE from association where id = "+id +";";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.executeUpdate();
            System.out.println("Association supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Association> getAll(){
        List<Association> associations = new ArrayList<>();

        String req = "SELECT * FROM association";

        try {
            Statement pst = connection.createStatement();
            ResultSet res = pst.executeQuery(req);
            while (res.next()) {
                Association association = new Association();

                association.setId(res.getInt("id"));
                association.setName(res.getString("name"));
                association.setPhone(res.getString("phone"));
                association.setEmail(res.getString("email"));
                association.setCity(res.getString("city"));
                association.setState(res.getString("state"));
                association.setDescription(res.getString("description"));
                association.setId_user(res.getInt("id_user"));
                association.setFacebook_adresse(res.getString("facebook_adresse"));


                associations.add(association);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return associations;
    }

    public Association getById(int id){
        Association result = null;

        String req = "SELECT * FROM association WHERE id = ?";

        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet res = pst.executeQuery();

            if (res.next()) {
                result = new Association();
                result.setId(res.getInt("id"));
                result.setName(res.getString("name"));
                result.setPhone(res.getString("phone"));
                result.setEmail(res.getString("email"));
                result.setCity(res.getString("city"));
                result.setState(res.getString("state"));
                result.setDescription(res.getString("description"));
                result.setId_user(res.getInt("id_user"));
                result.setStatut(res.getInt("statut"));
                result.setFacebook_adresse(res.getString("facebook_adresse"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }


    public void update(Association assoc) {
        String req = "UPDATE association SET name=?, phone=?, email=?, city=?, state=?, description=?, id_user=? WHERE id = "+assoc.getId() +";";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, assoc.getName());
            ps.setString(2, assoc.getPhone());
            ps.setString(3, assoc.getEmail());
            ps.setString(4, assoc.getCity());
            ps.setString(5, assoc.getState());
            ps.setString(6, assoc.getDescription());
            ps.setInt(7, assoc.getId_user());



            ps.executeUpdate();
            System.out.println("Association modifiée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de l'association : " + e.getMessage());
        }
    }

    public List<Association> getDisabledAssociations(Integer stat){
        List<Association> associations = new ArrayList<>();

        String req = "SELECT * FROM association where statut="+stat;

        try {
            Statement pst = connection.createStatement();
            ResultSet res = pst.executeQuery(req);
            while (res.next()) {
                Association association = new Association();

                association.setId(res.getInt("id"));
                association.setName(res.getString("name"));
                association.setPhone(res.getString("phone"));
                association.setEmail(res.getString("email"));
                association.setCity(res.getString("city"));
                association.setState(res.getString("state"));
                association.setDescription(res.getString("description"));
                association.setId_user(res.getInt("id_user"));
                association.setFacebook_adresse(res.getString("facebook_adresse"));


                associations.add(association);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return associations;
    }


    public void ActivateAssociation(Association assoc){
        String req = "UPDATE association SET statut=1 WHERE id = "+assoc.getId() +";";
        try {
            PreparedStatement ps = connection.prepareStatement(req);




            ps.executeUpdate();
            System.out.println("Association activated !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'activation de l'association : " + e.getMessage());
        }

    }

    public List<Association> getAssociationByIdUser(int id){
        List<Association> associations = new ArrayList<>();

        String req = "SELECT * FROM association WHERE id_user = " + id +";";

        try {
            Statement pst = connection.createStatement();
            ResultSet res = pst.executeQuery(req);
            while (res.next()) {
                Association association = new Association();

                association.setId(res.getInt("id"));
                association.setName(res.getString("name"));
                association.setPhone(res.getString("phone"));
                association.setEmail(res.getString("email"));
                association.setCity(res.getString("city"));
                association.setState(res.getString("state"));
                association.setDescription(res.getString("description"));
                association.setId_user(res.getInt("id_user"));
                association.setFacebook_adresse(res.getString("facebook_adresse"));


                associations.add(association);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return associations;
    }
}