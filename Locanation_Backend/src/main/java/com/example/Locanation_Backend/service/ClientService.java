package com.example.Locanation_Backend.service;

import com.example.Locanation_Backend.model.Client;
import com.example.Locanation_Backend.repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepo clientRepo;

    @Autowired
    public ClientService(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    // Basic CRUD Operations
    public List<Client> findAll() {
        return clientRepo.findAll();
    }

    public Client findById(int id) {
        return clientRepo.findById(id).orElse(null);
    }

    public Client save(Client client) {
        // Basic validation before saving
        if (client.getNom() == null || client.getNom().isEmpty() ||
                client.getPrenom() == null || client.getPrenom().isEmpty() ||
                client.getCin() == null || client.getCin().isEmpty()) {
            throw new IllegalArgumentException("Client must have at least nom, prenom, and CIN");
        }
        return clientRepo.save(client);
    }

    public void deleteById(int id) {
        clientRepo.deleteById(id);
    }

    // Search Operations
    public List<Client> searchClients(String nom, String prenom, String cin) {
        return clientRepo.searchClients(nom, prenom, cin);
    }

    public Client findByDrivingLicense(String permisConduire) {
        if (permisConduire == null || permisConduire.isEmpty()) {
            throw new IllegalArgumentException("Driver's license number cannot be empty");
        }
        return clientRepo.findByDrivingLicense(permisConduire);
    }

    // Business Logic Methods
    public boolean isClientEligibleForRental(int clientId) {
        Client client = findById(clientId);
        if (client == null) return false;

        // Example business rule: Client must have valid driving license
        return client.getPermis_conduire() != null &&
                !client.getPermis_conduire().isEmpty();
    }

    public boolean clientExists(int clientId) {
        return findById(clientId) != null;
    }
}