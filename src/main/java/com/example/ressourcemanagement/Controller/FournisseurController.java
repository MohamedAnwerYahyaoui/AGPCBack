package com.example.ressourcemanagement.Controller;

import com.example.ressourcemanagement.Models.Fournisseur;
import com.example.ressourcemanagement.Services.FournisseurFunImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fournisseur")
public class FournisseurController {
    private final FournisseurFunImpl fournisseurService;

    public FournisseurController(FournisseurFunImpl fournisseurService){
        this.fournisseurService=fournisseurService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Fournisseur>> listFournisseur(){
        return new ResponseEntity<>(fournisseurService.findAll(), HttpStatus.OK);
    }




    @PostMapping("/ajouter")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Fournisseur> createFournisseur(@RequestBody Fournisseur fournisseur) {
        return new ResponseEntity<>(fournisseurService.ajouterFournisseur(fournisseur), HttpStatus.OK);
    }
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Fournisseur> updateFournisseur(@PathVariable(value = "id") long id,
                                                         @RequestBody Fournisseur fournisseur){
        return new ResponseEntity<>(fournisseurService.updateFournisseur(id, fournisseur),
                HttpStatus.OK);
    }
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteFournisseur(@PathVariable(value = "id") long id) {
        fournisseurService.deleteFournisseur(id);
        return ResponseEntity.ok("{\"message\": \"Fournisseur supprimé avec succès !\"}");
    }

}
