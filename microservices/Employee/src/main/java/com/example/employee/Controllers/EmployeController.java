package com.example.employee.Controllers;
import com.example.employee.Entities.Employe;
import com.example.employee.Services.EmployeService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/Employee")
public class EmployeController {

    private final EmployeService employeService;

    public EmployeController(EmployeService  employeService) {

        this.employeService = employeService;

    }
    /**
     * Exporter les employés d'une équipe sous forme d'Excel.
     * URL : GET /Employee/export/{equipeId}
     */
    @GetMapping("/export/{equipeId}")
    public ResponseEntity<ByteArrayResource> exportEmployeesByEquipe(@PathVariable Long equipeId) {
        List<Employe> employes = employeService.getEmployesByEquipe(equipeId);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employes");

        // En-tête
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Nom");
        headerRow.createCell(2).setCellValue("Poste");

        // Remplir les données
        int rowNum = 1;
        for (Employe employe : employes) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(employe.getId());
            row.createCell(1).setCellValue(employe.getNom() != null ? employe.getNom() : "");
            row.createCell(2).setCellValue(employe.getPoste() != null ? employe.getPoste() : "");
        }

        // Ajuster la taille des colonnes
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            workbook.write(out);
            workbook.close();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création du fichier Excel", e);
        }

        ByteArrayResource resource = new ByteArrayResource(out.toByteArray());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employes_equipe_" + equipeId + ".xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(out.size())
                .body(resource);
    }

    /**
     * Importer un fichier Excel et synchroniser les employés d'une équipe.
     * (Upsert : update/insert, et delete pour les employés absents du fichier)
     * URL : POST /Employee/import/{equipeId}
     */
    @PostMapping("/import/{equipeId}")
    public ResponseEntity<String> importEmployees(@RequestParam("file") MultipartFile file,
                                                  @PathVariable Long equipeId) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Veuillez sélectionner un fichier.");
        }

        List<Employe> excelEmployees = new ArrayList<>();
        Set<Long> excelIds = new HashSet<>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                // Ignorer l'en-tête
                if (row.getRowNum() == 0) continue;

                Employe employe = new Employe();
                // Gestion de l'ID (peut être absent pour un nouvel employé)
                if (row.getCell(0) != null) {
                    if (row.getCell(0).getCellType() == CellType.NUMERIC) {
                        long idValue = (long) row.getCell(0).getNumericCellValue();
                        employe.setId(idValue);
                        excelIds.add(idValue);
                    } else if (row.getCell(0).getCellType() == CellType.STRING) {
                        String idStr = row.getCell(0).getStringCellValue();
                        if (!idStr.trim().isEmpty()) {
                            try {
                                long idValue = Long.parseLong(idStr.trim());
                                employe.setId(idValue);
                                excelIds.add(idValue);
                            } catch (NumberFormatException e) {
                                // En cas d'échec, l'ID reste null (nouvel employé)
                            }
                        }
                    }
                }
                // Lecture du Nom et du Poste
                if (row.getCell(1) != null) {
                    employe.setNom(row.getCell(1).getStringCellValue());
                }
                if (row.getCell(2) != null) {
                    employe.setPoste(row.getCell(2).getStringCellValue());
                }
                excelEmployees.add(employe);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la lecture du fichier : " + e.getMessage());
        }

        // Pour chaque employé du fichier, effectuer un upsert (update ou insert)
        for (Employe employe : excelEmployees) {
            employeService.upsertEmployeDansEquipe(employe, equipeId);
        }

        // Récupérer les employés actuels de l'équipe dans la base
        List<Employe> dbEmployees = employeService.getEmployesByEquipe(equipeId);
        // Pour chaque employé en base qui n'est pas dans le fichier Excel, le supprimer
        for (Employe dbEmp : dbEmployees) {
            if (dbEmp.getId() != null && !excelIds.contains(dbEmp.getId())) {
                employeService.deleteEmploye(dbEmp.getId());
            }
        }

        return ResponseEntity.ok("Importation réussie !");
    }
    @GetMapping("/list")
    public ResponseEntity<List<Employe>> listEmployees(){
        return new ResponseEntity<>(employeService.findAll(), HttpStatus.OK);
    }
    @PostMapping("/ajouter/{contratId}")
    public ResponseEntity<Employe> ajouterEmployeAvecContrat(@RequestBody Employe employe, @PathVariable Long contratId) {
        Employe nouvelEmploye = employeService.ajouterEmployeAvecContrat(employe, contratId);
        return new ResponseEntity<>(nouvelEmploye, HttpStatus.CREATED);
    }


    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Employe> updateEmploye(@PathVariable(value = "id") long id,
                                                         @RequestBody Employe employe){
        return new ResponseEntity<>(employeService.updateEmploye(id, employe),
                HttpStatus.OK);
    }
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteEmploye(@PathVariable(value = "id") long id) {
        return new ResponseEntity<>(employeService.deleteEmploye(id), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Employe> getEmployeById(@PathVariable long id) {
        Employe employe = employeService.getEmployeById(id);
        return new ResponseEntity<>(employe, HttpStatus.OK);
    }
    @PostMapping("/affecterEquipe/{employeId}/{equipeId}")
    public ResponseEntity<Employe> affecterEmployeAEquipe(@PathVariable Long employeId, @PathVariable Long equipeId) {
        Employe employeMiseAJour = employeService.affecterEmployeAEquipe(employeId, equipeId);
        return new ResponseEntity<>(employeMiseAJour, HttpStatus.OK);
    }
    @GetMapping
    public Page<Employe> getEmployes(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) Long contratId,
            @RequestParam(required = false) Long equipeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return employeService.getEmployes(searchTerm, contratId, equipeId, page, size);
    }

}
