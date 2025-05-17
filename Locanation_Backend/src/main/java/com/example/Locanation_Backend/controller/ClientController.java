package com.example.Locanation_Backend.controller;

import com.example.Locanation_Backend.model.Client;
import com.example.Locanation_Backend.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable int id) {
        return clientService.findById(id);
    }

    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientService.save(client);
    }

    @PutMapping("/{id}")
    public Client updateClient(@PathVariable int id, @RequestBody Client client) {
        client.setId(id);
        return clientService.save(client);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable int id) {
        clientService.deleteById(id);
    }

    @GetMapping("/search")
    public List<Client> searchClients(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String prenom,
            @RequestParam(required = false) String cin) {
        return clientService.searchClients(nom, prenom, cin);
    }

    @GetMapping("/by-license/{permisConduire}")
    public Client getClientByDrivingLicense(@PathVariable String permisConduire) {
        return clientService.findByDrivingLicense(permisConduire);
    }
}