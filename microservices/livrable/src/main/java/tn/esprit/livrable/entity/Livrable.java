package tn.esprit.livrable.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "livrable")
public class Livrable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    private LocalDate dateLivraison;

    @OneToMany(mappedBy = "livrable", cascade = CascadeType.ALL)
    private List<Tache> taches = new ArrayList<>();









    public Livrable(){}






    public Livrable(String nom, String description, LocalDate dateLivraison, List<Tache> taches) {
        this.nom = nom;
        this.description = description;
        this.dateLivraison = dateLivraison;
        this.taches = taches;
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
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(LocalDate dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public List<Tache> getTaches() {
        return taches;
    }

    public void setTaches(List<Tache> taches) {
        this.taches = taches;
    }




}
