package com.example.ressourcemanagement.Services;

import com.example.ressourcemanagement.Models.Commande;

import java.util.List;

public interface CommandeFunctionality {
    List<Commande> getAllCommandes();
    Commande getCommandeById(int id);
    Commande createCommande(Commande commande);
    Commande updateCommande(int id, Commande commandeDetails);
    void deleteCommande(int id);
}
