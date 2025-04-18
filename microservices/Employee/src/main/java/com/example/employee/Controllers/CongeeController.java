package com.example.employee.Controllers;
import com.example.employee.Entities.Congee;
import com.example.employee.Enum.Etat;
import com.example.employee.Services.CongeeService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/Congee")
public class CongeeController {
    private final CongeeService congeeService;

    public CongeeController(CongeeService  congeeService){

        this.congeeService=congeeService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Congee>> listCongees(){
        return new ResponseEntity<>(congeeService.findAll(), HttpStatus.OK);
    }
    @PostMapping("/employee/{employeeId}")
    public ResponseEntity<Congee> addCongeeToEmployee(@PathVariable Long employeeId, @RequestBody Congee congee) {
        Congee congee1 = congeeService.addCongeeToEmployee(employeeId, congee);
        return ResponseEntity.ok(congee1);
    }
    //Consulter Congées par Etat

    @GetMapping("/etat/{etat}")
    public ResponseEntity<List<Congee>> getCongesByEtat(@PathVariable Etat etat) {
        List<Congee> conges = congeeService.getCongesByEtat(etat);
        return new ResponseEntity<>(conges, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Congee> updateCongee(@PathVariable(value = "id") long id,
                                               @RequestBody Congee congee){
        return new ResponseEntity<>(congeeService.updateCongee(id, congee),
                HttpStatus.OK);
    }
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteCongee(@PathVariable(value = "id") long id) {
        return new ResponseEntity<>(congeeService.deleteCongee(id), HttpStatus.OK);
    }
    @GetMapping("/congees/{id}")
    public ResponseEntity<Congee> getCongeeById(@PathVariable Long id) {
        Congee congee = (Congee) congeeService.getCongeeById(id);
        if (congee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(congee);
    }
    @GetMapping
    public Page<Congee> getCongees(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) String etat,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return congeeService.getCongees(searchTerm, etat, start, end, page, size);
    }
    @GetMapping("/check/nom/{nom}")
    public long checkCongeeByNom(@PathVariable String nom) {
        return congeeService.checkRemainingDaysByNom(nom);
    }
}
