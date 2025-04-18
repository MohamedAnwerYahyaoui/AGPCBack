package com.example.employee.Entities;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ContratSpecification {


        public static Specification<Contrat> hasSearchText(String searchText) {
            return (root, query, cb) -> {
                if (searchText == null || searchText.trim().isEmpty()) {
                    return cb.conjunction(); // Pas de filtre
                }
                String lower = searchText.toLowerCase();
                return cb.like(cb.lower(root.get("contrat")), "%" + lower + "%");
            };
        }


        public static Specification<Contrat> hasSelectedType(String selectedType) {
            return (root, query, cb) -> {
                if (selectedType == null || selectedType.trim().isEmpty()) {
                    return cb.conjunction();
                }
                return cb.equal(root.get("contrat"), selectedType);
            };
        }


        public static Specification<Contrat> inDateRange(LocalDate startDate, LocalDate endDate) {
            return (root, query, cb) -> {
                // Si aucune date n’est spécifiée, on ne filtre pas.
                if (startDate == null && endDate == null) {
                    return cb.conjunction();
                }

                Predicate p = cb.conjunction();

                // dateDebut >= startDate
                if (startDate != null) {
                    p = cb.and(p, cb.greaterThanOrEqualTo(root.get("dateDebut"), startDate.atStartOfDay()));
                }

                // dateFin <= endDate
                if (endDate != null) {
                    // endDate.atTime(23,59,59) si vous voulez inclure toute la journée
                    p = cb.and(p, cb.lessThanOrEqualTo(root.get("dateFin"), endDate.atTime(23, 59, 59)));
                }

                return p;
            };
        }
    }


