package com.example.employee.Controllers;
import com.example.employee.Entities.Livrable;
import com.example.employee.Services.LivrableService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Livrable")
public class LivrableController {
    private final LivrableService livrableService;

    public LivrableController(LivrableService  livrableService){

        this.livrableService=livrableService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Livrable>> listLivrables(){
        return new ResponseEntity<>(livrableService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/ajouter")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Livrable> createLivrable(@RequestBody Livrable livrable) {
        return new ResponseEntity<>(livrableService.addLivrable(livrable), HttpStatus.OK);
    }
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Livrable> updateLivrable(@PathVariable(value = "id") long id,
                                               @RequestBody Livrable livrable){
        return new ResponseEntity<>(livrableService.updateLivrable(id, livrable),
                HttpStatus.OK);
    }
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteLivrable(@PathVariable(value = "id") long id) {
        return new ResponseEntity<>(livrableService.deleteLivrable(id), HttpStatus.OK);
    }
    @GetMapping
    public Page<Livrable> getLivrables(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return livrableService.getLivrables(searchTerm, page, size);
    }
}
