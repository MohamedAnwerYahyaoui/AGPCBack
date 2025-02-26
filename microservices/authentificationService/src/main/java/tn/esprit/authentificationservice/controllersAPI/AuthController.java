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



//    @PostMapping("/login")
//    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRecord loginRecord) {
//        try {
//            // Call the login method from AuthService and pass the LoginRecord
//            Map<String, Object> response = authService.login(loginRecord);
//            return ResponseEntity.ok(response); // Return response with 200 OK status
//        } catch (Exception e) {
//            return ResponseEntity.status(401).body(Map.of("message", "Invalid username or password"));
//        }
//    }
//
//    // Endpoint for logging out
//    @PostMapping("/logout/{userId}")
//    public ResponseEntity<String> logout(@PathVariable String userId) {
//        authService.logout(userId);
//        return ResponseEntity.ok("User logged out successfully");
//    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRecord loginRequest) {
      return authServiceImp.login(loginRequest);

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam("refresh_token") String refreshToken) {
        return authServiceImp.logout(refreshToken);
    }


}
