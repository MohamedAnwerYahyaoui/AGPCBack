package com.example.employee.Entities;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class EmployeSpecification {
    public static Specification<Employe> advancedSearch(
            String searchTerm,
            Long contratId,
            Long equipeId
    ) {
        return (Root<Employe> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

            // On part d'un predicate "vrai" (AND de rien)
            Predicate predicate = cb.conjunction();

            // 1) Filtrage par searchTerm (insensible à la casse)
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                String lowerSearch = "%" + searchTerm.toLowerCase() + "%";

                // On fait un OR sur nom, prenom, poste, email
                Predicate orClause = cb.disjunction();
                orClause = cb.or(
                        orClause,
                        cb.like(cb.lower(root.get("nom")), lowerSearch),
                        cb.like(cb.lower(root.get("prenom")), lowerSearch),
                        cb.like(cb.lower(root.get("poste")), lowerSearch),
                        cb.like(cb.lower(root.get("email")), lowerSearch)
                );

                // On combine avec le predicate global (AND)
                predicate = cb.and(predicate, orClause);
            }

            // 2) Filtrage par contratId
            if (contratId != null) {
                // On joint sur la propriété "contrat"
                Join<Object, Object> contratJoin = root.join("contrat", JoinType.LEFT);
                predicate = cb.and(predicate,
                        cb.equal(contratJoin.get("id"), contratId)
                );
            }

            // 3) Filtrage par equipeId
            if (equipeId != null) {
                Join<Object, Object> equipeJoin = root.join("equipe", JoinType.LEFT);
                predicate = cb.and(predicate,
                        cb.equal(equipeJoin.get("id"), equipeId)
                );
            }

            return predicate;
        };
    }
}
