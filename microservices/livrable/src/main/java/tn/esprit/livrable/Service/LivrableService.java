package tn.esprit.livrable.Service;

import org.springframework.stereotype.Service;
import tn.esprit.livrable.Repository.LivrableRepo;
import tn.esprit.livrable.entity.Livrable;

import java.util.List;

@Service
public class LivrableService {


    private final LivrableRepo lr;
    public LivrableService(LivrableRepo lr){
        this.lr=lr;
    }

    public Livrable ajouterLivrable(Livrable livrable) {
        return lr.save(livrable);
    }


    public List<Livrable> findAll() {
        return lr.findAll();
    }


    public Livrable updatelivrable(long id, Livrable newLivrable) {
        if (lr.findById(id).isPresent()) {
            Livrable livrable = lr.findById(id).get();
            livrable.setDescription(newLivrable.getDescription());
            livrable.setDateLivraison(newLivrable.getDateLivraison());
            livrable.setNom(newLivrable.getNom());
            livrable.setTaches(newLivrable.getTaches());

            return lr.save(livrable);
        } else
            return null;
    }


    public String deleteLivrable(long id) {
        if (lr.findById(id).isPresent()) {
            lr.deleteById(id);
            return "livrable  supprimé";
        } else
            return "livrable non supprimé";
    }

    public Livrable findById(Long id){
        return lr.findById(id).get();
    }








}
