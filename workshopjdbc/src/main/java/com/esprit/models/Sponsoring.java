package com.esprit.models;

import java.sql.Date;

public class Sponsoring {

    //att
    private int id;
    private Double  budget ;

    private Date datesponsoring ;
    private String type;

    //constructors

    public Sponsoring() {
    }

    public Sponsoring(Double budget, Date datesponsoring, String type) {
        this.budget = budget;
        this.datesponsoring = datesponsoring;
        this.type = type;
    }

    public Sponsoring(int id, Double budget, Date datesponsoring, String type) {
        this.id = id;
        this.budget = budget;
        this.datesponsoring = datesponsoring;
        this.type = type;
    }

    //getters  and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Date getDatesponsoring() {
        return datesponsoring;
    }

    public void setDatesponsoring(Date datesponsoring) {
        this.datesponsoring = datesponsoring;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //display


    @Override
    public String toString() {
        return "Sponsoring{" +
                "id=" + id +
                ", budget=" + budget +
                ", datesponsoring=" + datesponsoring +
                ", type='" + type + '\'' +
                '}';
    }
}
