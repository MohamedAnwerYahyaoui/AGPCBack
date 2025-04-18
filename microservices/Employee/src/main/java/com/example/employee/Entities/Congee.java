package com.example.employee.Entities;
import com.example.employee.Enum.Etat;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Entity
public class Congee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private Date dateDebut;
    private Date dateFin;
    @Enumerated(EnumType.STRING)
    private Etat etat;
    @Enumerated(EnumType.STRING)

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employe employe;

    public Congee(String nom, Date dateDebut, Date dateFin, Etat etat) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.etat = etat;
    }

    public Congee() {
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
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

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }


    /**
     * Calcule le nombre de jours restants par rapport à la date du jour.
     * Si la date du jour est après dateFin, on renvoie 0.
     */
    public long getRemainingDays() {
        LocalDate end = dateFin.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate today = LocalDate.now();

        if (!today.isBefore(end)) {
            // today >= end => 0
            return 0;
        }

        long diff = ChronoUnit.DAYS.between(today, end);
        // Ajouter +1 pour inclure la journée en cours
        return diff + 1;
    }


}
