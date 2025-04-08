package io.tutorial.spring.document.Service;

import io.tutorial.spring.document.Repository.NotificationRepository;
import io.tutorial.spring.document.entity.Document;
import io.tutorial.spring.document.entity.Notification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService implements INotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void envoyerNotification(String message, Document document) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Le message de notification ne peut pas être vide.");
        }

        if (document == null) {
            throw new IllegalArgumentException("La notification doit être liée à un document.");
        }

        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setDocument(document);
        notificationRepository.save(notification);
    }
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
    public List<Notification> getNotificationsNonLues() {
        return notificationRepository.findByLueFalse();
    }
    public Notification mettreAJourNotification(Long id, String message, Boolean lue) {
        Optional<Notification> notificationOptional = notificationRepository.findById(id);

        if (notificationOptional.isPresent()) {
            Notification notification = notificationOptional.get();
            if (message != null && !message.trim().isEmpty()) {
                notification.setMessage(message);
            }
            if (lue != null) {
                notification.setLue(lue);
            }
            return notificationRepository.save(notification);
        } else {
            throw new RuntimeException("Notification non trouvée avec l'ID : " + id);
        }
    }
        public void supprimerNotification(Long id) {
            if (!notificationRepository.existsById(id)) {
                throw new RuntimeException("Notification non trouvée avec l'ID : " + id);
            }
            notificationRepository.deleteById(id);
        }
    }



