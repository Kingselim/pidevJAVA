package com.esprit.models;

public class Participant {
    private int id;
    private int idseminar_id;
    private String name;
    private String last_name;
    private String function;
    private int phone;

    public Participant(){}

    public Participant(int id, int idseminar_id, String name, String last_name, String function, int phone) {
        this.id = id;
        this.idseminar_id = idseminar_id;
        this.name = name;
        this.last_name = last_name;
        this.function = function;
        this.phone = phone;
    }

    public Participant(int idseminar_id, String name, String last_name, String function, int phone) {
        this.idseminar_id = idseminar_id;
        this.name = name;
        this.last_name = last_name;
        this.function = function;
        this.phone = phone;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdseminar_id() {
        return idseminar_id;
    }

    public void setIdseminar_id(int idseminar_id) {
        this.idseminar_id = idseminar_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public int getPhone() {
        return phone;
    }


    public void setPhone(int phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", idseminar_id=" + idseminar_id +
                ", name='" + name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", function='" + function + '\'' +
                ", phone=" + phone +
                '}';
    }

}
