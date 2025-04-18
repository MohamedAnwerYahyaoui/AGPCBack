package com.example.employee.Services;
import com.example.employee.Entities.Equipe;
import com.example.employee.Entities.Livrable;
import com.example.employee.Repositories.EquipeRepositoriy;
import com.example.employee.Repositories.LivrableRepositoriy;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class EquipeService {

    private  final LivrableRepositoriy livrableRepositoriy;
    private final EquipeRepositoriy equipeRepository;

    public EquipeService(EquipeRepositoriy equipeRepository, LivrableRepositoriy livrableRepositoriy){

        this.equipeRepository=equipeRepository;
        this.livrableRepositoriy = livrableRepositoriy;
    }



    public Equipe addEquipe(Equipe equipe ) {

        return equipeRepository.save(equipe);
    }
    public Equipe getEquipeById(Long id) {
        return equipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipe non trouvée avec l'id : " + id));
    }


    public List<Equipe> findAll() {

        return equipeRepository.findAll();
    }


    public Equipe updateEquipe(long id, Equipe newEquipe) {
        if (equipeRepository.findById(id).isPresent()) {
            Equipe equipe = equipeRepository.findById(id).get();
            equipe.setNom(newEquipe.getNom());
            equipe.setNombreMembres(newEquipe.getNombreMembres());
            equipe.setDateCreation(newEquipe.getDateCreation());
            equipe.setContactEquipe(newEquipe.getContactEquipe());

            return equipeRepository.save(equipe);
        } else
            return null;
    }


    public String deleteEquipe(long id) {
        if (equipeRepository.findById(id).isPresent()) {
            equipeRepository.deleteById(id);
            return "Equipe est  supprimé avec succés";
        } else
            return "Equipe non supprimé";
    }
    public Equipe affecterLivrableAEquipe(Long equipeId, Long livrableId) {
        Equipe equipe = equipeRepository.findById(equipeId).orElseThrow(() -> new RuntimeException("Équipe non trouvée"));
        Livrable livrable = livrableRepositoriy.findById(livrableId)
                .orElseThrow(() -> new RuntimeException("Livrable non trouvé"));

        equipe.setLivrable(livrable);
       return equipeRepository.save(equipe);
    }
    public Page<Equipe> getEquipes(String searchTerm, Long livrableId, Pageable pageable) {
        Specification<Equipe> spec = Specification.where(null);

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            String term = searchTerm.toLowerCase();
            spec = spec.and((root, query, cb) -> {
                Join<Object, Object> livrableJoin = root.join("livrable", JoinType.LEFT);
                return cb.or(
                        cb.like(cb.lower(root.get("nom")), "%" + term + "%"),
                        cb.like(cb.lower(root.get("contactEquipe")), "%" + term + "%"),
                        cb.like(cb.lower(livrableJoin.get("nom")), "%" + term + "%")
                );
            });
        }

        if (livrableId != null) {
            spec = spec.and((root, query, cb) -> {
                Join<Object, Object> livrableJoin = root.join("livrable", JoinType.LEFT);
                return cb.equal(livrableJoin.get("id"), livrableId);
            });
        }

        return equipeRepository.findAll(spec,pageable);
    }
    public Equipe updateRating(Long id, double rating) {
        Equipe equipe = equipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipe non trouvée avec id " + id));
        equipe.setRating(rating);
        return equipeRepository.save(equipe);
    }
}
