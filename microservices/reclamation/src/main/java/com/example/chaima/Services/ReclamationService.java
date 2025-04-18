package com.example.chaima.Services;

import com.example.chaima.Entities.Employee;
import com.example.chaima.Entities.Reclamation;
import com.example.chaima.Repositories.EmployeeRepository;
import com.example.chaima.Repositories.ReclamationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReclamationService {

    @Autowired
    private ReclamationRepository reclamationRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }

    public Optional<Reclamation> getReclamationById(Integer id) {
        return reclamationRepository.findById(id);
    }

    public Reclamation saveReclamation(Reclamation reclamation) {
        Reclamation savedReclamation = reclamationRepository.save(reclamation);

        Employee employee = employeeRepository.findById(reclamation.getEmployee().getId()).get();
        String email = employee.getEmail();
        String subject = "New Reclamation";
        String body = "Dear " + employee.getName() + ",\n\n"
                + "A new reclamation has been created.\n"
                + "Description: " + savedReclamation.getComment() + "\n\n"
                + "Best Regards,\n"
                + "Reclamation Team";
        emailService.sendEmail(email, subject, body);

        return savedReclamation;
    }

    public void deleteReclamation(Integer id) {
        reclamationRepository.deleteById(id);
    }

    public List<Reclamation> getUserReclamations(Integer userId) {
        return reclamationRepository.findByEmployeeId(userId);
    }
}
