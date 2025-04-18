package com.example.employee.Repositories;
import com.example.employee.Entities.Livrable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LivrableRepositoriy extends JpaRepository<Livrable,Long> , JpaSpecificationExecutor<Livrable> {
}
