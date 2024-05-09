package com.esprit.models;

import java.util.Date;

public class Action {

    public int id;
    public String name_action;
    public String description_action;
    public Date date_action;
    public String location;
    public int id_association;
    public String image;
    public String organized_for;
    public int target_amount;
    public String category;

    public Action(){

    }

    public Action(String name_action, String description_action, Date date_action, String location, int id_association, String image, String organized_for, int target_amount, String category) {
        //this.id = id;
        this.name_action = name_action;
        this.description_action = description_action;
        this.date_action = date_action;
        this.location = location;
        this.id_association = id_association;
        this.image = image;
        this.organized_for = organized_for;
        this.target_amount = target_amount;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Action{" +
                "id=" + id +
                ", name_action='" + name_action + '\'' +
                ", description_action='" + description_action + '\'' +
                ", date_action=" + date_action +
                ", location='" + location + '\'' +
                ", id_association=" + id_association +
                ", image='" + image + '\'' +
                ", organized_for='" + organized_for + '\'' +
                ", target_amount=" + target_amount +
                ", category='" + category + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_action() {
        return name_action;
    }

    public void setName_action(String name_action) {
        this.name_action = name_action;
    }

    public String getDescription_action() {
        return description_action;
    }

    public void setDescription_action(String description_action) {
        this.description_action = description_action;
    }

    public Date getDate_action() {
        return date_action;
    }

    public void setDate_action(Date date_action) {
        this.date_action = date_action;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId_association() {
        return id_association;
    }

    public void setId_association(int id_association) {
        this.id_association = id_association;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOrganized_for() {
        return organized_for;
    }

    public void setOrganized_for(String organized_for) {
        this.organized_for = organized_for;
    }

    public int getTarget_amount() {
        return target_amount;
    }

    public void setTarget_amount(int target_amount) {
        this.target_amount = target_amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
