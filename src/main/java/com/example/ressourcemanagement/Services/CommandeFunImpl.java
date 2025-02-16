package com.example.ressourcemanagement.Services;

import com.example.ressourcemanagement.DAO.CommandeRepository;
import com.example.ressourcemanagement.Models.Commande;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandeFunImpl {
    private CommandeRepository cr;

    public CommandeFunImpl(CommandeRepository cr){
        this.cr=cr;
    }



    public Commande ajouterCommande(Commande commande) {
        return cr.save(commande);
    }


    public List<Commande> findAll() {
        return cr.findAll();
    }


    public Commande updateCommande(long id, Commande newCommande) {
        if (cr.findById(id).isPresent()) {
            Commande commande = cr.findById(id).get();
            commande.setStatus(newCommande.getStatus());
            commande.setDate(newCommande.getDate());
            commande.setTotalAmount(newCommande.getTotalAmount());
            return cr.save(commande);
        } else
            return null;
    }


    public String deleteCommande(long id) {
        if (cr.findById(id).isPresent()) {
            cr.deleteById(id);
            return "commande supprimé";
        } else
            return "commande non supprimé";
    }
}
