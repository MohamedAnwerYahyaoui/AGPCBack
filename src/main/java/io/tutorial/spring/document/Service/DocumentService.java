package io.tutorial.spring.document.Service;

import io.tutorial.spring.document.Repository.DocumentRepository;
import io.tutorial.spring.document.Repository.NotificationRepository;
import io.tutorial.spring.document.entity.Document;
import io.tutorial.spring.document.entity.Notification;
import io.tutorial.spring.document.entity.TypeDocument;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final NotificationRepository notificationRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository, NotificationRepository notificationRepository) {
        this.documentRepository = documentRepository;
        this.notificationRepository = notificationRepository;
    }

    public Document addDocument(Document document) {
        return documentRepository.save(document);
    }

    public Document addDocumentWithFile(Document document, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get("uploads");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String fileName = file.getOriginalFilename();
        Path path = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        document.setCheminFichier(path.toString());
        return documentRepository.save(document);
    }

    public Document updateDocument(Document document) {
        return documentRepository.save(document);
    }

    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }

    public Page<Document> getAllDocuments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return documentRepository.findAll(pageable);
    }
}
