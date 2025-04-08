package io.tutorial.spring.document.Service;

import io.tutorial.spring.document.Repository.AssuranceRepository;
import io.tutorial.spring.document.Repository.DocumentRepository;
import io.tutorial.spring.document.entity.Assurance;
import io.tutorial.spring.document.entity.Document;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssuranceService {
    private static final Logger logger = LoggerFactory.getLogger(AssuranceService.class);

    private final AssuranceRepository assuranceRepository;
    private final DocumentRepository documentRepository;

    public AssuranceService(AssuranceRepository assuranceRepository, DocumentRepository documentRepository) {
        this.assuranceRepository = assuranceRepository;
        this.documentRepository = documentRepository;

    }

    public Assurance ajouterAssurance(Assurance assurance) {
        return assuranceRepository.save(assurance);
    }
    public Page<Assurance> obtenirAssurancesAvecPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return assuranceRepository.findAll(pageable);
    }


    public List<Assurance> obtenirToutesLesAssurances() {
        return assuranceRepository.findAll();
    }

    public Optional<Assurance> obtenirAssuranceParId(Long id) {
        return assuranceRepository.findById(id);
    }

    public Assurance mettreAJourAssurance(Long id, Assurance assuranceDetails) {
        // Récupérer l'assurance existante
        Assurance assurancee = assuranceRepository.findById(id).orElseThrow(() -> new RuntimeException("Assurance non trouvée"));

        // Mettre à jour les autres attributs de l'assurance
        assurancee.setDateExpiration(assuranceDetails.getDateExpiration());
        assurancee.setTypeAssurance(assuranceDetails.getTypeAssurance());
        assurancee.setMontantCouverture(assuranceDetails.getMontantCouverture());
        assurancee.setNom(assuranceDetails.getNom());

        // Mettre à jour la liste des documents associés
        if (assuranceDetails.getDocuments() != null) {
            // Supprimer les documents associés à l'assurance actuelle
            for (Document document : assurancee.getDocuments()) {
                // Vous pouvez choisir de ne pas supprimer certains documents si vous voulez les garder
                document.setAssurance(null); // Détacher les documents avant de les supprimer
            }
            // Mettez à jour la liste des documents
            assurancee.getDocuments().clear(); // Vider la liste des documents précédemment associés

            // Ajouter les nouveaux documents à l'assurance
            for (Document document : assuranceDetails.getDocuments()) {
                document.setAssurance(assurancee); // Associer chaque document à l'assurance
                assurancee.getDocuments().add(document); // Ajouter le document à la liste
            }
        }

        // Sauvegarder l'assurance mise à jour
        return assuranceRepository.save(assurancee);
    }


    @Transactional
    public void supprimerAssurance(Long id) {
        Optional<Assurance> assurance = assuranceRepository.findById(id);
        if (assurance.isPresent()) {
            assuranceRepository.delete(assurance.get());
            assuranceRepository.flush(); // 🔹 Force la suppression immédiate
            logger.info("Assurance avec ID : {} supprimée.", id);
        } else {
            throw new RuntimeException("Assurance non trouvée avec l'ID : " + id);
        }
    }

}
