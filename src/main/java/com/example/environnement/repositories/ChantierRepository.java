package com.example.environnement.repositories;

import com.example.environnement.entities.Chantier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChantierRepository extends JpaRepository<Chantier,Long> {
}
