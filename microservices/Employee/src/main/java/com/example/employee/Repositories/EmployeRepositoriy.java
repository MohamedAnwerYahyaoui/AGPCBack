package com.example.employee.Repositories;
import com.example.employee.Entities.Employe;
import com.example.employee.Entities.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeRepositoriy extends JpaRepository<Employe,Long> , JpaSpecificationExecutor<Employe> {
    Employe findByNom(String nom);
    List<Employe> findByEquipeId(Long equipeId);

}
