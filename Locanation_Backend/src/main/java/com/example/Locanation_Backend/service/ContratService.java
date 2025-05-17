package com.example.Locanation_Backend.service;

import com.example.Locanation_Backend.model.Contrat;
import com.example.Locanation_Backend.repository.ContratRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ContratService {

    private final ContratRepository contratRepository;

    @Autowired
    public ContratService(ContratRepository contratRepository) {
        this.contratRepository = contratRepository;
    }

    // Basic CRUD Operations
    public List<Contrat> findAll() {
        return contratRepository.findAll();
    }

    public Contrat findById(int id) {
        return contratRepository.findById(id).orElse(null);
    }

    public Contrat save(Contrat contrat) {
        validateContrat(contrat);
        return contratRepository.save(contrat);
    }

    public void deleteById(int id) {
        contratRepository.deleteById(id);
    }

    // Specialized Queries
    public List<Contrat> findByClientId(int clientId) {
        return contratRepository.findByClientId(clientId);
    }

    public List<Contrat> findByVehiculeId(int vehiculeId) {
        return contratRepository.findByVehiculeId(vehiculeId);
    }

    public List<Contrat> findActiveContrats() {
        return contratRepository.findActiveContrats();
    }

    public List<Contrat> searchContrats(String dateDebut, String dateFin, String statut) {
        return contratRepository.searchContrats(dateDebut, dateFin, statut);
    }

    // Business Logic Methods
    public boolean isVehiculeAvailable(int vehiculeId, String dateDebut, String dateFin) {
        List<Contrat> conflictingContrats = contratRepository.searchContrats(
                        dateDebut,
                        dateFin,
                        "ACTIVE"
                ).stream()
                .filter(c -> c.getVehiculeId() == vehiculeId)
                .toList();

        return conflictingContrats.isEmpty();
    }

    public double calculateContractAmount(int vehiculeId, LocalDate dateDebut, LocalDate dateFin, int estimatedKm) {
        long days = java.time.temporal.ChronoUnit.DAYS.between(dateDebut, dateFin);
        // In a real implementation, you would fetch the vehicle's pricing details
        double dailyRate = 100.0; // Default value, should come from vehicle
        double kmRate = 0.5; // Default value, should come from vehicle
        return (days * dailyRate) + (estimatedKm * kmRate);
    }

    public Contrat closeContract(int contratId, int finalKilometrage, LocalDate returnDate) {
        Contrat contrat = findById(contratId);
        if (contrat != null) {
            contrat.setDateRetour(returnDate);
            contrat.setKilometrageRetour(finalKilometrage);
            contrat.setStatut("COMPLETED");
            return save(contrat);
        }
        return null;
    }

    // Validation
    private void validateContrat(Contrat contrat) {
        if (contrat.getClientId() <= 0) {
            throw new IllegalArgumentException("Client ID must be positive");
        }
        if (contrat.getVehiculeId() <= 0) {
            throw new IllegalArgumentException("Vehicle ID must be positive");
        }
        if (contrat.getDateDebut() == null || contrat.getDateFin() == null) {
            throw new IllegalArgumentException("Start and end dates are required");
        }
        if (contrat.getDateDebut().isAfter(contrat.getDateFin())) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        if (contrat.getKilometrageDepart() < 0) {
            throw new IllegalArgumentException("Starting mileage cannot be negative");
        }
    }

    // Additional utility methods
    public int countActiveContratsByVehicule(int vehiculeId) {
        return contratRepository.countActiveContratsByVehicule(vehiculeId);
    }

    public boolean contractExists(int contratId) {
        return findById(contratId) != null;
    }
}