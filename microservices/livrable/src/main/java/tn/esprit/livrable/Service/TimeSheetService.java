package tn.esprit.livrable.Service;

import org.springframework.stereotype.Service;
import tn.esprit.livrable.Repository.TacheRepo;
import tn.esprit.livrable.Repository.TimeSheetRepo;
import tn.esprit.livrable.Repository.UserRepo;
import tn.esprit.livrable.entity.Tache;
import tn.esprit.livrable.entity.Timesheet;
import tn.esprit.livrable.entity.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TimeSheetService {

    private final TimeSheetRepo tsr;
    private final UserRepo ur;
    private final TacheRepo tr;

    public TimeSheetService(TimeSheetRepo tsr,UserRepo ur,TacheRepo tr){
        this.ur=ur;
        this.tr=tr;
        this.tsr=tsr;
    }




    public Timesheet ajouterTimeSheet(Timesheet timeSheet) {
        return tsr.save(timeSheet);
    }


    public List<Timesheet> findAll() {
        return tsr.findAll();
    }


    public Timesheet updateTimeSheet(long id, Timesheet newTimeSheet) {
        if (tsr.findById(id).isPresent()) {
            Timesheet timeSheet = tsr.findById(id).get();
            timeSheet.setDescription(newTimeSheet.getDescription());
            timeSheet.setDate(newTimeSheet.getDate());
            timeSheet.setHeures(newTimeSheet.getHeures());
            timeSheet.setTache(newTimeSheet.getTache());

            return tsr.save(timeSheet);
        } else
            return null;
    }


    public String deleteTimeSheet(long id) {
        if (tsr.findById(id).isPresent()) {
            tsr.deleteById(id);
            return "TimeSheet supprimé";
        } else
            return "TimeSheet non supprimé";
    }

    public Timesheet findById(Long id){
        return tsr.findById(id).get();
    }

    public Timesheet affecterTimesheetATacheEtUser(Long tacheId, Long userId, Timesheet timesheet) {
        Tache tache = tr.findById(tacheId).get();
        User user = ur.findById(userId).get();
        if(!user.equals(tache.getUser())) {
            throw new IllegalStateException("L'utilisateur n'est pas assigné à cette tâche");
        }
        timesheet.setTache(tache);
        timesheet.setUser(user);
        return tsr.save(timesheet);
    }


    public Map<String, Double> getHeuresParTache() {
        List<Timesheet> timesheets = tsr.findAll();
        return timesheets.stream()
                .filter(timesheet -> timesheet.getTache() != null)
                .collect(Collectors.groupingBy(
                        timesheet -> timesheet.getTache().getNom(),
                        Collectors.summingDouble(Timesheet::getHeures)
                ));
    }







}
