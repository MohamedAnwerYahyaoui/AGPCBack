package com.example.ressourcemanagement.Services;

import com.example.ressourcemanagement.Models.Facture;

import java.util.List;

public interface FactureFunctionality {
    List<Facture> getAllFactures();
    Facture getFactureById(int id);
    Facture createFacture(Facture facture);
    Facture updateFacture(int id, Facture factureDetails);
    void deleteFacture(int id);
}
