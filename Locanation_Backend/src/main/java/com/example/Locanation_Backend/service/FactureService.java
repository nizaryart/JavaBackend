package com.example.Locanation_Backend.service;

import com.example.Locanation_Backend.model.Facture;
import com.example.Locanation_Backend.repository.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FactureService {

    private final FactureRepository factureRepository;

    @Autowired
    public FactureService(FactureRepository factureRepository) {
        this.factureRepository = factureRepository;
    }

    // Basic CRUD Operations
    public List<Facture> findAll() {
        return factureRepository.findAll();
    }

    public Facture findById(int id) {
        return factureRepository.findById(id).orElse(null);
    }

    public Facture save(Facture facture) {
        validateFacture(facture);

        // Generate invoice number if new
        if (facture.getId() == 0 && (facture.getNumeroFacture() == null || facture.getNumeroFacture().isEmpty())) {
            facture.setNumeroFacture(factureRepository.generateInvoiceNumber());
        }

        // Set default invoice date if not provided
        if (facture.getDateFacture() == null) {
            facture.setDateFacture(LocalDate.now());
        }

        return factureRepository.save(facture);
    }

    public void deleteById(int id) {
        factureRepository.deleteById(id);
    }

    // Specialized Queries
    public Facture findByContratId(int contratId) {
        return factureRepository.findByContratId(contratId);
    }

    public List<Facture> findByClientId(int clientId) {
        return factureRepository.findByClientId(clientId);
    }

    public List<Facture> findUnpaidFactures() {
        return factureRepository.findUnpaidFactures();
    }

    // Payment Processing
    public Facture markAsPaid(int id, String paymentMethod) {
        Facture facture = findById(id);
        if (facture != null) {
            facture.setStatut("PAID");
            facture.setModePaiement(paymentMethod);
            facture.setDatePaiement(LocalDate.now());
            return save(facture);
        }
        return null;
    }

    // Search and Reporting
    public List<Facture> searchFactures(String dateFrom, String dateTo, String statut) {
        LocalDate from = dateFrom != null ? LocalDate.parse(dateFrom) : null;
        LocalDate to = dateTo != null ? LocalDate.parse(dateTo) : null;
        return factureRepository.searchFactures(from, to, statut);
    }

    // Business Logic
    public String generateInvoicePrintVersion(int id) {
        Facture facture = findById(id);
        if (facture == null) {
            return "Invoice not found";
        }

        return String.format(
                "INVOICE #%s\nDate: %s\nAmount: %.2f\nTVA: %.2f%%\nStatus: %s\nPayment Method: %s",
                facture.getNumeroFacture(),
                facture.getDateFacture(),
                facture.getMontant(),
                facture.getTva(),
                facture.getStatut(),
                facture.getModePaiement() != null ? facture.getModePaiement() : "N/A"
        );
    }

    // Validation
    private void validateFacture(Facture facture) {
        if (facture.getContratId() <= 0) {
            throw new IllegalArgumentException("Contract ID must be positive");
        }
        if (facture.getMontant() <= 0) {
            throw new IllegalArgumentException("Invoice amount must be positive");
        }
        if (facture.getTva() < 0) {
            throw new IllegalArgumentException("TVA cannot be negative");
        }
        if (facture.getDateFacture() != null && facture.getDateFacture().isAfter(LocalDate.now().plusDays(1))) {
            throw new IllegalArgumentException("Invoice date cannot be in the future");
        }
    }

    // Additional Business Methods
    public double calculateTotalRevenue(LocalDate startDate, LocalDate endDate) {
        List<Facture> factures = factureRepository.searchFactures(startDate, endDate, "PAID");
        return factures.stream()
                .mapToDouble(Facture::getMontant)
                .sum();
    }

    public boolean invoiceExists(int id) {
        return findById(id) != null;
    }
}