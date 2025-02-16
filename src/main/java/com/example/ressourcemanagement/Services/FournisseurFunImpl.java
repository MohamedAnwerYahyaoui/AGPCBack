package com.example.ressourcemanagement.Services;

import com.example.ressourcemanagement.DAO.FournisseurRepository;
import com.example.ressourcemanagement.Models.Fournisseur;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FournisseurFunImpl {
    private FournisseurRepository fr;

    public FournisseurFunImpl(FournisseurRepository fr){
        this.fr=fr;
    }



    public Fournisseur ajouterFournisseur(Fournisseur fournisseur ) {
        return fr.save(fournisseur);
    }


    public List<Fournisseur> findAll() {
        return fr.findAll();
    }


    public Fournisseur updateFournisseur(long id, Fournisseur newFournisseur) {
        if (fr.findById(id).isPresent()) {
            Fournisseur fournisseur = fr.findById(id).get();
            fournisseur.setContact(newFournisseur.getContact());
            fournisseur.setName(newFournisseur.getName());
            fournisseur.setNumtel(newFournisseur.getNumtel());

            return fr.save(fournisseur);
        } else
            return null;
    }


    public String deleteFournisseur(long id) {
        if (fr.findById(id).isPresent()) {
            fr.deleteById(id);
            return "Fournisseur supprimé";
        } else
            return "Fournisseur non supprimé";
    }
}
