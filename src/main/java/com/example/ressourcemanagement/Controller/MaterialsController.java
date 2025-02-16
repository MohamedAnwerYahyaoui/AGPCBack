package com.example.ressourcemanagement.Controller;

import com.example.ressourcemanagement.Models.Materials;
import com.example.ressourcemanagement.Services.MaterialsFunImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/ajouter")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Materials> createMaterials(@RequestBody Materials materials) {
        return new ResponseEntity<>(materialsService.ajouterMaterials(materials), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Materials> updateMaterials(@PathVariable(value = "id") long id,
                                                     @RequestBody Materials materials){
        return new ResponseEntity<>(materialsService.updateMaterials(id, materials),
                HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteMaterials(@PathVariable(value = "id") long id) {
        return new ResponseEntity<>(materialsService.deleteMaterials(id), HttpStatus.OK);
    }
}
