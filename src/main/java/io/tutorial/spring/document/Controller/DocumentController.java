package io.tutorial.spring.document.Controller;

import io.tutorial.spring.document.Repository.AssuranceRepository; // Add this import
import io.tutorial.spring.document.Repository.DocumentRepository;
import io.tutorial.spring.document.Repository.NotificationRepository;
import io.tutorial.spring.document.Service.DocumentService;
import io.tutorial.spring.document.entity.Assurance;
import io.tutorial.spring.document.entity.Document;
import io.tutorial.spring.document.entity.Notification;
import io.tutorial.spring.document.entity.TypeDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/document")
public class DocumentController {
    private final DocumentService documentService;
    private final NotificationRepository notificationRepository;
    private final AssuranceRepository assuranceRepository; // Add this field

    @Autowired
    public DocumentController(DocumentService documentService, NotificationRepository notificationRepository, AssuranceRepository assuranceRepository) {
        this.documentService = documentService;
        this.notificationRepository = notificationRepository;
        this.assuranceRepository = assuranceRepository; // Inject the repository
    }

    @PostMapping("/adddocument")
    public ResponseEntity<?> addDocument(@RequestBody Document document) {
        try {
            Document savedDocument = documentService.addDocument(document);
            return ResponseEntity.ok(savedDocument);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur interne : " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Page<Document>> getAllDocuments(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Page<Document> documents = documentService.getAllDocuments(page, size);
        return ResponseEntity.ok(documents);
    }

    @PostMapping("/adddocumentwithfile")
    public ResponseEntity<?> addDocumentWithFile(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("nom") String nom,
                                                 @RequestParam("type") String type,
                                                 @RequestParam("assuranceId") Long assuranceId) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Le fichier est vide.");
        }
        try {
            Document document = new Document();
            document.setNom(nom);
            try {
                document.setTypeD(TypeDocument.valueOf(type.trim().toUpperCase()));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("TypeDocument invalide : " + type);
            }

            // Fetch the Assurance entity from the database
            Assurance assurance = assuranceRepository.findById(assuranceId)
                    .orElseThrow(() -> new RuntimeException("Assurance with ID " + assuranceId + " not found"));
            document.setAssurance(assurance);

            Document savedDocument = documentService.addDocumentWithFile(document, file);
            return ResponseEntity.ok(savedDocument);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erreur lors de l'enregistrement du fichier : " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur interne : " + e.getMessage());
        }
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return ResponseEntity.ok(notifications);
    }

    @PutMapping("/updateDocument/{id}")
    public ResponseEntity<?> updateDocument(@PathVariable Long id, @RequestBody Document document) {
        try {
            document.setId(id);
            Document updatedDocument = documentService.updateDocument(document);
            return ResponseEntity.ok(updatedDocument);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la mise à jour du document : " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteDocument/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable Long id) {
        try {
            documentService.deleteDocument(id);
            return ResponseEntity.ok("Document supprimé avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la suppression du document : " + e.getMessage());
        }
    }
}