package io.tutorial.spring.document.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "document")
public class Document {
    @Id
    @GeneratedValue
    private Long id;
    private String nom;

    private LocalDateTime dateCreation = LocalDateTime.now();
    private String cheminFichier;
    @Enumerated(EnumType.STRING)
    private TypeDocument typeD;
    @OneToOne(mappedBy = "document", cascade = CascadeType.ALL)
    @JsonIgnore
    private Notification notification;
    @ManyToOne(cascade = CascadeType.PERSIST)  // Autres options possibles comme REMOVE
    @JoinColumn(name = "assurance_id")
    @JsonBackReference
    private Assurance assurance;

    public Document() {
    }




    public Document(String nom, TypeDocument typeD, String cheminFichier, LocalDateTime dateCreation, Notification notification, Long id, Assurance assurance) {
        this.nom = nom;

        this.dateCreation = dateCreation;
        this.notification = notification;
        this.cheminFichier = cheminFichier;
        this.typeD = typeD;
        this.assurance = assurance;



    }

    public Long getId() {
        return id;
    }

    public TypeDocument getTypeD() {
        return typeD;
    }

    public void setTypeD(TypeDocument typeD) {
        this.typeD = typeD;
    }

    public String getCheminFichier() {
        return cheminFichier;
    }

    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
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




    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
    public Assurance getAssurance() {
        return assurance;
    }

    public void setAssurance(Assurance assurance) {
        this.assurance = assurance;
    }

}
