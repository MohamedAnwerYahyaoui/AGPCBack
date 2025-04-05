package com.example.budget.services;

import com.example.budget.entities.Budget;
import com.example.budget.entities.Expences;
import com.example.budget.entities.Tache;
import com.example.budget.repositories.BudgetRepository;
import com.example.budget.repositories.ExpencesRepository;
import com.example.budget.repositories.TacheRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FiancesServices {

    private final BudgetRepository bg;
    private final ExpencesRepository ex;
    private  final TacheRepository tch;

    public FiancesServices(BudgetRepository bg, ExpencesRepository ex, TacheRepository tch) {
        this.bg = bg;
        this.ex = ex;
        this.tch = tch;
    }


    //Budget

  public List<Budget> getAllBudget(){
        return bg.findAll();

  }


  public Budget addBudget(Budget budget){
        return bg.save(budget);
  }

  public Budget ModifBudget(Budget budget){
return bg.save(budget);

    }

    public void DeleteBudget(Long id){
        bg.deleteById(id);
    }
public  Budget findById(Long id){
        return bg.findById(id).get();
}

 public List<Budget> FindBudgetByTache(String  tache){
        return bg.findByTache_Nom(tache);
    }


//Expences



    public List<Expences> getAllExpences(){
        return ex.findAll();

    }

    public Expences AddExpences(Expences expences){
        return ex.save(expences);
    }

    public Expences UpdateExpences(Expences expences){
        return ex.save(expences);
    }

    public void DeleteExpences(Long id){
         ex.deleteById(id);
    }
    public Expences GEtByIdExpences(Long id){
        return ex.findById(id).get();
    }

    //Tache



public  List<Tache> getAllTaches(){
        return tch.findAll();
}












}
