package com.example.employee.Repositories;
import com.example.employee.Entities.Equipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipeRepositoriy extends JpaRepository<Equipe,Long> {
    Page<Equipe> findAll(Specification<Equipe> spec, Pageable pageable);
    @Query("SELECT e FROM Equipe e ORDER BY e.rating DESC")
    List<Equipe> findBestEquipe(Pageable pageable);

}
