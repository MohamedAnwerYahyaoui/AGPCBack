package io.tutorial.spring.document.Controller;

import io.tutorial.spring.document.Service.NotificationService;
import io.tutorial.spring.document.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    // 1. Obtenir toutes les notifications
    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }



    // 2. Obtenir les notifications non lues
    @GetMapping("/non-lues")
    public List<Notification> getNotificationsNonLues() {
        return notificationService.getNotificationsNonLues();
    }

    // 3. Mettre à jour une notification (ex. marquer comme lue)
    @PutMapping("/{id}")
    public Notification mettreAJourNotification(
            @PathVariable Long id,
            @RequestBody Notification notification
    ) {
        return notificationService.mettreAJourNotification(
                id,
                notification.getMessage(),
                notification.getLue()
        );
    }

    // 4. Supprimer une notification
    @DeleteMapping("/{id}")
    public String supprimerNotification(@PathVariable Long id) {
        notificationService.supprimerNotification(id);
        return "Notification supprimée avec succès.";
    }
}

