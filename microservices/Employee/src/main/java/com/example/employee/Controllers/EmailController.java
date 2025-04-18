package com.example.employee.Controllers;
import com.example.employee.Entities.Email;
import com.example.employee.Services.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/emails")
public class EmailController {


  private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody Email email) {
        emailService.sendSimpleEmail(email.getTo(), email.getSubject(), email.getBody());
        return ResponseEntity.ok("Email sent successfully!");
    }
}
