package com.example.budget.repositories;

import com.example.budget.entities.Expences;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpencesRepository extends JpaRepository<Expences,Long> {


    List<Expences> findByBudget_Id(Long bd);

}
