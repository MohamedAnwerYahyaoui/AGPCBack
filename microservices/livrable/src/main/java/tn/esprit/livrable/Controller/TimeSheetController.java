package tn.esprit.livrable.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.livrable.Service.TimeSheetService;
import tn.esprit.livrable.entity.Tache;
import tn.esprit.livrable.entity.Timesheet;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/timeSheet")
public class TimeSheetController {


    private final TimeSheetService tss;

    public TimeSheetController(TimeSheetService tss){
        this.tss=tss;
    }



    @GetMapping("/all")
    public ResponseEntity<List<Timesheet>> listTimeSheet(){
        return new ResponseEntity<>(tss.findAll(), HttpStatus.OK);
    }




    @PostMapping("/ajouter")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Timesheet> createTimeSheet(@RequestBody Timesheet timeSheet) {
        return new ResponseEntity<>(tss.ajouterTimeSheet(timeSheet), HttpStatus.OK);
    }
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Timesheet> updateTimeSheet(@PathVariable(value = "id") long id,
                                             @RequestBody Timesheet timeSheet){
        return new ResponseEntity<>(tss.updateTimeSheet(id, timeSheet),
                HttpStatus.OK);
    }
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteTimeSheet(@PathVariable(value = "id") long id) {
        return new ResponseEntity<>(tss.deleteTimeSheet(id), HttpStatus.OK);
    }




    @GetMapping("/find/{id}")
    public Timesheet findById(@PathVariable Long id){
        return tss.findById(id);
    }

    @PutMapping("/affecter/{tacheId}/{userId}")
    public ResponseEntity<Timesheet> affecterTimesheetATacheEtUser(@PathVariable Long tacheId, @PathVariable Long userId, @RequestBody Timesheet timesheet) {

        try {
            Timesheet createdTimesheet = tss.affecterTimesheetATacheEtUser(tacheId, userId, timesheet);
            return ResponseEntity.ok(createdTimesheet);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }




    @GetMapping("/heures-par-tache")
    public Map<String, Double> getHeuresParTache() {
        return tss.getHeuresParTache();
    }
















}
