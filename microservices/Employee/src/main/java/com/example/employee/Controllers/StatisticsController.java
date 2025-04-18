package com.example.employee.Controllers;
import com.example.employee.Entities.Statistique;
import com.example.employee.Services.StatisticsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    // Injection par constructeur (pas de @Autowired)
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    // Retourne un objet Statistique (les stats de contrats & congés)
    @GetMapping
    public Statistique getStatistics() {
        return new Statistique(
                statisticsService.countContratsByType(),
                statisticsService.countCongesByStatus()
        );
    }
}
