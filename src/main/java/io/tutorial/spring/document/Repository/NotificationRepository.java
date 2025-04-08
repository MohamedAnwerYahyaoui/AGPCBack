package io.tutorial.spring.document.Repository;

import io.tutorial.spring.document.entity.Document;
import io.tutorial.spring.document.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    void deleteByDocumentId(Long documentId);
    Optional<Notification> findByDocumentId(Long documentId);
    List<Notification> findByLueFalse();


}
