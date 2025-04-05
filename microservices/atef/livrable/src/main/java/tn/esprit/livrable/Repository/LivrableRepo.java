package tn.esprit.livrable.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.livrable.entity.Livrable;

import java.util.List;

@Repository
public interface LivrableRepo extends JpaRepository<Livrable,Long> {

}


