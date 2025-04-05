package com.example.formations.coontrollers;

import com.example.formations.FormationsApplication;
import com.example.formations.entities.Formation;
import com.example.formations.repositories.FormationRepository;
import jakarta.persistence.GeneratedValue;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/formation")
public class FormationController {

    private final FormationRepository formation;

    public FormationController(FormationRepository formation) {
        this.formation = formation;
    }
@GetMapping("/GetAll")
    public List<Formation>getAll(){
        return formation.findAll();
    }
@PostMapping("/AddFormation")
    public  Formation addFormation(@RequestBody  Formation f){
        return formation.save(f);
    }


    @PutMapping("/UpdateFormation/{id}")

    public Formation UpdateFormation(@PathVariable Long id, @RequestBody Formation updatedZone) {
        Formation existingZone = formation.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found"));



        existingZone.setLocation(updatedZone.getLocation());
        existingZone.setDescription(updatedZone.getDescription());
        existingZone.setName(updatedZone.getName());

        return formation.save(existingZone);
    }

    @DeleteMapping("/DeleteFormation/{id}")
    public void DeleteFormation(@PathVariable Long id){
        formation.deleteById(id);
    }



    @GetMapping("/GetbyId/{id}")
    public Formation getChantierById(@PathVariable Long id) {
        return formation.findById(id).orElseThrow(() -> new RuntimeException("chantier not found"));
    }


}
