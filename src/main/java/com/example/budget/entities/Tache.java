package com.example.budget.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Entity
public class Tache {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String nom;
    @JsonIgnore
@OneToMany(mappedBy = "tache",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Budget>budgets= new ArrayList<>();;

    public Tache() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Budget> getBudgets() {
        return budgets;
    }

    public void setBudgets(List<Budget> budgets) {
        this.budgets = budgets;
    }

    public Tache(Long id, String nom, List<Budget> budgets) {
        this.id = id;
        this.nom = nom;
        this.budgets = budgets;
    }
}

