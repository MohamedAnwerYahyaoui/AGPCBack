package com.example.ressourcemanagement.DAO;

import com.example.ressourcemanagement.Models.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepository extends JpaRepository<Commande,Long>  {
}
