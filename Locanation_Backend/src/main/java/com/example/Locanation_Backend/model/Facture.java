package com.example.Locanation_Backend.model;

import java.time.LocalDate;

public class Facture {
    private int id;
    private int contratId;
    private String numeroFacture;
    private LocalDate dateFacture;
    private double montant;
    private double tva; // Added field
    private String statut;  // "PAID", "UNPAID", "PARTIAL"
    private String modePaiement;  // "CASH", "CARD", "TRANSFER"
    private LocalDate datePaiement;
    private String notes;

    // Constructors
    public Facture() {}

    public Facture(int id, int contratId, String numeroFacture, LocalDate dateFacture,
                   double montant, double tva, String statut, String modePaiement,
                   LocalDate datePaiement, String notes) {
        this.id = id;
        this.contratId = contratId;
        this.numeroFacture = numeroFacture;
        this.dateFacture = dateFacture;
        this.montant = montant;
        this.tva = tva;
        this.statut = statut;
        this.modePaiement = modePaiement;
        this.datePaiement = datePaiement;
        this.notes = notes;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContratId() {
        return contratId;
    }

    public void setContratId(int contratId) {
        this.contratId = contratId;
    }

    public String getNumeroFacture() {
        return numeroFacture;
    }

    public void setNumeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
    }

    public LocalDate getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(LocalDate dateFacture) {
        this.dateFacture = dateFacture;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public double getTva() {
        return tva;
    }

    public void setTva(double tva) {
        this.tva = tva;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Facture{" +
                "id=" + id +
                ", contratId=" + contratId +
                ", numeroFacture='" + numeroFacture + '\'' +
                ", dateFacture=" + dateFacture +
                ", montant=" + montant +
                ", tva=" + tva +
                ", statut='" + statut + '\'' +
                ", modePaiement='" + modePaiement + '\'' +
                ", datePaiement=" + datePaiement +
                ", notes='" + notes + '\'' +
                '}';
    }
}