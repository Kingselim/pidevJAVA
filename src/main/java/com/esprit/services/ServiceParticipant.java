package com.esprit.services;
import com.esprit.models.Seminaire;
import com.esprit.utils.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.esprit.models.Participant;

public class ServiceParticipant implements IService<Participant> {
    private Connection cnx;
    public ServiceParticipant(){
        cnx= DataSource.getInstance().getConnection();
    }


    @Override
    public void ajouter(Participant participant) {
        String query="INSERT INTO participant(idseminar_id, name, last_name, function, phone) VALUES (?,?,?,?,?);";
        try{
            PreparedStatement pst=cnx.prepareStatement(query);
            pst.setInt(1, participant.getIdseminar_id());
            pst.setString(2,participant.getName());
            pst.setString(3,participant.getLast_name());
            pst.setString(4,participant.getFunction());
            pst.setInt(5,participant.getPhone());

            pst.executeUpdate();
            System.out.println("Participant ajoute");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }




    }

    @Override
    public void modifier(Participant participant) {
        String query="UPDATE participant SET id=?,idseminar_id=?,name=?,last_name=?,function=?,phone=? WHERE id=?;";
        try{
            PreparedStatement pst=cnx.prepareStatement(query);
            pst.setInt(1,participant.getId());
            pst.setInt(2,participant.getIdseminar_id());
            pst.setString(3,participant.getName());
            pst.setString(4,participant.getLast_name());
            pst.setString(5,participant.getFunction());
            pst.setInt(6,participant.getPhone());
            pst.setInt(7,participant.getId());
            pst.executeUpdate();
            System.out.println("Participant modifie");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Participant participant) {
        String query="DELETE FROM participant WHERE id = ?";
        try{
            PreparedStatement pst=cnx.prepareStatement(query);

            pst.setInt(1,participant.getId());
            pst.executeUpdate();
            System.out.println("Participant supprime");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Participant> afficher() {
        List<Participant> lu=new ArrayList<>();
        try{
            String query="SELECT * FROM participant";
            Statement st=cnx.createStatement();
            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
                Participant participant=new Participant();
                participant.setId(rs.getInt("id"));
                participant.setIdseminar_id(rs.getInt("idseminar_id"));
                participant.setName(rs.getString("name"));
                participant.setLast_name(rs.getString("last_name"));
                participant.setFunction(rs.getString("function"));
                participant.setPhone(rs.getInt("phone"));
                lu.add(participant);


            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return lu;
    }

    public List<Participant> getParticipantsForSeminaire(int idSeminaire) {
        List<Participant> participants = new ArrayList<>();
        try {
            String query = "SELECT participant.* \n" +
                    "FROM participant\n" +
                    "INNER JOIN seminar ON participant.idseminar_id = seminar.id\n" +
                    "WHERE seminar.id = ?;";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, idSeminaire);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Participant participant = new Participant();
                participant.setId(rs.getInt("id"));
                participant.setIdseminar_id(rs.getInt("idseminar_id"));
                participant.setName(rs.getString("name"));
                participant.setLast_name(rs.getString("last_name"));
                participant.setFunction(rs.getString("function"));
                participant.setPhone(rs.getInt("phone"));
                participants.add(participant);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return participants;
    }




}

