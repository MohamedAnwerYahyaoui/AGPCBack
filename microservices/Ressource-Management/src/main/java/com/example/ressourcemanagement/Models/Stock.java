package com.example.ressourcemanagement.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Getter
@Setter

@AllArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "materiel_id")
    @JsonIgnoreProperties("stockList")
    private Materials materiel;

    private int currentQuantity;

    private int threshold;

    public Stock() {
    }

    public Stock(Materials materiel, int currentQuantity, int threshold) {
        this.materiel = materiel;
        this.currentQuantity = currentQuantity;
        this.threshold = threshold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Materials getMateriel() {
        return materiel;
    }

    public void setMateriel(Materials materiel) {
        this.materiel = materiel;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}