package com.esprit.models;

public class Association {

    public int id;
    public String name;
    public String phone;
    public String email;
    public String city;
    public String state;
    public String description;
    public int statut;
    public int id_user;
    public String  facebook_adresse;

    public Association(){

    }

    public Association( String name, String phone, String email, String city, String state, String description, int statut, int id_user, String facebook_adresse) {
        //this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.city = city;
        this.state = state;
        this.description = description;
        this.statut = statut;
        this.id_user = id_user;
        this.facebook_adresse = facebook_adresse;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getDescription() {
        return description;
    }

    public int getStatut() {
        return statut;
    }

    public int getId_user() {
        return id_user;
    }

    public String getFacebook_adresse() {
        return facebook_adresse;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setFacebook_adresse(String facebook_adresse) {
        this.facebook_adresse = facebook_adresse;
    }

    @Override
    public String toString() {
        return "Association{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", description='" + description + '\'' +
                ", statut=" + statut +
                ", id_user=" + id_user +
                ", facebook_adresse='" + facebook_adresse + '\'' +
                '}';
    }

}
