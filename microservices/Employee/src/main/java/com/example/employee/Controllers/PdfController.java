package com.example.employee.Controllers;
import com.example.employee.Entities.Contrat;
import com.example.employee.Repositories.ContratRepositoriy;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;


@RestController
@RequestMapping("/api/pdf")
public class PdfController {
    private final ContratRepositoriy contratRepositoriy;

    public PdfController(ContratRepositoriy contratRepositoriy) {
        this.contratRepositoriy = contratRepositoriy;
    }

    @GetMapping("/{id}")
    public void generatePdf(@PathVariable("id") Long id,
                            HttpServletResponse response)
            throws DocumentException, IOException {

        // 1. Récupérer le contrat depuis la BD via un repository ou un service
        Contrat contrat = contratRepositoriy.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrat introuvable pour l'ID : " + id));

        // 2. Configurer la réponse HTTP pour retourner un fichier PDF
        response.setContentType("application/pdf");
        // Nom dynamique du PDF : contrat_{id}.pdf
        response.setHeader("Content-Disposition", "attachment; filename=\"contrat_" + id + ".pdf\"");

        // 3. Création du document PDF (format A4, marges de 50)
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        OutputStream out = response.getOutputStream();
        PdfWriter.getInstance(document, out);

        document.open();

        // 4. Mise en forme : un titre
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE);
        Paragraph title = new Paragraph("Contrat de Travail", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Saut de ligne
        document.add(new Paragraph("\n"));

        // 5. Paragraphe d’introduction
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
        Paragraph intro = new Paragraph(
                "Ci-dessous figurent les informations essentielles relatives au contrat. " +
                        "Veuillez prendre connaissance de l'intégralité des clauses et signer ce document pour valider votre engagement.",
                normalFont
        );
        intro.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(intro);

        // Saut de ligne
        document.add(new Paragraph("\n"));

        // 6. Création d'un tableau à 2 colonnes pour afficher les attributs
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100); // Largeur du tableau : 100% de la page
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        float[] columnWidths = { 3f, 7f };
        table.setWidths(columnWidths);

        // 7. En-tête du tableau (ligne d'entête colorée)
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);

        PdfPCell headerCell1 = new PdfPCell(new Phrase("Attribut", headerFont));
        headerCell1.setBackgroundColor(BaseColor.DARK_GRAY);
        headerCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell1.setPadding(5);

        PdfPCell headerCell2 = new PdfPCell(new Phrase("Valeur", headerFont));
        headerCell2.setBackgroundColor(BaseColor.DARK_GRAY);
        headerCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell2.setPadding(5);

        table.addCell(headerCell1);
        table.addCell(headerCell2);

        // 8. Remplir les cellules avec les attributs du contrat
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateDebutStr = (contrat.getDateDebut() != null)
                ? sdf.format(contrat.getDateDebut())
                : "N/A";
        String dateFinStr = (contrat.getDateFin() != null)
                ? sdf.format(contrat.getDateFin())
                : "N/A";

        addRow(table, "Date de début", dateDebutStr, normalFont);
        addRow(table, "Date de fin",   dateFinStr,   normalFont);

        // Par exemple, si le champ du type de contrat est getContrat() ou getTypeContrat()
        addRow(table, "Type de contrat",
                (contrat.getContrat() != null) ? contrat.getContrat().name() : "N/A",
                normalFont);

        // 9. Ajouter le tableau au document
        document.add(table);

        // 10. Exemple de clause ou d’autres informations
        Paragraph clause = new Paragraph(
                "Clause de confidentialité : Le salarié s'engage à ne pas divulguer les informations sensibles " +
                        "de l'entreprise. L'employé s'engage à respecter les horaires de travail définis, ainsi que " +
                        "toutes les politiques internes, procédures et instructions émises par son supérieur hiérarchique.\n\n" +

                        "Ce paragraphe définit les attentes liées aux performances, à la ponctualité et à la communication " +
                        "des absences. En cas de non-respect, des sanctions disciplinaires peuvent être envisagées.",
                normalFont
        );
        clause.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(clause);

        // Saut de ligne
        document.add(new Paragraph("\n"));

        // 11. Zone de signature
        Paragraph signature = new Paragraph(
                "Signature de l'employé : ___________________________   Date : ____/____/________",
                normalFont
        );
        signature.setSpacingBefore(20);
        document.add(signature);

        // 12. Fermer le document
        document.close();
    }

    /**
     * Méthode utilitaire pour ajouter une ligne (clé, valeur) au tableau
     */
    private void addRow(PdfPTable table, String label, String value, Font font) {
        PdfPCell cellLabel = new PdfPCell(new Phrase(label, font));
        cellLabel.setBackgroundColor(new BaseColor(240, 240, 240)); // léger gris
        cellLabel.setPadding(5);
        table.addCell(cellLabel);

        PdfPCell cellValue = new PdfPCell(new Phrase(value, font));
        cellValue.setPadding(5);
        table.addCell(cellValue);
    }
}
