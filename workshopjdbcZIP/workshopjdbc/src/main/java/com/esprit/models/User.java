package com.esprit.models;

public class User {

    private int id;
    private String name;
    private String lastname;
    private String dtb;
    private String phone;
    private String email;
    private String address;
    private String password;
    private String state;
    private String role;
    private String image;
    private String sexe;

    public User(){

    }
    public User(int id,String name, String lastname, String dtb, String phone, String email, String address, String password, String state, String role, String image, String sexe) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.dtb = dtb;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.password = password;
        this.state = state;
        this.role = role;
        this.image = image;
        this.sexe = sexe;
    }
    public User(String name, String lastname, String dtb, String phone, String email, String address, String password, String state, String role, String image, String sexe) {
        this.name = name;
        this.lastname = lastname;
        this.dtb = dtb;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.password = password;
        this.state = state;
        this.role = role;
        this.image = image;
        this.sexe = sexe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDtb() {
        return dtb;
    }

    public void setDtb(String dtb) {
        this.dtb = dtb;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dtb='" + dtb + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", state='" + state + '\'' +
                ", role=" + role +
                ", image='" + image + '\'' +
                ", sexe='" + sexe + '\'' +
                "}\n";
    }



}
