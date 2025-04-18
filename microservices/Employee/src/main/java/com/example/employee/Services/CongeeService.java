package com.example.employee.Services;
import com.example.employee.Entities.Congee;
import com.example.employee.Entities.CongeeSpecification;
import com.example.employee.Entities.Employe;
import com.example.employee.Enum.Etat;
import com.example.employee.Repositories.CongeeRepositoriy;
import com.example.employee.Repositories.EmployeRepositoriy;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class CongeeService {

    private final CongeeRepositoriy congeeRepositoriy;
    private  final EmployeRepositoriy employeRepositoriy;
    private  final EmailService emailService;


    public CongeeService(CongeeRepositoriy congeeRepository,EmployeRepositoriy employeRepositoriy,EmailService emailService) {

        this.congeeRepositoriy=congeeRepository;
        this.employeRepositoriy=employeRepositoriy;
        this.emailService=emailService;

    }
    @Transactional
    public Congee addCongeeToEmployee(Long employeeId, Congee congee) {
        Employe employee = employeRepositoriy.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));
        congee.setEmploye(employee);

        return congeeRepositoriy.save(congee);
    }
    //Consulter des Congées par Etat

    public List<Congee> getCongesByEtat(Etat etat) {
        return congeeRepositoriy.findByEtat(etat);
    }


    public List<Congee> findAll() {

        return congeeRepositoriy.findAll();
    }
    public Congee getCongeeById(Long id) {
        return congeeRepositoriy.findById(id).orElse(null);
    }


    public Congee updateCongee(long id, Congee newCongee) {
        if (congeeRepositoriy.findById(id).isPresent()) {
            Congee congee = congeeRepositoriy.findById(id).get();
            congee.setNom(newCongee.getNom());
            congee.setDateDebut(newCongee.getDateDebut());
            congee.setDateFin(newCongee.getDateFin());
            congee.setEtat(newCongee.getEtat());
            return congeeRepositoriy.save(congee);
        } else
            return null;
    }


    public String deleteCongee(long id) {
        if (congeeRepositoriy.findById(id).isPresent()) {
            congeeRepositoriy.deleteById(id);
            return "Congee est  supprimé avec succés";
        } else
            return "Congee non supprimé";
    }

    public Page<Congee> getCongees(
            String searchTerm,
            String etat,
            Date start,
            Date end,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Specification<Congee> spec = CongeeSpecification.advancedSearch(searchTerm, etat, start, end);
        return congeeRepositoriy.findAll(spec, pageable);
    }

    /**
     * Calcule le nombre de jours restants, envoie un email si < 5.
     */
    public long checkRemainingDaysByNom(String nomCongee) {
        List<Congee> congees = congeeRepositoriy.findByNom(nomCongee);

        if (congees.isEmpty()) {
            throw new RuntimeException("Congee introuvable (nom=" + nomCongee + ")");
        }

        // Filtrer seulement les congés dont la date de fin est dans le futur
        LocalDate today = LocalDate.now();
        Optional<Congee> congeeOpt = congees.stream()
                .filter(c -> c.getDateFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(today))
                .findFirst();

        if (congeeOpt.isEmpty()) {
            throw new RuntimeException("Aucun congé futur trouvé pour (nom=" + nomCongee + ")");
        }

        Congee congee = congeeOpt.get();
        long remaining = congee.getRemainingDays();

        if (remaining < 5) {
            String email = congee.getEmploye().getEmail();
            emailService.sendSimpleEmail(
                    email,
                    "Alerte congé",
                    "Bonjour,\nIl ne vous reste que " + remaining + " jours de congé pour '" + nomCongee + "'."
            );
        }
        return remaining;
    }


}


