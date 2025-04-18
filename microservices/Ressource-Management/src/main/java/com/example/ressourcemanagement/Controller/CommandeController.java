package com.example.ressourcemanagement.Controller;

import com.example.ressourcemanagement.Models.Commande;
import com.example.ressourcemanagement.Services.CommandeFunImpl;
import com.example.ressourcemanagement.Services.CommandeFunctionality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/commande")
public class CommandeController {
    @Autowired
    private CommandeFunctionality commandeFunctionality;

    @GetMapping
    public ResponseEntity<List<Commande>> getAllCommandes() {
        List<Commande> commandes = commandeFunctionality.getAllCommandes();
        System.out.println("Commandes récupérées : " + commandes.size()); // Log du nombre de commandes
        if (commandes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(commandes);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Commande> getCommandeById(@PathVariable int id) {
        Commande commande = commandeFunctionality.getCommandeById(id);
        return ResponseEntity.ok(commande);
    }

    @PostMapping("/add")
    public ResponseEntity<Commande> createCommande(@RequestBody Commande commande) {
        Commande createdCommande = commandeFunctionality.createCommande(commande);
        return ResponseEntity.ok(createdCommande);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Commande> updateCommande(@PathVariable int id, @RequestBody Commande commandeDetails) {
        Commande updatedCommande = commandeFunctionality.updateCommande(id, commandeDetails);
        return ResponseEntity.ok(updatedCommande);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCommande(@PathVariable int id) {
        commandeFunctionality.deleteCommande(id);
        return ResponseEntity.ok("{\"message\": \"Commande supprimée avec succès !\"}");
    }

}
