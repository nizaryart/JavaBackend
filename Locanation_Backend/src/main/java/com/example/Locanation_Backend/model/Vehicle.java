package com.example.Locanation_Backend.model;

public class Vehicle {
    private int id;
    private String marque;
    private String modele;
    private String matricule;
    private double prix_journalier;
    private double prix_par_km;
    private String couleur;
    private String carburant;
    private String boite_vitesse;
    private boolean disponible;
    private int annee;

    // Constructors
    public Vehicle() {}

    public Vehicle(int id, String marque, String modele, String matricule,
                   double prix_journalier, double prix_par_km, String couleur,
                   String carburant, String boite_vitesse, boolean disponible,
                   int annee) {
        this.id = id;
        this.marque = marque;
        this.modele = modele;
        this.matricule = matricule;
        this.prix_journalier = prix_journalier;
        this.prix_par_km = prix_par_km;
        this.couleur = couleur;
        this.carburant = carburant;
        this.boite_vitesse = boite_vitesse;
        this.disponible = disponible;
        this.annee = annee;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public double getPrix_journalier() {
        return prix_journalier;
    }

    public void setPrix_journalier(double prix_journalier) {
        this.prix_journalier = prix_journalier;
    }

    public double getPrix_par_km() {
        return prix_par_km;
    }

    public void setPrix_par_km(double prix_par_km) {
        this.prix_par_km = prix_par_km;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getCarburant() {
        return carburant;
    }

    public void setCarburant(String carburant) {
        this.carburant = carburant;
    }

    public String getBoite_vitesse() {
        return boite_vitesse;
    }

    public void setBoite_vitesse(String boite_vitesse) {
        this.boite_vitesse = boite_vitesse;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }
}