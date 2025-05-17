package com.example.Locanation_Backend.controller;

import com.example.Locanation_Backend.model.Facture;
import com.example.Locanation_Backend.service.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/factures")
public class FacturesController {

    private final FactureService factureService;

    @Autowired
    public FacturesController(FactureService factureService) {
        this.factureService = factureService;
    }

    @GetMapping
    public List<Facture> getAllFactures() {
        return factureService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Facture> getFactureById(@PathVariable int id) {
        Facture facture = factureService.findById(id);
        return facture != null ? ResponseEntity.ok(facture) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Facture> createFacture(@RequestBody Facture facture) {
        try {
            Facture savedFacture = factureService.save(facture);
            return ResponseEntity.ok(savedFacture);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Facture> updateFacture(@PathVariable int id, @RequestBody Facture facture) {
        facture.setId(id);
        try {
            Facture updatedFacture = factureService.save(facture);
            return ResponseEntity.ok(updatedFacture);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacture(@PathVariable int id) {
        if (factureService.invoiceExists(id)) {
            factureService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/contrat/{contratId}")
    public ResponseEntity<Facture> getFactureByContrat(@PathVariable int contratId) {
        Facture facture = factureService.findByContratId(contratId);
        return facture != null ? ResponseEntity.ok(facture) : ResponseEntity.notFound().build();
    }

    @GetMapping("/client/{clientId}")
    public List<Facture> getFacturesByClient(@PathVariable int clientId) {
        return factureService.findByClientId(clientId);
    }

    @GetMapping("/unpaid")
    public List<Facture> getUnpaidFactures() {
        return factureService.findUnpaidFactures();
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<Facture> markAsPaid(@PathVariable int id, @RequestParam String paymentMethod) {
        Facture facture = factureService.markAsPaid(id, paymentMethod);
        return facture != null ? ResponseEntity.ok(facture) : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public List<Facture> searchFactures(
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo,
            @RequestParam(required = false) String statut) {
        return factureService.searchFactures(dateFrom, dateTo, statut);
    }

    @GetMapping("/{id}/print")
    public ResponseEntity<String> generatePrintVersion(@PathVariable int id) {
        String printVersion = factureService.generateInvoicePrintVersion(id);
        return printVersion.equals("Invoice not found") ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(printVersion);
    }
}