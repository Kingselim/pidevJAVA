package com.esprit.models;

public class Souvenir {
    private String email;
    private String password;
    private String date;
    private int idsouvenir;
    public int getId() {
        return idsouvenir;
    }

    public void setId(int id) {
        this.idsouvenir = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public Souvenir(){}

    public Souvenir(String email, String password, String date) {
        this.email = email;
        this.password = password;
        this.date = date;
    }
}
