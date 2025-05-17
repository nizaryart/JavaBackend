package com.example.Locanation_Backend.model;

import java.time.LocalDate;

public class Paiement {
    private int id;
    private int factureId;
    private double montant;
    private LocalDate datePaiement;
    private String modePaiement;  // CASH, CARD, TRANSFER, CHEQUE
    private String reference;
    private String statut;  // PENDING, COMPLETED, FAILED, REFUNDED
    private String notes;

    // Constructors
    public Paiement() {}

    public Paiement(int id, int factureId, double montant, LocalDate datePaiement,
                    String modePaiement, String reference, String statut, String notes) {
        this.id = id;
        this.factureId = factureId;
        this.montant = montant;
        this.datePaiement = datePaiement;
        this.modePaiement = modePaiement;
        this.reference = reference;
        this.statut = statut;
        this.notes = notes;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFactureId() {
        return factureId;
    }

    public void setFactureId(int factureId) {
        this.factureId = factureId;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Paiement{" +
                "id=" + id +
                ", factureId=" + factureId +
                ", montant=" + montant +
                ", datePaiement=" + datePaiement +
                ", modePaiement='" + modePaiement + '\'' +
                ", reference='" + reference + '\'' +
                ", statut='" + statut + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }

    // Enum for payment status (optional)
    public enum Statut {
        PENDING,
        COMPLETED,
        FAILED,
        REFUNDED
    }

    // Enum for payment method (optional)
    public enum ModePaiement {
        CASH,
        CARD,
        TRANSFER,
        CHEQUE
    }
}