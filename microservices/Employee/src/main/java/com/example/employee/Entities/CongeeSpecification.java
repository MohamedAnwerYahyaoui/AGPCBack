package com.example.employee.Entities;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import java.util.Date;


public class CongeeSpecification {

        public static Specification<Congee> advancedSearch(
                String searchTerm,
                String etat,
                Date start,
                Date end
        ) {
            return (Root<Congee> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {


                Predicate predicate = cb.conjunction(); // un "AND" qui part vrai

                // 1) Filtrage par searchTerm (sur "nom")
                if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                    String lowerSearch = "%" + searchTerm.trim().toLowerCase() + "%";
                    predicate = cb.and(predicate,
                            cb.like(cb.lower(root.get("nom")), lowerSearch)
                    );
                }

                // 2) Filtrage par état
                if (etat != null && !etat.trim().isEmpty()) {
                    predicate = cb.and(predicate,
                            cb.equal(root.get("etat"), etat)
                    );
                }

                // 3) Filtrage par dateDebut/dateFin
                //    On veut : dateDebut >= start ET dateFin <= end
                //    (si les deux bornes sont fournies)
                if (start != null && end != null) {
                    predicate = cb.and(
                            predicate,
                            cb.greaterThanOrEqualTo(root.get("dateDebut"), start),
                            cb.lessThanOrEqualTo(root.get("dateFin"), end)
                    );
                } else if (start != null) {
                    // seulement dateDebut >= start
                    predicate = cb.and(
                            predicate,
                            cb.greaterThanOrEqualTo(root.get("dateDebut"), start)
                    );
                } else if (end != null) {
                    // seulement dateFin <= end
                    predicate = cb.and(
                            predicate,
                            cb.lessThanOrEqualTo(root.get("dateFin"), end)
                    );
                }

                return predicate;
            };
        }

    }


