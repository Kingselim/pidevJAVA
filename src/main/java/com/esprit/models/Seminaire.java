package com.esprit.models;

public class Seminaire {
    private int id;
    private int iduserasso_id;
    private String date_seminar;
    private String description;
    private String nomassociation;


    public Seminaire(){}
    public Seminaire(int id, int iduserasso_id, String date_seminar, String description, String nomassociation) {
        this.id = id;
        this.iduserasso_id = iduserasso_id;
        this.date_seminar = date_seminar;
        this.description = description;
        this.nomassociation = nomassociation;
    }

    public Seminaire(int iduserasso_id, String date_seminar, String description, String nomassociation) {
        this.iduserasso_id = iduserasso_id;
        this.date_seminar = date_seminar;
        this.description = description;
        this.nomassociation = nomassociation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIduserasso_id() {
        return iduserasso_id;
    }

    public void setIduserasso_id(int iduserasso_id) {
        this.iduserasso_id = iduserasso_id;
    }

    public String getDate_seminar() {
        return date_seminar;
    }

    public void setDate_seminar(String date_seminar) {
        this.date_seminar = date_seminar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNomassociation() {
        return nomassociation;
    }

    public void setNomassociation(String nomassociation) {
        this.nomassociation = nomassociation;
    }

    @Override
    public String toString() {
        return "Seminaire{" +
                "id=" + id +
                ", iduserasso_id=" + iduserasso_id +
                ", date_seminar='" + date_seminar + '\'' +
                ", description='" + description + '\'' +
                ", nomassociation='" + nomassociation + '\'' +
                '}';
    }
}
