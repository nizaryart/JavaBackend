package com.example.Locanation_Backend.controller;

import com.example.Locanation_Backend.model.Paiement;
import com.example.Locanation_Backend.service.PaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paiements")
public class PaiementsController {

    private final PaiementService paiementService;

    @Autowired
    public PaiementsController(PaiementService paiementService) {
        this.paiementService = paiementService;
    }

    @GetMapping
    public List<Paiement> getAllPaiements() {
        return paiementService.findAll();
    }

    @GetMapping("/{id}")
    public Paiement getPaiementById(@PathVariable int id) {
        return paiementService.findById(id);
    }

    @PostMapping
    public Paiement createPaiement(@RequestBody Paiement paiement) {
        return paiementService.save(paiement);
    }

    @PutMapping("/{id}")
    public Paiement updatePaiement(@PathVariable int id, @RequestBody Paiement paiement) {
        paiement.setId(id);
        return paiementService.save(paiement);
    }

    @DeleteMapping("/{id}")
    public void deletePaiement(@PathVariable int id) {
        paiementService.deleteById(id);
    }

    @GetMapping("/facture/{factureId}")
    public List<Paiement> getPaiementsByFacture(@PathVariable int factureId) {
        return paiementService.findByFactureId(factureId);
    }

    @GetMapping("/client/{clientId}")
    public List<Paiement> getPaiementsByClient(@PathVariable int clientId) {
        return paiementService.findByClientId(clientId);
    }

    @GetMapping("/recent")
    public List<Paiement> getRecentPaiements(@RequestParam(defaultValue = "10") int count) {
        return paiementService.findRecentPaiements(count);
    }

    @GetMapping("/total")
    public double getTotalPaiements(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return paiementService.calculateTotalPaiements(startDate, endDate);
    }

    @PostMapping("/{id}/validate")
    public Paiement validatePaiement(@PathVariable int id) {
        return paiementService.validatePaiement(id);
    }

    @GetMapping("/search")
    public List<Paiement> searchPaiements(
            @RequestParam(required = false) String modePaiement,
            @RequestParam(required = false) Double minAmount,
            @RequestParam(required = false) Double maxAmount) {
        return paiementService.searchPaiements(modePaiement, minAmount, maxAmount);
    }
}