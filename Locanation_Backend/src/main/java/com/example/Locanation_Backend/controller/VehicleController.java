package com.example.Locanation_Backend.controller;

import com.example.Locanation_Backend.model.Vehicle;
import com.example.Locanation_Backend.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/{id}")
    public Vehicle findById(@PathVariable int id) {
        return vehicleService.findById(id);
    }

    @GetMapping
    public List<Vehicle> findAll() {
        return vehicleService.findAll();
    }

    @GetMapping("/available")
    public List<Vehicle> findAvailableVehicles() {
        return vehicleService.findAvailableVehicles();
    }

    @PostMapping
    public Vehicle createVehicle(@RequestBody Vehicle vehicle) {
        return vehicleService.save(vehicle);
    }

    @PutMapping("/{id}")
    public Vehicle updateVehicle(@PathVariable int id, @RequestBody Vehicle vehicle) {
        vehicle.setId(id);
        return vehicleService.save(vehicle);
    }

    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable int id) {
        vehicleService.deleteById(id);
    }

    @GetMapping("/search")
    public List<Vehicle> searchVehicles(@RequestParam(required = false) String marque,
                                        @RequestParam(required = false) String modele,
                                        @RequestParam(required = false) Double maxPrice) {
        return vehicleService.searchVehicles(marque, modele, maxPrice);
    }
}