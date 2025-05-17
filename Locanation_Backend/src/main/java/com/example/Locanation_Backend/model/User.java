package com.example.Locanation_Backend.model;

public class User {
    private int id;
    private String login;
    private String mot_de_passe;
    private Role role;
    private String nom;
    private String email;
    private String telephone;
    private String date_creation;

    // Constructors
    public User() {}

    public User(int id, String login, String mot_de_passe, Role role,
                String nom, String email, String telephone, String date_creation) {
        this.id = id;
        this.login = login;
        this.mot_de_passe = mot_de_passe;
        this.role = role;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.date_creation = date_creation;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(String date_creation) {
        this.date_creation = date_creation;
    }
}