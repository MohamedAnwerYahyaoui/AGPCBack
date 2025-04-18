package tn.esprit.livrable.FeignClient;

//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.livrable.DTO.EmailReqDTO;
//import tn.esprit.livrable.Entity.Tache;
//import tn.esprit.user.User;

import java.util.List;

@FeignClient(name = "GmailApi", url = "http://localhost:8081")
public interface GmailClient {




    @PostMapping(value = "/mail/send", consumes = "application/json")
    String sendEmail(@RequestBody EmailReqDTO emailRequest);
    }


