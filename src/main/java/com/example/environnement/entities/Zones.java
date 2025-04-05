package com.example.environnement.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Zones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String Description;

    @ManyToOne
    private Chantier chantier;

    @ManyToOne
    private Users user;

    public Zones() {

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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Zones(Long id, String nom, String description, Chantier chantier, Users user) {
        this.id = id;
        this.nom = nom;
        Description = description;
        this.chantier = chantier;
        this.user = user;
    }
}

