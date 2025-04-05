package com.example.budget.controllers;

import com.example.budget.entities.Budget;
import com.example.budget.entities.Expences;
import com.example.budget.entities.Tache;
import com.example.budget.repositories.BudgetRepository;
import com.example.budget.repositories.ExpencesRepository;
import com.example.budget.repositories.TacheRepository;
import com.example.budget.services.FiancesServices;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RestController
@RequestMapping("/finance")
public class FinanceController {
    private final FiancesServices finance;
    private final BudgetRepository bdg;

    private  final ExpencesRepository ex;
private final TacheRepository tach;
    public FinanceController(FiancesServices finance, BudgetRepository bdg, ExpencesRepository ex, TacheRepository tach) {
        this.finance = finance;
        this.bdg = bdg;
        this.ex = ex;
        this.tach = tach;
    }


    //Budget
    @PostMapping("/Budget/AddBudget")
    public ResponseEntity<Budget> AddBudget(@RequestBody Budget request) {
        // 🔹 Vérifier que la tâche existe
        Tache tache = tach.findById(request.getTache().getId())
                .orElseThrow(() -> new RuntimeException("Tâche introuvable avec ID: " + request.getTache().getId()));

        // 🔹 Créer et sauvegarder le Budget
        Budget budget = new Budget();
        budget.setNom(request.getNom());
        budget.setDescription(request.getDescription());
        budget.setMontant(request.getMontant());
        budget.setMontant_left(request.getMontant_left());
        budget.setExpences(request.getExpences());
        budget.setTache(tache); // ✅ Associer la tâche existante

        Budget savedBudget = finance.addBudget(budget);
        return ResponseEntity.ok(savedBudget);
    }



    @GetMapping("/Budget/getAll")
    public List<Budget> GetAllBudget(){return finance.getAllBudget();}




    @PutMapping("Budget/UpdateBudget/{id}")
    public ResponseEntity<Budget> updateBudget(@PathVariable Long id, @RequestBody Budget request) {
        // 🔹 Vérifier si le budget existe
        Budget existingBudget = bdg.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget introuvable avec ID: " + id));

        // 🔹 Vérifier si la tâche existe et l'associer
        if (request.getTache() != null) {
            Tache tache = tach.findById(request.getTache().getId())
                    .orElseThrow(() -> new RuntimeException("Tâche introuvable avec ID: " + request.getTache().getId()));
            existingBudget.setTache(tache);
        }

        // 🔹 Mettre à jour les champs du budget
        existingBudget.setNom(request.getNom());
        existingBudget.setDescription(request.getDescription());
        existingBudget.setMontant(request.getMontant());
        existingBudget.setMontant_left(request.getMontant_left());
        existingBudget.setExpences(request.getExpences());

        // 🔹 Sauvegarder les modifications
        Budget updatedBudget = bdg.save(existingBudget);
        return ResponseEntity.ok(updatedBudget);
    }



  //  @PutMapping("/Budget/UpdateBudget")
  //  public  Budget UpdateBudget(Budget budget){
     //   return finance.ModifBudget(budget);
   // }

    @DeleteMapping("Budget/DeleteBudget/{id}")
    public void deleteBudget(@PathVariable Long id) {

        bdg.deleteById(id);
    }

    @GetMapping("Budget/GetBudget/{id}")
    public ResponseEntity<Budget> getBudgetById(@PathVariable Long id) {
        Budget budget = bdg.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget introuvable avec ID: " + id));

        return ResponseEntity.ok(budget);
    }



    @GetMapping("/Budget/FIndByTache")
    public List<Budget>FindByTache(String tache){
        return finance.FindBudgetByTache(tache);
    }



    //Expences


    @GetMapping("/Expences/GetAllExpences")

    public List<Expences>GetAllExpences(){
        return finance.getAllExpences();
    }




    @PostMapping("/Expences/AddExpences")
    public ResponseEntity<Expences> AddExpences(@RequestBody Expences request) {
        // 🔹 Vérifier que la tâche existe
        Budget tache = bdg.findById(request.getBudget().getId())
                .orElseThrow(() -> new RuntimeException("Tâche introuvable avec ID: " + request.getBudget().getId()));

        // 🔹 Créer et sauvegarder le Budget
        Expences expences = new Expences();
        expences.setCategory(request.getCategory());
        expences.setDescription(request.getDescription());
        expences.setMontant(request.getMontant());
        expences.setBudget(tache); // ✅ Associer la tâche existante

        Expences savedBudget = finance.AddExpences(expences);
        return ResponseEntity.ok(savedBudget);
    }


    @PutMapping("/Expences/UpdateExpences/{id}")
    public ResponseEntity<Expences> updateBudget(@PathVariable Long id, @RequestBody Expences request) {
        // 🔹 Vérifier si le expences existe
        Expences existingBudget = ex.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget introuvable avec ID: " + id));

        // 🔹 Vérifier si la budget existe et l'associer
        if (request.getBudget() != null) {
            Budget tache = bdg.findById(request.getBudget().getId())
                    .orElseThrow(() -> new RuntimeException("Tâche introuvable avec ID: " + request.getBudget().getId()));
            existingBudget.setBudget(tache);
        }

        // 🔹 Mettre à jour les champs du Expences
        existingBudget.setMontant(request.getMontant());
        existingBudget.setCategory(request.getCategory());
        existingBudget.setDescription(request.getDescription());


        // 🔹 Sauvegarder les modifications
        Expences updatedBudget = ex.save(existingBudget);
        return ResponseEntity.ok(updatedBudget);
    }


    @DeleteMapping("/Expences/Delete/{id}")
    public void DeleteExpences( @PathVariable Long id){
        finance.DeleteExpences(id);
    }



    @GetMapping("Expences/GetExpences/{id}")
    public ResponseEntity<Expences> getExpencesById(@PathVariable Long id) {
        Expences budget = ex.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget introuvable avec ID: " + id));

        return ResponseEntity.ok(budget);
    }


    @GetMapping("/Expences/GetByIdBudget/{id}")
    public List<Expences>getByBudgetId(@PathVariable Long id){
        return ex.findByBudget_Id(id);
    }



    //TAches


    @GetMapping("/Taches/all")

    public List<Tache> GetAll(){
        return finance.getAllTaches();
    }

}
