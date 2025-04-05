package tn.esprit.livrable.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.livrable.Repository.LivrableRepo;
import tn.esprit.livrable.Repository.TacheRepo;
import tn.esprit.livrable.Service.TacheService;
import tn.esprit.livrable.Service.UserService;
import tn.esprit.livrable.entity.Livrable;
import tn.esprit.livrable.entity.Status;
import tn.esprit.livrable.entity.Tache;
import tn.esprit.livrable.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/tache")
public class TacheController {

    private final TacheService ts;
    private final UserService us;

    private final TacheRepo tr;
    private final LivrableRepo lr;




    public TacheController(TacheService ts,UserService us,TacheRepo tr,LivrableRepo lr){
this.tr=tr;
this.lr=lr;

        this.us=us;
        this.ts=ts;
    }




    @GetMapping("/all")
    public ResponseEntity<List<Tache>> listTache(){
        return new ResponseEntity<>(ts.findAll(), HttpStatus.OK);
    }




    @PostMapping("/ajouter")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Tache> createTache(@RequestBody Tache tache) {
        return new ResponseEntity<>(ts.ajouterTache(tache), HttpStatus.OK);
    }




@PutMapping("/{id}")
public ResponseEntity<?> updateTache(@PathVariable long id, @RequestBody Tache tache) {
    try {
        Tache updated = ts.updateTache(id, tache);
        return ResponseEntity.ok(updated);
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}






    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteTache(@PathVariable(value = "id") long id) {
        return new ResponseEntity<>(ts.deleteTache(id), HttpStatus.OK);
    }




    @GetMapping("/find/{id}")
    public Tache findById(@PathVariable Long id){
        return ts.findById(id);
    }






    @PutMapping("/assign/{tacheId}/{userId}")
    public ResponseEntity<?> assignTache(@PathVariable Long tacheId, @PathVariable Long userId) {
        try {
            Tache assignedTache = ts.assignerTacheAUser(tacheId, userId);
            return ResponseEntity.ok(assignedTache);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }





    @DeleteMapping("/unassign")
    public ResponseEntity<?> unassignTache(@RequestParam String tacheNom) {
            Tache updatedTache = ts.dessaffecterTacheDeUser(tacheNom);
            return ResponseEntity.ok(updatedTache);

    }
    @PutMapping("/livrer/{tacheId}/{livrableId}")
    public ResponseEntity<String> affecterTacheALivrableSiTermine(@PathVariable Long tacheId,@PathVariable Long livrableId) {
        ts.affecterTacheALivrableSiTermine(tacheId, livrableId);
        return ResponseEntity.ok("Tâche affectée au livrable avec succès");
    }

    @GetMapping("/{tacheId}/users")
    public ResponseEntity<List<User>> getUsersByTacheId(@PathVariable Long tacheId) {
        List<User> users = ts.getUsersByTacheId(tacheId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}/taches")
    public ResponseEntity<Set<Tache>> getTachesByUserId(@PathVariable Long userId) {
        Set<Tache> taches = us.getTachesByUserId(userId);
        return ResponseEntity.ok(taches);
    }



    @PostMapping("/transferer-terminees")
    public ResponseEntity<String> transfererTachesTerminees() {
        ts.transfererTachesTermineesVersLivrable();
        return ResponseEntity.ok("Transfert des tâches terminées vers les livrables effectué");
    }



    @PostMapping("/transferer-taches-terminees")
    public ResponseEntity<String> transfererTachesTermineesVersLivrable() {
        Set<Tache> tachesTerminees = tr.findByStatus(Status.DONE);

        if (tachesTerminees.isEmpty()) {
            return ResponseEntity.ok("Aucune tâche terminée à transférer.");
        }

        for (Tache tache : tachesTerminees) {
            Livrable livrable = new Livrable(tache.getNom(), tache.getDescription(), LocalDate.now(), new ArrayList<>());
            lr.save(livrable);
            tr.delete(tache);
        }

        return ResponseEntity.ok("Les tâches terminées ont été transférées avec succès.");
    }





}
