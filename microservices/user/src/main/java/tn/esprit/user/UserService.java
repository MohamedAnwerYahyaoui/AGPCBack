package tn.esprit.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository ur;



    public UserService(UserRepository ur){
        this.ur=ur;
    }


    public User findById(Long id){
        return ur.findById(id).get();
    }

}
