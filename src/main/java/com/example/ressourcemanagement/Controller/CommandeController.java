package com.example.ressourcemanagement.Controller;

import com.example.ressourcemanagement.Models.Commande;
import com.example.ressourcemanagement.Services.CommandeFunImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commande")
public class CommandeController {
    private final CommandeFunImpl commandeService;

    public CommandeController(CommandeFunImpl commandeService){
        this.commandeService=commandeService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Commande>> listCommande(){
        return new ResponseEntity<>(commandeService.findAll(), HttpStatus.OK);
    }




    @PostMapping("/ajouter")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Commande> createCommande(@RequestBody Commande commande) {
        return new ResponseEntity<>(commandeService.ajouterCommande(commande), HttpStatus.OK);
    }
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Commande> updateCommande(@PathVariable(value = "id") long id,
                                                   @RequestBody Commande commande){
        return new ResponseEntity<>(commandeService.updateCommande(id, commande),
                HttpStatus.OK);
    }
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteCommande(@PathVariable(value = "id") long id) {
        return new ResponseEntity<>(commandeService.deleteCommande(id), HttpStatus.OK);
    }

}
