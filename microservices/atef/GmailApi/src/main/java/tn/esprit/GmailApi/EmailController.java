package tn.esprit.GmailApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class EmailController {




    private final EmailService emailService;

    public EmailController(EmailService emailService){
        this.emailService=emailService;
    }




    @PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request) {
        emailService.sendReservationEmail(request.getTo(), request.getSubject(), request.getText());
        return ResponseEntity.ok("Email envoyé avec succès");
    }



}
