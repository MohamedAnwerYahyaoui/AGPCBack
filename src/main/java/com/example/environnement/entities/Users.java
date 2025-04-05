package com.example.environnement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Users {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    @JsonIgnore

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Zones> zones ;

    public Users() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Users(Long id, String nom, List<Zones> zones) {
        this.id = id;
        this.nom = nom;
        this.zones = zones;
    }
}
