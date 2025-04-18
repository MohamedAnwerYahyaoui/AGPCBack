package com.example.employee.Controllers;
import com.example.employee.Entities.Congee;
import com.example.employee.Entities.Contrat;
import com.example.employee.Repositories.CongeeRepositoriy;
import com.example.employee.Repositories.ContratRepositoriy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final ContratRepositoriy contratRepositoriy;
    private final CongeeRepositoriy congeeRepositoriy;

    public NotificationController(ContratRepositoriy contratRepositoriy, CongeeRepositoriy congeeRepositoriy) {
        this.contratRepositoriy = contratRepositoriy;
        this.congeeRepositoriy = congeeRepositoriy;
    }
    @GetMapping("/contracts")
    public List<Contrat> getContracts() {
        List<Contrat> contracts = contratRepositoriy.findAll();
        System.out.println("Contracts récupérés : " + contracts);
        return contracts;
    }

    @GetMapping("/leaves")
    public List<Congee> getLeaves() {
        List<Congee> leaves = congeeRepositoriy.findAll();
        System.out.println("Leaves récupérés : " + leaves);
        return leaves;
    }




}
