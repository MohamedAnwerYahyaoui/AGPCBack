package com.example.employee.Entities;
import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private int nombreMembres;
    private String contactEquipe;
    private Date dateCreation;
    private double rating;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="equipe")
    private Set<Employe> Employes;

    @OneToOne
    @JoinColumn(name = "livrable_id", referencedColumnName = "id")
    private Livrable livrable;

    public Equipe(String nom, int nombreMembres, String contactEquipe, Date dateCreation,double rating) {
        this.nom = nom;
        this.nombreMembres = nombreMembres;
        this.contactEquipe = contactEquipe;
        this.dateCreation = dateCreation;
        this.rating = rating;
    }

    public Equipe() {
    }

    public Livrable getLivrable() {
        return livrable;
    }

    public void setLivrable(Livrable livrable) {
        this.livrable = livrable;
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

    public int getNombreMembres() {
        return nombreMembres;
    }

    public void setNombreMembres(int nombreMembres) {
        this.nombreMembres = nombreMembres;
    }

    public String getContactEquipe() {
        return contactEquipe;
    }

    public void setContactEquipe(String contactEquipe) {
        this.contactEquipe = contactEquipe;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
