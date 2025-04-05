package tn.esprit.livrable.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.livrable.DTO.GanttItemDTO;
import tn.esprit.livrable.Repository.LivrableRepo;
import tn.esprit.livrable.Repository.TacheRepo;
import tn.esprit.livrable.entity.Livrable;
import tn.esprit.livrable.entity.Status;
import tn.esprit.livrable.entity.Tache;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/gantt")

public class GanttController {



    private final TacheRepo tacheRepo;
    private final LivrableRepo livrableRepo;

    public GanttController(TacheRepo tacheRepo, LivrableRepo livrableRepo) {
        this.tacheRepo = tacheRepo;
        this.livrableRepo = livrableRepo;
    }

    @GetMapping("/data")
    public ResponseEntity<List<GanttItemDTO>> getGanttData() {
        List<Tache> taches = tacheRepo.findAll();
        List<GanttItemDTO> ganttData = new ArrayList<>();


        for (Tache tache : taches) {
            GanttItemDTO item = new GanttItemDTO();
            item.setId(tache.getId().toString());
            item.setText(tache.getNom());
            item.setStartDate(tache.getDateDebut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
            item.setEndDate(tache.getDateFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
            item.setProgress(tache.getStatus() == Status.DONE ? 1 : 0.5); // 1 pour terminé, 0.5 pour en cours
            item.setParent(tache.getLivrable() != null ? tache.getLivrable().getId().toString() : "0");

            ganttData.add(item);
        }


        List<Livrable> livrables = livrableRepo.findAll();
        for (Livrable livrable : livrables) {
            GanttItemDTO item = new GanttItemDTO();
            item.setId(livrable.getId().toString());
            item.setText(livrable.getNom());
            item.setStartDate(livrable.getDateLivraison().minusDays(30).toString()); // Exemple
            item.setEndDate(livrable.getDateLivraison().toString());
            item.setProgress(0.3);
            item.setParent("0");
            item.setType("project");

            ganttData.add(item);
        }

        return ResponseEntity.ok(ganttData);
    }
}
