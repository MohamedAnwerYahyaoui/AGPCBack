package tn.esprit.livrable.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.livrable.entity.Status;
import tn.esprit.livrable.entity.Tache;

import java.util.List;
import java.util.Set;

@Repository
public interface TacheRepo extends JpaRepository<Tache,Long> {

    Tache findByNom(String nom);

    // zedetha bech naffecti e tache terminetr lel livrable
    Set<Tache> findByStatus(Status status);
//Tache findByStatus(Status s);



}
