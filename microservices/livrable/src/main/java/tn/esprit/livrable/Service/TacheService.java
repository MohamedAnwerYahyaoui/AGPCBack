package tn.esprit.livrable.Service;

import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.livrable.DTO.EmailReqDTO;
import tn.esprit.livrable.FeignClient.GmailClient;
import tn.esprit.livrable.Repository.LivrableRepo;
import tn.esprit.livrable.Repository.TacheRepo;
import tn.esprit.livrable.Repository.UserRepo;
import tn.esprit.livrable.entity.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service


public class TacheService {

    private final TacheRepo tr;
    private final UserRepo ur;
    private final LivrableRepo lr;
    private final GmailClient gcc;

    public TacheService(TacheRepo tr, UserRepo ur, LivrableRepo lr, GmailClient gcc) {
        this.tr = tr;
        this.ur = ur;
        this.lr = lr;
        this.gcc=gcc;
    }




    public Tache ajouterTache(Tache tache) {
        if (tache.getId() != null && tache.getId() == 0) {
            tache.setId(null);
        }

        // Si userId est fourni, associer l'utilisateur
        if (tache.getIduser() != null) {
            User user = ur.findById(tache.getIduser())
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
            tache.setUser(user);

            // Ajouter la tâche à la liste des tâches de l'utilisateur
            Set<Tache> userTasks = user.getTaches();
            userTasks.add(tache);
            user.setTaches(userTasks);
            ur.save(user);
        }

        return tr.save(tache);
    }









    public List<Tache> findAll() {

        return tr.findAll();
    }

public Tache updateTache(long id, Tache newTache) {
    return tr.findById(id).map(existingTache -> {

        existingTache.setNom(newTache.getNom());
        existingTache.setDescription(newTache.getDescription());
        existingTache.setStatus(newTache.getStatus());

        // Gestion des dates
        if (newTache.getDateDebut() != null) {
            existingTache.setDateDebut(newTache.getDateDebut());
        }
        if (newTache.getDateFin() != null) {
            existingTache.setDateFin(newTache.getDateFin());
        }

        return tr.save(existingTache);
    }).orElseThrow(() -> new RuntimeException("Tâche non trouvée"));
}




    public String deleteTache(long id) {
        if (tr.findById(id).isPresent()) {
            tr.deleteById(id);
            return "Tache supprimé";
        } else
            return "Tache non supprimé";
    }

    public Tache findById(Long id) {
        return tr.findById(id).get();
    }


    public Tache assignerTacheAUser(Long tacheId, Long userId) {

        User user = ur.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Tache tache = tr.findById(tacheId)
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée"));

        // 2. Construction du DTO pour l'email
        EmailReqDTO emailRequest = new EmailReqDTO();
        emailRequest.setTo(user.getEmail());
        emailRequest.setSubject("Nouvelle tâche assignée");
        emailRequest.setText(String.format(
                "Bonjour %s,%n%nVous avez été assigné à la tâche :%n" +
                        "- Nom: %s%n- Description: %s%n" +
                        "- Dates: %s à %s%n%nCordialement,",
                user.getUsername(),
                tache.getNom(),
                tache.getDescription(),
                tache.getDateDebut(),
                tache.getDateFin()
        ));

        // 3. Envoi de l'email

            String result = gcc.sendEmail(emailRequest);


        // 4. Mise à jour des relations
        tache.setUser(user);
        tache.setIduser(userId);
        user.getTaches().add(tache);

        ur.save(user);
        return tr.save(tache);
    }

    public Tache dessaffecterTacheDeUser(String tacheNom) {
        Tache tache = tr.findByNom(tacheNom);
        User user = tache.getUser();
        user.getTaches().removeIf(t -> t.getNom().equals(tacheNom));
        tache.setUser(null);
        ur.save(user);
        return tr.save(tache);
    }

    public void affecterTacheALivrableSiTermine(Long tacheId, Long livrableId) {
        Tache tache = tr.findById(tacheId).get();
        Livrable livrable = lr.findById(livrableId).get();
        if (tache.getStatus().equals(Status.DONE)){
            tache.setLivrable(livrable);
            tr.save(tache);
        }


    }

    public List<User> getUsersByTacheId(Long tacheId) {
        return ur.findUsersByTacheId(tacheId);
    }






    @Scheduled(fixedRate = 60000)
    @Transactional
    public void transfererTachesTermineesVersLivrable() {
        Set<Tache> tachesTerminees = tr.findByStatus(Status.DONE);
        if (!tachesTerminees.isEmpty()) {
            for (Tache tache : tachesTerminees) {
                Livrable livrable = new Livrable(tache.getNom(), tache.getDescription(), LocalDate.now(), new ArrayList<>());
                lr.save(livrable);
                tr.delete(tache);
            }
        }
    }}







