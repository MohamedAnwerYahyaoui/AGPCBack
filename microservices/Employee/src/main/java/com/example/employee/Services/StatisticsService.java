package com.example.employee.Services;
import com.example.employee.Entities.Congee;
import com.example.employee.Entities.Contrat;
import com.example.employee.Enum.Etat;
import com.example.employee.Enum.TypeContrat;
import com.example.employee.Repositories.CongeeRepositoriy;
import com.example.employee.Repositories.ContratRepositoriy;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class StatisticsService {
    private  final ContratRepositoriy contratRepositoriy;
    private  final CongeeRepositoriy congeeRepositoriy;
    public StatisticsService(ContratRepositoriy contratRepository, CongeeRepositoriy congeeRepositoriy){

        this.contratRepositoriy=contratRepository;
        this.congeeRepositoriy=congeeRepositoriy;
    }
    public Map<TypeContrat, Long> countContratsByType() {
        List<Contrat> allContrats = contratRepositoriy.findAll();
        return allContrats.stream()
                // On ignore les contrats dont getContrat() est null
                .filter(contrat -> contrat.getContrat() != null)
                .collect(Collectors.groupingBy(Contrat::getContrat, Collectors.counting()));
    }
    /**
     * Retourne un Map (CongeStatus -> nombre de congés)
     */
    public Map<Etat, Long> countCongesByStatus() {
        List<Congee> allConges = congeeRepositoriy.findAll();
        return allConges.stream()
                .filter(conge -> conge.getEtat() != null)
                .collect(Collectors.groupingBy(Congee::getEtat, Collectors.counting()));
    }
}
