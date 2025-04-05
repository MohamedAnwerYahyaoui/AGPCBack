package com.example.environnement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
@Entity
public class Chantier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;
    private String nom;
@JsonIgnore
    @OneToMany(mappedBy = "chantier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Zones>zones;

    public Chantier() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Zones> getZones() {
        return zones;
    }

    public void setZones(List<Zones> zones) {
        this.zones = zones;
    }

    public Chantier(Long id, String location, String nom, List<Zones> zones) {
        this.id = id;
        this.location = location;
        this.nom = nom;
        this.zones = zones;
    }
}
