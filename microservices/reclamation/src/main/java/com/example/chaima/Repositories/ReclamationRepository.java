package com.example.chaima.Repositories;

import com.example.chaima.Entities.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation, Integer> {
    List<Reclamation> findByEmployeeId(Integer employeeId);
}