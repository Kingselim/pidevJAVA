package com.esprit.models;

public class account {
    private int id;
    private double solde;
    private String type_account;
    private String date_creation;
    private int state;
    private int id_association_id;

    public account()
    {
        //constructeur par defaut
    }
    public account(int id,double solde,String type_account,String date_creation,int state,int id_association_id)
    {
        this.id = id;
        this.solde = solde;
        this.type_account = type_account;
        this.date_creation = date_creation;
        this.state = state;
        this.id_association_id = id_association_id;
    }
    public account(double solde,String type_account,String date_creation,int state,int id_association_id)
    {
        this.solde = solde;
        this.type_account = type_account;
        this.date_creation = date_creation;
        this.state = state;
        this.id_association_id = id_association_id;
    }


    public int getId() {
        return id;
    }

    public double getSolde() {
        return solde;
    }

    public String getType_account() {
        return type_account;
    }

    public String getDate_creation() {
        return date_creation;
    }

    public int getState() {
        return state;
    }

    public int getId_association_id() {
        return id_association_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public void setType_account(String type_account) {
        this.type_account = type_account;
    }

    public void setDate_creation(String date_creation) {
        this.date_creation = date_creation;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setId_association_id(int id_association_id) {
        this.id_association_id = id_association_id;
    }

    @Override
    public String toString() {
        return "account{" +
                "id=" + id +
                ", solde=" + solde +
                ", type_account='" + type_account + '\'' +
                ", date_creation='" + date_creation + '\'' +
                ", state=" + state +
                ", id_association_id=" + id_association_id +
                '}';
    }
}
