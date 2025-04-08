package io.tutorial.spring.document.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Assurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private LocalDate dateExpiration;
    private double montantCouverture;

//    @Version
//    private Integer version;

    @Enumerated(EnumType.STRING)
    private TypeAssurance typeAssurance;

    @OneToMany(mappedBy = "assurance", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Document> documents;

    // Constructeur par défaut
    public Assurance() {
       // this.version = 0;  // Assurez-vous que la version est initialisée à 0
    }

    // Constructeur avec paramètres
    public Assurance(List<Document> documents, double montantCouverture, TypeAssurance typeAssurance, LocalDate dateExpiration, String nom) {
        this.documents = documents;
        this.montantCouverture = montantCouverture;
        this.typeAssurance = typeAssurance;
        this.dateExpiration = dateExpiration;
        this.nom = nom;
        //this.version = 0;  // Initialisez la version ici
    }

    // Getters et setters
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

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public double getMontantCouverture() {
        return montantCouverture;
    }

    public void setMontantCouverture(double montantCouverture) {
        this.montantCouverture = montantCouverture;
    }

    public TypeAssurance getTypeAssurance() {
        return typeAssurance;
    }

    public void setTypeAssurance(TypeAssurance typeAssurance) {
        this.typeAssurance = typeAssurance;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

//    public Integer getVersion() {
//        return version;
//    }
//
//    public void setVersion(Integer version) {
//        this.version = version;
//    }
}
