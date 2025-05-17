package com.example.Locanation_Backend.service;

import com.example.Locanation_Backend.model.Vehicle;
import com.example.Locanation_Backend.repository.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepo vehicleRepo;

    public Vehicle findById(int id) {
        return vehicleRepo.findById(id).orElse(null);
    }

    public List<Vehicle> findAll() {
        return vehicleRepo.findAll();
    }

    public List<Vehicle> findAvailableVehicles() {
        return vehicleRepo.findAvailableVehicles();
    }

    public Vehicle save(Vehicle vehicle) {
        // You might want to add validation or business logic here
        return vehicleRepo.save(vehicle);
    }

    public void deleteById(int id) {
        vehicleRepo.deleteById(id);
    }

    public List<Vehicle> searchVehicles(String marque, String modele, Double maxPrice) {
        return vehicleRepo.searchVehicles(marque, modele, maxPrice);
    }

    // Additional business logic methods could be added here
    public double calculateRentalCost(int vehicleId, int days, double kilometers) {
        Vehicle vehicle = findById(vehicleId);
        if (vehicle == null) return 0;

        return (vehicle.getPrix_journalier() * days) +
                (vehicle.getPrix_par_km() * kilometers);
    }
}