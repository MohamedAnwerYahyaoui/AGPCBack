package com.example.budget.repositories;

import com.example.budget.entities.Budget;
import com.example.budget.entities.Expences;
import com.example.budget.entities.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget,Long> {

   public List<Budget> findBudgetByTache_Nom(String tache);

public List<Budget>findByTache_Nom(String tache);

List<Budget>getBudgetById(Long id);
}
