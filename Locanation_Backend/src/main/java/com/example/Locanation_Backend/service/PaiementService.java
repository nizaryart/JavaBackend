package com.example.Locanation_Backend.service;

import com.example.Locanation_Backend.model.Paiement;
import com.example.Locanation_Backend.repository.PaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaiementService {

    private final PaiementRepository paiementRepository;
    private final FactureService factureService;

    @Autowired
    public PaiementService(PaiementRepository paiementRepository, FactureService factureService) {
        this.paiementRepository = paiementRepository;
        this.factureService = factureService;
    }

    // Basic CRUD Operations
    public List<Paiement> findAll() {
        return paiementRepository.findAll();
    }

    public Paiement findById(int id) {
        return paiementRepository.findById(id).orElse(null);
    }

    @Transactional
    public Paiement save(Paiement paiement) {
        validatePaiement(paiement);

        // Set default payment date if not provided
        if (paiement.getDatePaiement() == null) {
            paiement.setDatePaiement(LocalDate.now());
        }

        // Set default status if not provided
        if (paiement.getStatut() == null || paiement.getStatut().isEmpty()) {
            paiement.setStatut("PENDING");
        }

        Paiement savedPaiement = paiementRepository.save(paiement);
        updateFactureStatus(savedPaiement.getFactureId());

        return savedPaiement;
    }

    public void deleteById(int id) {
        Paiement paiement = findById(id);
        if (paiement != null) {
            int factureId = paiement.getFactureId();
            paiementRepository.deleteById(id);
            updateFactureStatus(factureId);
        }
    }

    // Specialized Queries
    public List<Paiement> findByFactureId(int factureId) {
        return paiementRepository.findByFactureId(factureId);
    }

    public List<Paiement> findByClientId(int clientId) {
        return paiementRepository.findByClientId(clientId);
    }

    public List<Paiement> findRecentPaiements(int count) {
        return paiementRepository.findRecentPaiements(count);
    }

    // Payment Processing
    @Transactional
    public Paiement validatePaiement(int id) {
        int updated = paiementRepository.validatePaiement(id);
        if (updated > 0) {
            Paiement paiement = findById(id);
            updateFactureStatus(paiement.getFactureId());
            return paiement;
        }
        return null;
    }

    // Search and Reporting
    public List<Paiement> searchPaiements(String modePaiement, Double minAmount, Double maxAmount) {
        return paiementRepository.searchPaiements(modePaiement, minAmount, maxAmount);
    }

    public double calculateTotalPaiements(String startDate, String endDate) {
        LocalDate start = startDate != null ? LocalDate.parse(startDate) : null;
        LocalDate end = endDate != null ? LocalDate.parse(endDate) : null;
        return paiementRepository.calculateTotalPaiements(start, end);
    }

    // Business Logic
    public double getFactureBalance(int factureId) {
        return paiementRepository.getFactureBalance(factureId);
    }

    public boolean isPaymentComplete(int factureId) {
        return getFactureBalance(factureId) <= 0;
    }

    // Private helper methods
    private void validatePaiement(Paiement paiement) {
        if (paiement.getFactureId() <= 0) {
            throw new IllegalArgumentException("Invoice ID must be positive");
        }
        if (paiement.getMontant() <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }
        if (paiement.getDatePaiement() != null && paiement.getDatePaiement().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Payment date cannot be in the future");
        }
    }

    private void updateFactureStatus(int factureId) {
        if (isPaymentComplete(factureId)) {
            factureService.markAsPaid(factureId, "MULTIPLE");
        }
    }

    // Additional business methods
    public String generatePaymentReceipt(int paiementId) {
        Paiement paiement = findById(paiementId);
        if (paiement == null) {
            return "Payment not found";
        }

        return String.format(
                "PAYMENT RECEIPT\n" +
                        "Payment ID: %d\n" +
                        "Invoice: %d\n" +
                        "Amount: %.2f\n" +
                        "Date: %s\n" +
                        "Method: %s\n" +
                        "Status: %s\n" +
                        "Reference: %s",
                paiement.getId(),
                paiement.getFactureId(),
                paiement.getMontant(),
                paiement.getDatePaiement(),
                paiement.getModePaiement(),
                paiement.getStatut(),
                paiement.getReference() != null ? paiement.getReference() : "N/A"
        );
    }
}