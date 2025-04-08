package io.tutorial.spring.document.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    public void envoyerEmail(String destinataire, String sujet, String message) {
        try {
            logger.info("Tentative d'envoi d'email à : {}", destinataire); // Log avant envoi
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(destinataire);
            email.setSubject(sujet);
            email.setText(message);

            mailSender.send(email);
            logger.info("✅ Email envoyé avec succès à {}", destinataire); // Log après envoi
        } catch (Exception e) {
            logger.error("❌ Erreur lors de l'envoi de l'email : {}", e.getMessage());
        }
    }
}
