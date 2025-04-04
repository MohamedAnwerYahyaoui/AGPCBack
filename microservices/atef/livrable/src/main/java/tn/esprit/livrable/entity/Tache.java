package tn.esprit.livrable.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateDebut;
   @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateFin;
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "livrable_id")
    private Livrable livrable;

    @OneToMany(mappedBy = "tache", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Timesheet> timesheets = new ArrayList<>();

    @ManyToOne
   @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

private Long iduser;
    public Tache(){}



    public Tache(String nom, String description, Date dateDebut, Date dateFin, Status status, Livrable livrable, List<Timesheet> timesheets, User user, Long iduser) {
        this.nom = nom;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.status = status;
        this.livrable = livrable;
        this.timesheets = timesheets;
        this.user=user;
        this.iduser=iduser;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Livrable getLivrable() {
        return livrable;
    }

    public void setLivrable(Livrable livrable) {
        this.livrable = livrable;
    }

    public List<Timesheet> getTimesheets() {
        return timesheets;
    }

    public void setTimesheets(List<Timesheet> timesheets) {
        this.timesheets = timesheets;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getIduser() {
        return iduser;
    }

    public void setIduser(Long iduser) {
        this.iduser = iduser;
    }
}
