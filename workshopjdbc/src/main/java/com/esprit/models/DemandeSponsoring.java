package com.esprit.models;

import java.sql.Date;

public class DemandeSponsoring {

    private int id;
    private double budget;
    private Date datedebut;
    private Date datefin;
    private int id_sponsoring;

    private Sponsoring sponsoring;

    private int id_user;
    private String nomassociation;

    private String autretype;

    public DemandeSponsoring(int id, double budget, Date datedebut, Date datefin, int id_sponsoring, int id_user, String nomassociation,String autre) {
        this.id = id;
        this.budget = budget;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.id_sponsoring = id_sponsoring;
        this.id_user = id_user;
        this.nomassociation = nomassociation;
        this.autretype = autre;
    }

    public DemandeSponsoring() {
    }

    public DemandeSponsoring(int id, double budget, Date datedebut, Date datefin, int id_sponsoring, int id_user, String nomassociation) {
        this.id = id;
        this.budget = budget;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.id_sponsoring = id_sponsoring;
        this.id_user = id_user;
        this.nomassociation = nomassociation;
    }
    public DemandeSponsoring( double budget, Date datedebut, Date datefin, int id_sponsoring, int id_user, String nomassociation,String autre) {
        this.budget = budget;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.id_sponsoring = id_sponsoring;
        this.id_user = id_user;
        this.nomassociation = nomassociation;
        this.autretype = autre;
    }

    public DemandeSponsoring(double budget, Date datedebut, Date datefin, int id_sponsoring, int id_user, String nomassociation) {

        this.budget = budget;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.id_sponsoring = id_sponsoring;
        this.id_user = id_user;
        this.nomassociation = nomassociation;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public int getId_sponsoring() {
        return id_sponsoring;
    }

    public void setId_sponsoring(int id_sponsoring) {
        this.id_sponsoring = id_sponsoring;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public Date getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(Date datedebut) {
        this.datedebut = datedebut;
    }

    public Date getDatefin() {
        return datefin;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    public String getNomassociation() {
        return nomassociation;
    }

    public void setNomassociation(String nomassociation) {
        this.nomassociation = nomassociation;
    }

    public Sponsoring getSponsoring() {
        return sponsoring;
    }

    public void setSponsoring(Sponsoring sponsoring) {
        this.sponsoring = sponsoring;
    }

    public String getAutretype() {
        return autretype;
    }

    public void setAutretype(String autretype) {
        this.autretype = autretype;
    }

    @Override
    public String toString() {
        return "DemandeSponsoring{" +
                "id=" + id +
                ", budget=" + budget +
                ", id_sponsoring=" + id_sponsoring +
                ", id_user=" + id_user +
                ", datedebut=" + datedebut +
                ", datefin=" + datefin +
                ", nomassociation='" + nomassociation + '\'' +
                '}';
    }
}
