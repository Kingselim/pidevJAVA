package com.esprit.models;

public class transaction {
    private int id ;
    private double montant;
    private String account_debited;
    private String account_destination;
    private String date_transaction;
    private String type_transaction;
    private String description;
    private int id_account_id;

    public transaction(){}
    public transaction(int id, double montant, String account_debited, String account_destination, String date_transaction, String type_transaction, String description, int id_account_id) {
        this.id = id;
        this.montant = montant;
        this.account_debited = account_debited;
        this.account_destination = account_destination;
        this.date_transaction = date_transaction;
        this.type_transaction = type_transaction;
        this.description = description;
        this.id_account_id = id_account_id;
    }
    public transaction( double montant, String account_debited, String account_destination, String date_transaction, String type_transaction, String description, int id_account_id)
    {
        this.montant = montant;
        this.account_debited = account_debited;
        this.account_destination = account_destination;
        this.date_transaction = date_transaction;
        this.type_transaction = type_transaction;
        this.description = description;
        this.id_account_id = id_account_id;
    }

//getters
    public int getId() {
        return id;
    }

    public double getmontant() {
        return montant;
    }

    public String getaccount_debited() {
        return account_debited;
    }

    public String getaccount_destination() {
        return account_destination;
    }

    public String getdate_transaction() {
        return date_transaction;
    }

    public String gettype_transaction() {
        return type_transaction;
    }

    public String getdescription() {
        return description;
    }

    public int getid_account_id() {
        return id_account_id;
    }
//setters
    public void setId(int id) {
        this.id = id;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setAccount_debited(String account_debited) {
        this.account_debited = account_debited;
    }

    public void setAccount_destination(String account_destination) {
        this.account_destination = account_destination;
    }

    public void setDate_transaction(String date_transaction) {
        this.date_transaction = date_transaction;
    }

    public void setType_transaction(String type_transaction) {
        this.type_transaction = type_transaction;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId_account_id(int id_account_id) {
        this.id_account_id = id_account_id;
    }

    @Override
    public String toString() {
        return "transaction{" +
                "id=" + id +
                ", montant=" + montant +
                ", account_debited='" + account_debited + '\'' +
                ", account_destination='" + account_destination + '\'' +
                ", date_transaction='" + date_transaction + '\'' +
                ", type_transaction='" + type_transaction + '\'' +
                ", description='" + description + '\'' +
                ", id_account_id=" + id_account_id +
                '}';
    }

}
