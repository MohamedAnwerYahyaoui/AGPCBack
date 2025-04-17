package com.example.chaima.Controllers;

import com.example.chaima.Entities.Reclamation;
import com.example.chaima.Services.ReclamationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reclamations")
@CrossOrigin(origins = "http://localhost:4200")
public class ReclamationController {

    @Autowired
    private ReclamationService reclamationService;

    @GetMapping
    public List<Reclamation> getAllReclamations() {
        return reclamationService.getAllReclamations();
    }

    @GetMapping("/user")
    public List<Reclamation> getUserReclamations(@RequestParam Integer userId) {
        return reclamationService.getUserReclamations(userId);
    }

    @GetMapping("/{id}")
    public Optional<Reclamation> getReclamationById(@PathVariable Integer id) {
        return reclamationService.getReclamationById(id);
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Integer id) {
        Optional<Reclamation> reclamation = reclamationService.getReclamationById(id);
        if (reclamation.isPresent() && reclamation.get().getFile() != null) {
            String fileName = reclamation.get().getFileName();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                    .body(reclamation.get().getFile());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createReclamation(
            @RequestPart("reclamation") Reclamation reclamation,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {

        if (file != null && !file.isEmpty()) {
            reclamation.setFile(file.getBytes());
            reclamation.setFileName(file.getOriginalFilename());
        }

        try {
            Reclamation saved = reclamationService.saveReclamation(reclamation);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping
    public Reclamation updateReclamation(@RequestBody Reclamation reclamation) {
        return reclamationService.saveReclamation(reclamation);
    }

    @DeleteMapping("/{id}")
    public void deleteReclamation(@PathVariable String id) {
        try {
            Integer reclamationId = Integer.parseInt(id);
            reclamationService.deleteReclamation(reclamationId);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid reclamation ID: " + id);
        }
    }
}

