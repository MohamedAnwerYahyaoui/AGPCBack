package com.example.employee.Entities;

import com.example.employee.Enum.Etat;
import com.example.employee.Enum.TypeContrat;

import java.util.Map;

public class Statistique {
    private Map<TypeContrat, Long> countByType;

    // Congés : status -> count
    private Map<Etat, Long> countByStatus;

    public Statistique(Map<TypeContrat, Long> countByType, Map<Etat, Long> countByStatus) {
        this.countByType = countByType;
        this.countByStatus = countByStatus;
    }

    public Statistique() {
    }

    public Map<TypeContrat, Long> getCountByType() {
        return countByType;
    }

    public void setCountByType(Map<TypeContrat, Long> countByType) {
        this.countByType = countByType;
    }

    public Map<Etat, Long> getCountByStatus() {
        return countByStatus;
    }

    public void setCountByStatus(Map<Etat, Long> countByStatus) {
        this.countByStatus = countByStatus;
    }
}
