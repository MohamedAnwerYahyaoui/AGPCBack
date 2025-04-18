package com.example.employee.Repositories;
import com.example.employee.Entities.Contrat;
import com.example.employee.Services.ContratService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratRepositoriy extends JpaRepository<Contrat,Long> {
    Page<Contrat> findAll(Specification<Contrat> spec, Pageable pageable);

}
