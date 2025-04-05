package tn.esprit.livrable.Service;

import org.springframework.stereotype.Service;
import tn.esprit.livrable.Repository.UserRepo;
import tn.esprit.livrable.entity.Tache;
import tn.esprit.livrable.entity.User;

import java.util.List;
import java.util.Set;

@Service
public class UserService {


    private final UserRepo ur;



    public UserService(UserRepo ur){
        this.ur=ur;
    }



    User findById(Long id){
        return ur.findById(id).get();
    }


    public List<User> findAll() {
        return ur.findAll();
    }

    public Set<Tache> getTachesByUserId(Long userId) {
        return ur.findTachesByUserId(userId);
    }

}
