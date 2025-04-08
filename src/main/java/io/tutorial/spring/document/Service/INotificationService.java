package io.tutorial.spring.document.Service;

import io.tutorial.spring.document.entity.Document;

public interface INotificationService {
    void envoyerNotification(String message, Document document);

}
