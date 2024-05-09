package com.esprit.models;

public class Wallet {
    private int id;
    private int rate;
    private String lastconnection;
    private int nbrconnection;
    private int idofuser_id;

    public Wallet(){};
    public Wallet(int id, int rate, String lastconnection, int nbrconnection,int idofuser_id) {
        this.id = id;
        this.rate = rate;
        this.nbrconnection = nbrconnection;
        this.idofuser_id= idofuser_id;
        this.lastconnection=lastconnection;
    }

    public Wallet(int rate,String lastconnection,int nbrconnection, int idofuser_id) {
        this.rate = rate;
        this.nbrconnection = nbrconnection;
        this.idofuser_id= idofuser_id;
        this.lastconnection=lastconnection;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", rate=" + rate +
                ", lastconnection=" + lastconnection +
                ", nbrconnection=" + nbrconnection +
                ", idofuser_id=" + idofuser_id +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setLastconnection(String lastconnection) {
        this.lastconnection = lastconnection;
    }

    public void setNbrconnection(int nbrconnection) {
        this.nbrconnection = nbrconnection;
    }

    public void setIdofuser_id(int idofuser_id) {
        this.idofuser_id = idofuser_id;
    }

    public int getId() {
        return id;
    }

    public int getRate() {
        return rate;
    }

    public String getLastconnection() {
        return lastconnection;
    }

    public int getNbrconnection() {
        return nbrconnection;
    }

    public int getIdofuser_id() {
        return idofuser_id;
    }
}
