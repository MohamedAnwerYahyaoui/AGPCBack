package io.tutorial.spring.document.Service;

import io.tutorial.spring.document.entity.Assurance;

import java.util.List;
import java.util.Optional;

public interface IAssuranceService {
    Assurance ajouterAssurance(Assurance assurance);

    List<Assurance> obtenirToutesLesAssurances();

    Optional<Assurance> obtenirAssuranceParId(Long id);

    Assurance mettreAJourAssurance(Long id, Assurance assurance);

    void deleteAssurance (Long idDocument);
}
