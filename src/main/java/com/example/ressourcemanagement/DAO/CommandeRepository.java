package com.example.ressourcemanagement.DAO;

import com.example.ressourcemanagement.Models.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeRepository extends JpaRepository<Commande,Integer>  {
}
