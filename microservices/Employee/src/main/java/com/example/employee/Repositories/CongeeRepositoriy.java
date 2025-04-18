package com.example.employee.Repositories;
import com.example.employee.Entities.Congee;
import com.example.employee.Entities.Employe;
import com.example.employee.Enum.Etat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CongeeRepositoriy extends JpaRepository<Congee,Long> , JpaSpecificationExecutor<Congee> {
    List<Congee> findByEtat(Etat etat);
   List<Congee> findByNom(String nom);


}
