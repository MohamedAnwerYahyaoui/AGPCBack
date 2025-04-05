package tn.esprit.livrable.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.livrable.entity.BuildingModel;

public interface BuildingModelRepo extends JpaRepository<BuildingModel,Long> {
}
