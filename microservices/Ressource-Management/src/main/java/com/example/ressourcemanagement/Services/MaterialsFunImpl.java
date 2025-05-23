package com.example.ressourcemanagement.Services;

import com.example.ressourcemanagement.DAO.MaterialsRepository;
import com.example.ressourcemanagement.Models.Categorie;
import com.example.ressourcemanagement.Models.Materials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MaterialsFunImpl {

    private final MaterialsRepository mr;

    public MaterialsFunImpl(MaterialsRepository mr) {
        this.mr = mr;
        log.info("Repository MaterialsRepository injecté : {}", mr);
    }

    public Materials ajouterMaterials(Materials materials) {
        return mr.save(materials);
    }

    public List<Materials> findAll() {
        log.debug("Récupération de tous les matériaux");
        List<Materials> materials = mr.findAll();
        log.debug("Nombre de matériaux récupérés depuis la base de données : {}", materials.size());
        log.debug("Matériaux récupérés depuis la base de données : {}", materials);
        return materials;
    }

    public Materials findById(long id) {
        return mr.findById(id)
                .orElseThrow(() -> new RuntimeException("Material non trouvé avec ID : " + id));
    }

    public Materials updateMaterials(long id, Materials newMaterials) {
        return mr.findById(id)
                .map(materials -> {
                    materials.setName(newMaterials.getName());
                    materials.setQuantity(newMaterials.getQuantity());
                    materials.setUnitPrice(newMaterials.getUnitPrice());
                    materials.setCategorie(newMaterials.getCategorie());
                    return mr.save(materials);
                })
                .orElse(null);
    }

    public boolean deleteMaterials(long id) {
        if (mr.findById(id).isPresent()) {
            mr.deleteById(id);
            return true;
        }
        return false;
    }
}