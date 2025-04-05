package com.example.formations.repositories;

import com.example.formations.entities.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormationRepository extends JpaRepository<Formation,Long> {
}
