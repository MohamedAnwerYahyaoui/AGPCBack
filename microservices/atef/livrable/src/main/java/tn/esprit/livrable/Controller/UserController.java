package tn.esprit.livrable.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.livrable.Service.UserService;
import tn.esprit.livrable.entity.Tache;
import tn.esprit.livrable.entity.User;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {



    private final UserService us;

    public  UserController(UserService us){
        this.us=us;
    }



    @GetMapping("/all")
    public ResponseEntity<List<User>> listUser(){
        return new ResponseEntity<>(us.findAll(), HttpStatus.OK);
    }







}
