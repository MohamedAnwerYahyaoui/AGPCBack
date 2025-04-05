package com.example.budget.DTO;

public class BudgetRequest {
    private String nom;
    private String description;
    private int montant;
    private int montantLeft; // ✅ Ensure correct camelCase
    private int expences;
    private Long tacheId;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public int getMontantLeft() {
        return montantLeft;
    }

    public void setMontantLeft(int montantLeft) {
        this.montantLeft = montantLeft;
    }

    public int getExpences() {
        return expences;
    }

    public void setExpences(int expences) {
        this.expences = expences;
    }

    public Long getTacheId() {
        return tacheId;
    }

    public void setTacheId(Long tacheId) {
        this.tacheId = tacheId;
    }

    public BudgetRequest(String nom, String description, int montant, int montantLeft, int expences, Long tacheId) {
        this.nom = nom;
        this.description = description;
        this.montant = montant;
        this.montantLeft = montantLeft;
        this.expences = expences;
        this.tacheId = tacheId;
    }
}
