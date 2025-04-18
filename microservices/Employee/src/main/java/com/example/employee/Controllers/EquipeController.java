package com.example.employee.Controllers;
import com.example.employee.Entities.Equipe;
import com.example.employee.Entities.RatingDTO;
import com.example.employee.Services.EquipeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/Equipe")
public class EquipeController {
    private final EquipeService equipeService;

    public EquipeController(EquipeService equipeService) {

        this.equipeService = equipeService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Equipe>> listEquipes() {
        return new ResponseEntity<>(equipeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipe> getEquipeById(@PathVariable("id") Long id) {
        Equipe equipe = equipeService.getEquipeById(id);
        return new ResponseEntity<>(equipe, HttpStatus.OK);
    }

    @GetMapping
    public Page<Equipe> getEquipes(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false, name = "selectedLivrable") Long livrableId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return equipeService.getEquipes(searchTerm, livrableId, pageable);
    }

    @PostMapping("/ajouter")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Equipe> createEquipe(@RequestBody Equipe equipe) {
        return new ResponseEntity<>(equipeService.addEquipe(equipe), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Equipe> updateEquipe(@PathVariable(value = "id") long id,
                                               @RequestBody Equipe equipe) {
        return new ResponseEntity<>(equipeService.updateEquipe(id, equipe),
                HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteEquipe(@PathVariable(value = "id") long id) {
        return new ResponseEntity<>(equipeService.deleteEquipe(id), HttpStatus.OK);
    }

    @PostMapping({"/affecterLivrable/{equipeId}/{livrableId}"})
    public Equipe affecterLivrableAEquipe(@PathVariable Long equipeId, @PathVariable Long livrableId) {
        return this.equipeService.affecterLivrableAEquipe(equipeId, livrableId);
    }

    @PutMapping("/{id}/rating")
    public ResponseEntity<Equipe> updateRating(@PathVariable Long id, @RequestBody RatingDTO ratingDTO) {
        Equipe updatedEquipe = equipeService.updateRating(id, ratingDTO.getRating());
        return ResponseEntity.ok(updatedEquipe);
    }
}
