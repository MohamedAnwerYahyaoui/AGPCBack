package tn.esprit.livrable.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.livrable.entity.Tache;
import tn.esprit.livrable.entity.User;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

      User findByUsername(String nom);
      @Query("SELECT t.user FROM Tache t WHERE t.id = :tacheId")
      List<User> findUsersByTacheId(@Param("tacheId") Long tacheId);

      @Query("SELECT u.taches FROM User u WHERE u.id = :userId")
      Set<Tache> findTachesByUserId(@Param("userId") Long userId);



}
