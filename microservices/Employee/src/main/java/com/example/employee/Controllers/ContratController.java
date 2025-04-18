package com.example.employee.Controllers;
import com.example.employee.Entities.Contrat;
import com.example.employee.Services.ContratService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/Contrat")
public class ContratController {
    private final ContratService contratService;

    public ContratController(ContratService  contratService){

        this.contratService=contratService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Contrat>> listContrats(){
        return new ResponseEntity<>(contratService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/ajouter")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Contrat> createContrat(@RequestBody Contrat contrat) {
        return new ResponseEntity<>(contratService.addContrat(contrat), HttpStatus.OK);
    }
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Contrat> updateContrat(@PathVariable(value = "id") long id,
                                                 @RequestBody Contrat contrat){
        return new ResponseEntity<>(contratService.updateContrat(id, contrat),
                HttpStatus.OK);
    }
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteContrat(@PathVariable(value = "id") long id) {
        return new ResponseEntity<>(contratService.deleteContrat(id), HttpStatus.OK);
    }
    @GetMapping("/contrats/{id}")
    public ResponseEntity<Contrat> getContratById(@PathVariable Long id) {
        Contrat contrat = (Contrat) contratService.getContratById(id);
        if (contrat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(contrat);
    }
    @GetMapping
    public Page<Contrat> getContrats(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) String selectedType,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return contratService.findContrats(searchText, selectedType, startDate, endDate, pageable);
    }


    }



