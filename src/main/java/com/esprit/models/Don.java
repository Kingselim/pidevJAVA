package com.esprit.models;

import java.util.Date;

public class Don {

    private Integer id;
    private Integer montant;
    private Integer id_action;
    private Date date_donation;
    private String donateur;
    private String email;


    public Don() {

    }
    public Don(Integer id_action, Integer montant, Date date_donation, String donateur, String email) {

        this.id_action=id_action;
        this.montant = montant;
        this.date_donation = date_donation;
        this.donateur = donateur;
        this.email = email;
    }

    public String getDonateur() {
        return donateur;
    }

    public void setDonateur(String donateur) {
        this.donateur = donateur;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_action() {
        return id_action;
    }

    public void setId_action(Integer id_action) {
        this.id_action = id_action;
    }



    public Integer getMontant() {
        return montant;
    }

    public void setMontant(Integer montant) {
        this.montant = montant;
    }

    public Date getDate_donation() {
        return date_donation;
    }

    public void setDate_donation(Date date_donation) {
        this.date_donation = date_donation;
    }

    @Override
    public String toString() {
        return "Don{" +
                "id=" + id +
                ", montant=" + montant +
                ", date_donation=" + date_donation +
                ", id_action=" + id_action +
                '}';
    }

}
