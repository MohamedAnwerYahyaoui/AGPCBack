package com.example.budget.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
public class Expences {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private int Montant;
    private String Description;
    private Category Category;


    @ManyToOne
    private Budget budget;

    public Expences() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMontant() {
        return Montant;
    }

    public void setMontant(int montant) {
        Montant = montant;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public com.example.budget.entities.Category getCategory() {
        return Category;
    }

    public void setCategory(com.example.budget.entities.Category category) {
        Category = category;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public Expences(long id, int montant, String description, com.example.budget.entities.Category category, Budget budget) {
        this.id = id;
        Montant = montant;
        Description = description;
        Category = category;
        this.budget = budget;
    }
}
