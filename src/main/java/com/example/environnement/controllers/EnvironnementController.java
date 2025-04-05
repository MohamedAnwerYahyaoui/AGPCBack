package com.example.environnement.controllers;

import com.example.environnement.entities.Chantier;
import com.example.environnement.entities.Users;
import com.example.environnement.entities.Zones;
import com.example.environnement.repositories.ChantierRepository;
import com.example.environnement.repositories.Usersrepository;
import com.example.environnement.repositories.ZonesRepository;
import com.example.environnement.services.ServiceEnvironnement;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/environnement")
@RestController
@Controller
public class EnvironnementController {

  private final ServiceEnvironnement en;
  private final ZonesRepository zoneRepository;
  private final ChantierRepository chantier;
  private final Usersrepository user;

    public EnvironnementController(ServiceEnvironnement en, ZonesRepository zoneRepository, ChantierRepository chantier, Usersrepository user) {
        this.en = en;
        this.zoneRepository = zoneRepository;
        this.chantier = chantier;
        this.user = user;
    }



    //Chantiers

    @GetMapping("/Chantier/getAll")
    public List<Chantier> GetAllChantier(){
        return en.getALlChantier();
    }

    @PostMapping("/Chantier/AddChantier")
    public Chantier AddChantier(@RequestBody Chantier chantier){
        return  en.addChantier(chantier);
    }




    @PutMapping("/Chantier/UpdateChantier/{id}")

    public Chantier UpdateChantier(@PathVariable Long id, @RequestBody Chantier updatedZone) {
        Chantier existingZone = chantier.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found"));


        existingZone.setNom(updatedZone.getNom());
        existingZone.setLocation(updatedZone.getLocation());

        return chantier.save(existingZone);
    }




    @DeleteMapping("/Chantier/DeleteChantier/{id}")
    public void DeleteChantier(@PathVariable Long id){
        en.DeleteChantier(id);
    }




    @GetMapping("/Chantier/GetbyId/{id}")
    public Chantier getChantierById(@PathVariable Long id) {
        return chantier.findById(id).orElseThrow(() -> new RuntimeException("chantier not found"));
    }




    //Zones


    @GetMapping("/Zones/GetAllZones")

    public List<Zones> getAllZones() {
        return zoneRepository.findAll();
    }


    @GetMapping("/Zones/GetById/{id}")
    public Zones getZoneById(@PathVariable Long id) {
        return zoneRepository.findById(id).orElseThrow(() -> new RuntimeException("Zone not found"));
    }


    @PostMapping("/Zones/AddZones")
    public ResponseEntity<Zones>  addZone(@RequestBody Zones zone) {
        // 🔹 Vérifier que la tâche existe
        Chantier tache = chantier.findById(zone.getChantier().getId())
                .orElseThrow(() -> new RuntimeException("Tâche introuvable avec ID: " + zone.getChantier().getId()));

        Users u = user.findById(zone.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Tâche introuvable avec ID: " + zone.getUser().getId()));

        // 🔹 Créer et sauvegarder le Budget
        Zones expences = new Zones();
        expences.setDescription(zone.getDescription());
        expences.setNom(zone.getNom());
        expences.setChantier(tache);
        expences.setUser(u); // ✅ Associer la tâche existante

        Zones savedBudget = en.AddZones(expences);
        return ResponseEntity.ok(savedBudget);    }



    @PutMapping("/Zones/UpdateZones/{id}")
    public Zones updateZone(@PathVariable  Long id, @RequestBody Zones updatedZone) {
        Zones existingZone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found"));

        if (updatedZone.getChantier() != null) {
            Chantier tache = chantier.findById(updatedZone.getChantier().getId())
                    .orElseThrow(() -> new RuntimeException("Tâche introuvable avec ID: " + updatedZone.getChantier().getId()));
            existingZone.setChantier(tache);
        }

        if (updatedZone.getUser() != null) {
            Users tachee = user.findById(updatedZone.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("Tâche introuvable avec ID: " + updatedZone.getUser().getId()));
            existingZone.setUser(tachee);
        }



        existingZone.setNom(updatedZone.getNom());
        existingZone.setDescription(updatedZone.getDescription());


        return zoneRepository.save(existingZone);
    }

    @DeleteMapping("/Zones/DeleteZones/{id}")
    public void deleteZone(@PathVariable Long id) {
        zoneRepository.deleteById(id);
    }
@GetMapping("/user/all")
    public List<Users>getal(){
        return user.findAll();
}
}
