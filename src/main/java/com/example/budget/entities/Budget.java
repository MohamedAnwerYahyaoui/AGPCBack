package com.example.budget.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
public class Budget {
@Id

@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String Description;
    private int Montant;
    private int expences ;
    private int Montant_left;
    @ManyToOne
    private Tache tache;
@JsonIgnore
    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Expences>expencess;

    public Budget() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMontant() {
        return Montant;
    }

    public void setMontant(int montant) {
        Montant = montant;
    }

    public int getExpences() {
        return expences;
    }

    public void setExpences(int expences) {
        this.expences = expences;
    }

    public int getMontant_left() {
        return Montant_left;
    }

    public void setMontant_left(int montant_left) {
        Montant_left = montant_left;
    }

    public Tache getTache() {
        return tache;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
    }

    public List<Expences> getExpencess() {
        return expencess;
    }

    public void setExpencess(List<Expences> expencess) {
        this.expencess = expencess;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Budget(Long id, String nom, String description, int montant, int expences, int montant_left, Tache tache, List<Expences> expencess) {
        this.id = id;
        this.nom = nom;
        Description = description;
        Montant = montant;
        this.expences = expences;
        Montant_left = montant_left;
        this.tache = tache;
        this.expencess = expencess;
    }
}




