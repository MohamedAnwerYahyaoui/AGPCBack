package com.example.ressourcemanagement.Controller;

import com.example.ressourcemanagement.Models.Fournisseur;
import com.example.ressourcemanagement.Models.Materials;
import com.example.ressourcemanagement.Services.MaterialsFunImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/materials")
public class MaterialsController {

    private final MaterialsFunImpl materialsService;


    public MaterialsController(MaterialsFunImpl materialsService){
        this.materialsService = materialsService;
    }
    @GetMapping("/list")
    public ResponseEntity<List<Materials>> listMaterials(){
        return new ResponseEntity<>(materialsService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Materials>> getAllMaterials() {
        List<Materials> materials = materialsService.findAll();
        return new ResponseEntity<>(materials, HttpStatus.OK);
    }
    @PostMapping(value = "/ajouter")
    public ResponseEntity<Materials> createMaterials(@RequestBody Materials materials) {
        System.out.println("Nom reçu : " + materials.getName()); // Afficher le nom
        System.out.println("Quantité reçue : " + materials.getQuantity()); // Afficher la quantité
        System.out.println("Prix unitaire reçu : " + materials.getUnitPrice()); // Afficher le prix unitaire
        System.out.println("Catégorie reçue : " + materials.getCategorie()); // Afficher la catégorie

        Materials nouveauMaterial = materialsService.ajouterMaterials(materials); // Ajouter dans la base de données
        return new ResponseEntity<>(nouveauMaterial, HttpStatus.CREATED); // Retourner le matériau ajouté
    }


    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Materials> updateMaterials(@PathVariable(value = "id") long id,
                                                     @RequestBody Materials materials){
        return new ResponseEntity<>(materialsService.updateMaterials(id, materials),
                HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteMaterials(@PathVariable(value = "id") long id) {
        materialsService.deleteMaterials(id);
        return ResponseEntity.ok("{\"message\": \"Matériau supprimé avec succès !\"}");
    }

}
