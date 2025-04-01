package com.example.ressourcemanagement.DAO;

import com.example.ressourcemanagement.Models.Commande;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



@Repository
public interface CommandeRepository extends JpaRepository<Commande,Integer>  {
    // Ajout de la méthode findAll avec pagination
    Page<Commande> findAll(Pageable pageable);
}
