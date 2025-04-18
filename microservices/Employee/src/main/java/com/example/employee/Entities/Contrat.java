package com.example.employee.Entities;
import com.example.employee.Enum.TypeContrat;
import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Contrat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateDebut;
    private Date dateFin;
    @Enumerated(EnumType.STRING)
    private TypeContrat contrat;

    public boolean isExpired() {
        return dateFin != null && dateFin.before(new Date());
    }



    public Contrat(Date dateDebut, Date dateFin, TypeContrat contrat) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.contrat = contrat;
    }

    public Contrat() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public TypeContrat getContrat() {
        return contrat;
    }

    public void setContrat(TypeContrat contrat) {
        this.contrat = contrat;
    }
}
