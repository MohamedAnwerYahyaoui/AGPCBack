package com.example.employee.Services;
import com.example.employee.Entities.Contrat;
import com.example.employee.Entities.ContratSpecification;
import com.example.employee.Repositories.ContratRepositoriy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;


@Service
public class ContratService {
    private  final ContratRepositoriy contratRepositoriy;

    public ContratService(ContratRepositoriy contratRepository){

        this.contratRepositoriy=contratRepository;
    }

    public Contrat addContrat(Contrat contrat) {

        return contratRepositoriy.save(contrat);
    }


    public List<Contrat> findAll() {

        return contratRepositoriy.findAll();
    }

    public Contrat getContratById(Long id) {
        return contratRepositoriy.findById(id).orElse(null);
    }
    public Contrat updateContrat(long id, Contrat newContrat) {
        if (contratRepositoriy.findById(id).isPresent()) {
            Contrat contrat = contratRepositoriy.findById(id).get();
            contrat.setDateDebut(newContrat.getDateDebut());
            contrat.setDateFin(newContrat.getDateFin());
            contrat.setDateDebut(newContrat.getDateDebut());
            contrat.setContrat(newContrat.getContrat());
            return contratRepositoriy.save(contrat);
        } else
            return null;
    }


    public String deleteContrat(long id) {
        if (contratRepositoriy.findById(id).isPresent()) {
            contratRepositoriy.deleteById(id);
            return "Contrat est  supprimé avec succés";
        } else
            return "Contrat non supprimé";
    }
    public Page<Contrat> findContrats(String searchText,
                                      String selectedType,
                                      LocalDate startDate,
                                      LocalDate endDate,
                                      Pageable pageable) {
        Specification<Contrat> spec = Specification.where(ContratSpecification.hasSearchText(searchText))
                .and(ContratSpecification.hasSelectedType(selectedType))
                .and(ContratSpecification.inDateRange(startDate, endDate));

        return contratRepositoriy.findAll(spec, pageable);
    }
}
