package com.esprit.services;

import com.esprit.models.Action;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActionService implements IAssociationAction<Action> {

    private Connection connection;

    public ActionService() {
        connection = DataSource.getInstance().getConnection();
    }


    public void add(Action action) {

        System.out.println(action.toString());
        String req = "INSERT INTO action(name_action, description_action, date_action, location, id_association, image, organized_for,target_amount, category) VALUES ('" + action.getName_action() + "', '" + action.getDescription_action() + "', '" + action.getDate_action() + "', '" + action.getLocation() + "', '" + action.getId_association() + "', '" + action.getImage() + "', '" + action.getOrganized_for() + "', '" + action.getTarget_amount() + "', '" +action.getCategory()  +"');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Action ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(int id) {

        String req = "DELETE from action where id = "+id +";";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.executeUpdate();
            System.out.println("Action supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Action getById(int id) {

        Action a = new Action();
        return a;
    }

    public List<Action> getAll(){

        List<Action> actions = new ArrayList<>();

        String req = "SELECT * FROM action";

        try {
            Statement pst = connection.createStatement();
            ResultSet res = pst.executeQuery(req);
            while (res.next()) {
                Action action =new Action();

                action.setId(res.getInt("id"));
                action.setName_action(res.getString("name_action"));
                action.setDescription_action(res.getString("description_action"));
                action.setDate_action(res.getDate("date_action"));
                action.setLocation(res.getString("location"));
                action.setId_association(res.getInt("id_association"));
                action.setImage(res.getString("image"));
                action.setOrganized_for(res.getString("organized_for"));
                action.setTarget_amount(res.getInt("target_amount"));
                action.setCategory(res.getString("category"));

                actions.add(action);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return actions;
    }

    public void update(Action action) {

        String req = "UPDATE action SET name_action=?, description_action=? ,location=?,id_association=?, organized_for=?, target_amount=? , category=? , date_action=? WHERE id = "+action.getId() +";";
        try {

            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, action.getName_action());
            ps.setString(2,action.getDescription_action());
            ps.setString(3, action.getLocation());
            ps.setInt(4, action.getId_association());
            ps.setString(5, action.getOrganized_for());
            ps.setInt(6, action.getTarget_amount());
            ps.setString(7, action.getCategory());
            java.util.Date utilDate = action.getDate_action();
            Date sqlDate = new Date(utilDate.getTime());
            ps.setDate(8, sqlDate);


            ps.executeUpdate();
            System.out.println("Association modifiée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de l'association : " + e.getMessage());
        }
    }


    public List<Action> getActionByIdAssociation(int id) {

        List<Action> actions = new ArrayList<>();

        String req = "SELECT * FROM action WHERE id_association ="+id+";";

        try {
            Statement pst = connection.createStatement();
            ResultSet res = pst.executeQuery(req);
            while (res.next()) {
                Action action =new Action();

                action.setId(res.getInt("id"));
                action.setName_action(res.getString("name_action"));
                action.setDescription_action(res.getString("description_action"));
                action.setDate_action(res.getDate("date_action"));
                action.setLocation(res.getString("location"));
                action.setId_association(res.getInt("id_association"));
                action.setImage(res.getString("image"));
                action.setOrganized_for(res.getString("organized_for"));
                action.setTarget_amount(res.getInt("target_amount"));
                action.setCategory(res.getString("category"));

                actions.add(action);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return actions;
    }
}
