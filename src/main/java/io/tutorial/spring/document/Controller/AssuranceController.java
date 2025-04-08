package io.tutorial.spring.document.Controller;

import io.tutorial.spring.document.Service.AssuranceService;
import io.tutorial.spring.document.entity.Assurance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.Optional;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/assurance")
public class AssuranceController {
    private final AssuranceService assuranceService;
    private static final Logger logger = LoggerFactory.getLogger(AssuranceController.class);

    public AssuranceController(AssuranceService assuranceService) {
        this.assuranceService = assuranceService;
    }

    @PostMapping("/add")
    public ResponseEntity<Assurance> ajouterAssurance(@RequestBody Assurance assurance) {
        Assurance nouvelleAssurance = assuranceService.ajouterAssurance(assurance);
        return ResponseEntity.ok(nouvelleAssurance);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<Assurance>> obtenirAssurancesAvecPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        try {
            Page<Assurance> assurances = assuranceService.obtenirAssurancesAvecPagination(page, size);
            return ResponseEntity.ok(assurances);
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des assurances : {}", e.getMessage());
            return ResponseEntity.status(500).body(Page.empty());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assurance> obtenirAssuranceParId(@PathVariable Long id) {
        Optional<Assurance> assurance = assuranceService.obtenirAssuranceParId(id);
        return assurance.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Assurance> updateAssurance(@PathVariable(value = "id") long id,
                                                     @RequestBody Assurance assurance) {
        Assurance updatedAssurance = assuranceService.mettreAJourAssurance(id, assurance);
        return ResponseEntity.ok(updatedAssurance);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> supprimerAssurance(@PathVariable Long id) {
        try {
            assuranceService.supprimerAssurance(id);
            return ResponseEntity.ok("Assurance supprimée avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    @GetMapping(value = "/generate-pdf/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePDF(@PathVariable Long id) {
        try {
            Optional<Assurance> assuranceOpt = assuranceService.obtenirAssuranceParId(id);
            if (!assuranceOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            Assurance assurance = assuranceOpt.get();

            // Créer un PDF avec iText 7
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Ajouter du contenu au PDF
            document.add(new Paragraph("Attestation d'assurance"));
            document.add(new Paragraph("ID: " + assurance.getId()));
            document.add(new Paragraph("Nom: " + assurance.getNom()));
            document.add(new Paragraph("Type: " + assurance.getTypeAssurance()));
            document.add(new Paragraph("Date d'expiration: " + assurance.getDateExpiration()));
            document.add(new Paragraph("Montant de couverture: " + assurance.getMontantCouverture() + " EUR"));

            // Fermer le document
            document.close();

            byte[] pdfBytes = baos.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "Attestation_" + id + ".pdf");
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Erreur lors de la génération du PDF : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}