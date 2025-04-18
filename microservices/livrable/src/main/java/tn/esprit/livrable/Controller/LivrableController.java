package tn.esprit.livrable.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.livrable.Repository.LivrableRepo;
import tn.esprit.livrable.Service.LivrableService;
import tn.esprit.livrable.entity.Livrable;
import tn.esprit.livrable.entity.Tache;

import java.util.List;

@RestController
@RequestMapping("/livrable")
public class LivrableController {

    private final LivrableService ls;
    public LivrableController(LivrableService ls){
        this.ls=ls;
    }


    @GetMapping("/all")
    public ResponseEntity<List<Livrable>> listLivrable(){
        return new ResponseEntity<>(ls.findAll(), HttpStatus.OK);
    }




    @PostMapping("/ajouter")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Livrable> createLivrable(@RequestBody Livrable livrable) {
        return new ResponseEntity<>(ls.ajouterLivrable(livrable), HttpStatus.OK);
    }
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Livrable> updateLivrable(@PathVariable(value = "id") long id,
                                             @RequestBody Livrable livrable){
        return new ResponseEntity<>(ls.updatelivrable(id, livrable),
                HttpStatus.OK);
    }
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteLivrable(@PathVariable(value = "id") long id) {
        return new ResponseEntity<>(ls.deleteLivrable(id), HttpStatus.OK);
    }




    @GetMapping("/find/{id}")
    public Livrable findById(@PathVariable Long id){
        return ls.findById(id);
    }












}
