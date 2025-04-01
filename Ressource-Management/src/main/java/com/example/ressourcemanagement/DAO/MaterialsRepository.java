package com.example.ressourcemanagement.DAO;

import com.example.ressourcemanagement.Models.Fournisseur;
import com.example.ressourcemanagement.Models.Materials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialsRepository  extends JpaRepository<Materials,Long> {
}
