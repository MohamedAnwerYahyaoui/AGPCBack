package com.example.employee.Entities;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class LivrableSpecification {

    public static Specification<Livrable> searchByNom(String searchTerm) {
        return (Root<Livrable> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                return cb.conjunction();
            }
            String lowerSearch = "%" + searchTerm.toLowerCase() + "%";
            return cb.like(cb.lower(root.get("nom")), lowerSearch);
        };
    }
}
