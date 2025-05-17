package com.example.Locanation_Backend.controller;

import com.example.Locanation_Backend.model.Contrat;
import com.example.Locanation_Backend.service.ContratService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contrats")
public class ContratsController {

    private final ContratService contratService;

    @Autowired
    public ContratsController(ContratService contratService) {
        this.contratService = contratService;
    }

    @GetMapping
    public List<Contrat> getAllContrats() {
        return contratService.findAll();
    }

    @GetMapping("/{id}")
    public Contrat getContratById(@PathVariable int id) {
        return contratService.findById(id);
    }

    @PostMapping
    public Contrat createContrat(@RequestBody Contrat contrat) {
        return contratService.save(contrat);
    }

    @PutMapping("/{id}")
    public Contrat updateContrat(@PathVariable int id, @RequestBody Contrat contrat) {
        contrat.setId(id);
        return contratService.save(contrat);
    }

    @DeleteMapping("/{id}")
    public void deleteContrat(@PathVariable int id) {
        contratService.deleteById(id);
    }

    @GetMapping("/client/{clientId}")
    public List<Contrat> getContratsByClient(@PathVariable int clientId) {
        return contratService.findByClientId(clientId);
    }

    @GetMapping("/vehicule/{vehiculeId}")
    public List<Contrat> getContratsByVehicule(@PathVariable int vehiculeId) {
        return contratService.findByVehiculeId(vehiculeId);
    }

    @GetMapping("/active")
    public List<Contrat> getActiveContrats() {
        return contratService.findActiveContrats();
    }

    @GetMapping("/search")
    public List<Contrat> searchContrats(
            @RequestParam(required = false) String dateDebut,
            @RequestParam(required = false) String dateFin,
            @RequestParam(required = false) String statut) {
        return contratService.searchContrats(dateDebut, dateFin, statut);
    }
}