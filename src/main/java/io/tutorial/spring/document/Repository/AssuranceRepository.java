package io.tutorial.spring.document.Repository;

import io.tutorial.spring.document.entity.Assurance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssuranceRepository extends JpaRepository<Assurance, Long> {
    Page<Assurance> findAll(Pageable pageable);

}
