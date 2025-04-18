package tn.esprit.livrable.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.livrable.entity.Timesheet;

@Repository
public interface TimeSheetRepo extends JpaRepository<Timesheet,Long> {
}
