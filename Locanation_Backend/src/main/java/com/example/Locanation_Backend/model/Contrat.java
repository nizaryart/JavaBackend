package com.example.Locanation_Backend.model;

import java.time.LocalDate;

public class Contrat {
    private int id;
    private int clientId;
    private int vehiculeId;
    private int utilisateurId;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalDate dateRetour;
    private int kilometrageDepart;
    private int kilometrageRetour;
    private double montantTotal;
    private String statut;
    private LocalDate dateCreation;

    // Constructors
    public Contrat() {}

    public Contrat(int id, int clientId, int vehiculeId, int utilisateurId,
                   LocalDate dateDebut, LocalDate dateFin, LocalDate dateRetour,
                   int kilometrageDepart, int kilometrageRetour, double montantTotal,
                   String statut,  LocalDate dateCreation) {
        this.id = id;
        this.clientId = clientId;
        this.vehiculeId = vehiculeId;
        this.utilisateurId = utilisateurId;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.dateRetour = dateRetour;
        this.kilometrageDepart = kilometrageDepart;
        this.kilometrageRetour = kilometrageRetour;
        this.montantTotal = montantTotal;
        this.statut = statut;
        this.dateCreation = dateCreation;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(int vehiculeId) {
        this.vehiculeId = vehiculeId;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public LocalDate getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

    public int getKilometrageDepart() {
        return kilometrageDepart;
    }

    public void setKilometrageDepart(int kilometrageDepart) {
        this.kilometrageDepart = kilometrageDepart;
    }

    public int getKilometrageRetour() {
        return kilometrageRetour;
    }

    public void setKilometrageRetour(int kilometrageRetour) {
        this.kilometrageRetour = kilometrageRetour;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return "Contrat{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", vehiculeId=" + vehiculeId +
                ", utilisateurId=" + utilisateurId +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", dateRetour=" + dateRetour +
                ", kilometrageDepart=" + kilometrageDepart +
                ", kilometrageRetour=" + kilometrageRetour +
                ", montantTotal=" + montantTotal +
                ", statut='" + statut + '\'' +
                ", dateCreation=" + dateCreation +
                '}';
    }
}