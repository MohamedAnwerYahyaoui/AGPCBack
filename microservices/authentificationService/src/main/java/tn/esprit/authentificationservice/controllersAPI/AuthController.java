package tn.esprit.authentificationservice.controllersAPI;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.authentificationservice.Models.LoginRecord;
import tn.esprit.authentificationservice.services.contrat.AuthServiceContrat;
import tn.esprit.authentificationservice.services.impl.AuthServiceImpl;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class AuthController {

  //  private final AuthServiceContrat authService;
    private final AuthServiceImpl authServiceImp;






    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRecord loginRequest) {
      return authServiceImp.login(loginRequest);

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam("refresh_token") String refreshToken) {
        return authServiceImp.logout(refreshToken);
    }


}
