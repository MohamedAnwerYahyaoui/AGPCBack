package com.example.ressourcemanagement.Services;

import com.example.ressourcemanagement.DAO.FournisseurRepository;
import com.example.ressourcemanagement.DAO.MaterialsRepository;
import com.example.ressourcemanagement.Models.Fournisseur;
import com.example.ressourcemanagement.Models.Materials;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class MaterialsFunImpl {
    private MaterialsRepository mr;

    public MaterialsFunImpl(MaterialsRepository mr){
        this.mr=mr;
    }



    public Materials ajouterMaterials(Materials materials ) {
        return mr.save(materials);
    }


    public List<Materials> findAll() {
        log.debug("Récupération de tous les materials");
        List<Materials> materials = mr.findAll();
        log.debug("Nombre de materials récupérés : {}",materials.size());
        return materials;
    }

    public Materials findById(long id) {
        return mr.findById(id)
                .orElseThrow(() -> new RuntimeException("material non trouvé avec ID : " + id));
    }

    public Materials updateMaterials(long id, Materials newMaterials) {
        if (mr.findById(id).isPresent()) {
            Materials materials = mr.findById(id).get();
            materials .setName(newMaterials .getName());
            materials .setQuantity(newMaterials .getQuantity());
            materials .setUnitPrice(newMaterials .getUnitPrice());
            materials .setCategorie(newMaterials .getCategorie());
            return mr.save(materials) ;
        } else
            return null;
    }


    public String deleteMaterials(long id) {
        if (mr.findById(id).isPresent()) {
            mr.deleteById(id);
            return "Matériel supprimé";
        } else
            return "Matériel non supprimé";
    }
}
