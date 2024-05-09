package com.esprit.services;

import com.esprit.models.User;
import com.esprit.utils.DataSource;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import okhttp3.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserService implements IService<User> {


    private Connection connection;

    public UserService() {
        connection = DataSource.getInstance().getConnection();
    }

    public void ajouter(User user) {
        String req = "INSERT INTO user(name,lastname,dtb,phone,email,address,password,state,role,image,sexe) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(2, user.getLastname());
            pst.setString(1, user.getName());
            pst.setString(3, user.getDtb());
            pst.setString(4, user.getPhone());
            pst.setString(5, user.getEmail());
            pst.setString(6, user.getAddress());
            pst.setString(7, user.getPassword());
            pst.setString(8, user.getState());
            pst.setString(9, user.getRole());
            pst.setString(10, user.getImage());
            pst.setString(11, user.getSexe());
            pst.executeUpdate();
            System.out.println("Utilisateur ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modifier(User user)
    {
        String req = "UPDATE user SET name=?,lastname=?,dtb=?,phone=?,email=?,address=?,password=?,state=?,role=?,image=?,sexe=? WHERE id=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, user.getName());
            pst.setString(2, user.getLastname());
            pst.setString(3, user.getDtb());
            pst.setString(4, user.getPhone());
            pst.setString(5, user.getEmail());
            pst.setString(6, user.getAddress());
            pst.setString(7, user.getPassword());
            pst.setString(8, user.getState());
            pst.setString(9, user.getRole());
            pst.setString(10, user.getImage());
            pst.setString(11, user.getSexe());
            pst.setInt(12, user.getId());
            pst.executeUpdate();
            System.out.println("Utilisateur Modifier !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void supprimer(User user)
    {
        Alert alertShowInfo = new Alert(Alert.AlertType.CONFIRMATION);
        alertShowInfo.setContentText("Voulez vous supprimer l'utilisateur");
        //alertShowInfo.showAndWait();
        Optional<ButtonType> result = alertShowInfo.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String req = "DELETE from user where id = ?;";
            try {
                PreparedStatement pst = connection.prepareStatement(req);
                pst.setInt(1, user.getId());
                pst.executeUpdate();
                System.out.println("Utilisateur supprmiée !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }
    public List<User> afficher()
    {
        List<User> user = new ArrayList<>();


        String req = "SELECT * from user WHERE state=1";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                user.add(new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("dtb"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("password"),
                        rs.getString("state"),
                        rs.getString("role"),
                        rs.getString("image"),
                        rs.getString("sexe")
                        ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public List<User> afficherUserBanni()
    {
        List<User> user = new ArrayList<>();


        String req = "SELECT * from user WHERE state=0";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                user.add(new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("dtb"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("password"),
                        rs.getString("state"),
                        rs.getString("role"),
                        rs.getString("image"),
                        rs.getString("sexe")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }


public void envoyermail(User u,String authcode)
{
    OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
    MediaType mediaType = MediaType.parse("application/json");
    //RequestBody body = RequestBody.create(mediaType, "{\"from\":{\"email\":\"mailtrap@demomailtrap.com\",\"name\":\"Mot de passe Oublié\"},\"to\":[{\"email\":\"selim03gaaloul@gmail.com\"}],\"subject\":\"Reset Password!\",\"text\":\"Afin de Rénitialiser le mot de passe! : voici le code a saisir :"+authcode+" \",\"category\":\"Integration Test\"}");
    String htmlTemplate = "<html><head><style> .header { background-color: #007bff; color: white; padding: 10px; } .footer { background-color: #007bff; color: white; padding: 10px; } </style></head><body><div class='header'>Header Content</div>" +
            "<div >Bonjour Mr [nom], Afin de réinitialiser le mot de passe, veuillez saisir le code suivant : <strong>[CODE]</strong>.</div>" +
            "<div class='footer'>Footer Content</div></body></html>";
    htmlTemplate = htmlTemplate.replace("[CODE]", authcode);
    htmlTemplate = htmlTemplate.replace("[nom]", u.getName());
    RequestBody body = RequestBody.create(mediaType, "{\"from\":{\"email\":\"mailtrap@demomailtrap.com\",\"name\":\"Mot de passe Oublié\"},\"to\":[{\"email\":\"selim03gaaloul@gmail.com\"}],\"subject\":\"Reset Password!\", \"html\": \"" + htmlTemplate + "\",\"category\":\"Integration Test\"}");


    Request request = new Request.Builder()
            .url("https://send.api.mailtrap.io/api/send")
            .method("POST", body)
            .addHeader("Authorization", "Bearer e0970a027a348eb6c62fbf88336ac050")
            .addHeader("Content-Type", "application/json")
            .build();

    try {
        Response response = client.newCall(request).execute();
        System.out.println("mailenvoyer");
    } catch (IOException e) {
        //throw new RuntimeException(e);
        System.out.println(e.getMessage());
    }
}


    public boolean userexist(String emailverif)
    {
        String email="";
        String req = "SELECT email FROM User WHERE email=?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, emailverif);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                email=rs.getString("email");
            }
            if(email.isEmpty())
            {
                System.out.println("Utilisateur n'existe pas ou mail est vide!");
                return false;
            }else
            {
                System.out.println("Utilisateur existe !");
                return true;
            }

        }catch (SQLException e) {

            System.out.println(e.getMessage());

        }
        return false;
    }

    public User recupererUserAvecId(int idv)
    {
        String req = "SELECT * FROM User WHERE id=?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, idv);
            ResultSet rs = pst.executeQuery();
            User user = new User();
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setLastname(rs.getString("lastname"));
                user.setAddress(rs.getString("address"));
                user.setDtb(rs.getString("dtb"));
                user.setEmail(rs.getString("email"));
                user.setPassword( rs.getString("password"));
                user.setImage(rs.getString("image"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                user.setState(rs.getString("state"));
                user.setSexe(rs.getString("sexe"));
            }
            System.out.println("lutilisateur est remplir !");
            return user;
        }catch (SQLException e) {

            System.out.println(e.getMessage());
        }
        return null;
    }

    public int seconnecter(String emailverif,String passwordverif) {
        // boolean exist = userexist(emailverif); // verifie que le user existe dans la base de donnéé
        User user = new User();
        user = recupererUser(emailverif);
        String pass = "";
        String namecheck = null;
        namecheck = user.getName();


        if (!Objects.equals(namecheck, null)) {
            System.out.println("objet utilisateur n'est pas null 'namecheck'");
            String passduUser=user.getPassword();
            String hashed2 = "$2a" + passduUser.substring(3);
            if (BCrypt.checkpw(passwordverif,hashed2)) {
                System.out.println("le mot de passe est correct !");
                if (user.getState().equals("0")) {
                    System.out.println("le compte est bloque !");
                    return 4; // retourne 4 si le compte est bloque
                }else
                {
                    if (user.getRole().equals("1")) {
                        return 2; // retour 2 si c'est un ADMIN
                    }
                    if (user.getRole().equals("0")) {
                        return 3; //retour 3 si c'est un client
                    }
                }

            }else{
                System.out.println("le mot de passe incorrect !");

                return 1; //retour 3 si le mdps est incorrect
            }
        }
        return 0; // retourne 0 si le user n'existe pas

    }

    public User recupererUser(String emailverif)
    {
        String req = "SELECT * FROM User WHERE email=?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, emailverif);
            ResultSet rs = pst.executeQuery();
            User user = new User();
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setLastname(rs.getString("lastname"));
                user.setAddress(rs.getString("address"));
                user.setDtb(rs.getString("dtb"));
                user.setEmail(rs.getString("email"));
                user.setPassword( rs.getString("password"));
                user.setImage(rs.getString("image"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                user.setState(rs.getString("state"));
                user.setSexe(rs.getString("sexe"));
            }
            System.out.println("l'utilisateur est remplir !");
            return user;
        }catch (SQLException e) {

             System.out.println(e.getMessage());
        }
        return null;
    }



    public List<User> recupererUseFemme() {
        List<User> user = new ArrayList<>();

        String req = "SELECT * from user WHERE sexe=0";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                user.add(new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("dtb"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("password"),
                        rs.getString("state"),
                        rs.getString("role"),
                        rs.getString("image"),
                        rs.getString("sexe")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }


    public List<User> recupererUseHomme() {
        List<User> user = new ArrayList<>();

        String req = "SELECT * from user WHERE sexe=1";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                user.add(new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("dtb"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("password"),
                        rs.getString("state"),
                        rs.getString("role"),
                        rs.getString("image"),
                        rs.getString("sexe")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }
}
