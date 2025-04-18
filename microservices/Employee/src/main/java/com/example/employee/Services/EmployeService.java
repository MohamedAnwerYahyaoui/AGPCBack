package com.example.employee.Services;
import com.example.employee.Entities.Contrat;
import com.example.employee.Entities.Employe;
import com.example.employee.Entities.EmployeSpecification;
import com.example.employee.Entities.Equipe;
import com.example.employee.Repositories.ContratRepositoriy;
import com.example.employee.Repositories.EmployeRepositoriy;
import com.example.employee.Repositories.EquipeRepositoriy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class EmployeService {

    private final EmployeRepositoriy employeRepository;
    private final EquipeRepositoriy equipeRepositoriy;
    private  final ContratRepositoriy contratRepositoriy;

    public EmployeService(EmployeRepositoriy employeRepository, EquipeRepositoriy equipeRepositoriy,ContratRepositoriy contratRepositoriy){

        this.employeRepository=employeRepository;
        this.equipeRepositoriy=equipeRepositoriy;
        this.contratRepositoriy=contratRepositoriy;
    }
    public List<Employe> getEmployesByEquipe(Long equipeId) {
        return employeRepository.findByEquipeId(equipeId);
    }
    // Méthode upsert pour gérer l'import (update ou insert)
    public void upsertEmployeDansEquipe(Employe employeData, Long equipeId) {
        if (employeData.getId() != null) {
            Optional<Employe> existingOpt = employeRepository.findById(employeData.getId());
            if (existingOpt.isPresent()) {
                Employe existing = existingOpt.get();
                existing.setNom(employeData.getNom());
                existing.setPoste(employeData.getPoste());
                employeRepository.save(existing);
                affecterEmployeAEquipe(existing.getId(), equipeId);
                return;
            }
        }
        // Si l'employé n'existe pas (ou ID null), on le crée
        Employe newEmp = new Employe();
        newEmp.setNom(employeData.getNom());
        newEmp.setPoste(employeData.getPoste());
        newEmp = employeRepository.save(newEmp);
        affecterEmployeAEquipe(newEmp.getId(), equipeId);
    }


    public Employe ajouterEmployeAvecContrat(Employe employe, Long contratId) {
        Contrat contrat = contratRepositoriy.findById(contratId)
                .orElseThrow(() -> new RuntimeException("Contrat non trouvé"));
        employe.setContrat(contrat);


        return employeRepository.save(employe);
    }


    public List<Employe> findAll() {

        return employeRepository.findAll();
    }


    public Employe updateEmploye(long id, Employe newEmployee) {
        if (employeRepository.findById(id).isPresent()) {
            Employe employe = employeRepository.findById(id).get();
            employe.setNom(newEmployee.getNom());
            employe.setPrenom(newEmployee.getPrenom());
            employe.setEmail(newEmployee.getEmail());
            employe.setTelephone(newEmployee.getTelephone());
            employe.setDateEmbauche(newEmployee.getDateEmbauche());
            employe.setPoste(newEmployee.getPoste());
            employe.setSalaire(newEmployee.getSalaire());
            if (newEmployee.getContrat() != null) {
                employe.setContrat(newEmployee.getContrat());
            }
            return employeRepository.save(employe);
        } else
            return null;
    }


    public String deleteEmploye(long id) {
        if (employeRepository.findById(id).isPresent()) {
            employeRepository.deleteById(id);
            return "Employee est  supprimé avec succés";
        } else
            return "Employee non supprimé";
    }
    public Employe getEmployeById(long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé avec l'id : " + id));
    }

        public Employe affecterEmployeAEquipe(Long employeId, Long equipeId) {
            Employe employe = employeRepository.findById(employeId)
                    .orElseThrow(() -> new RuntimeException("Employé non trouvé"));
            Equipe equipe = equipeRepositoriy.findById(equipeId)
                    .orElseThrow(() -> new RuntimeException("Équipe non trouvée"));
            employe.setEquipe(equipe);
            return employeRepository.save(employe);
        }
    public Page<Employe> getEmployes(
            String searchTerm,
            Long contratId,
            Long equipeId,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Specification<Employe> spec = EmployeSpecification.advancedSearch(searchTerm, contratId, equipeId);
        return employeRepository.findAll(spec, pageable);
    }
    public void upsertEmployee(String nom, String poste) {
        Employe emp = employeRepository.findByNom(nom);
        if (emp != null) {
            emp.setPoste(poste);
            employeRepository.save(emp);
        } else {
            Employe newEmp = new Employe();
            newEmp.setNom(nom);
            newEmp.setPoste(poste);
            employeRepository.save(newEmp);
        }
    }

}

