package tn.esprit.authentificationservice.controllersAPI;

import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.authentificationservice.Models.RoleDTO;
import tn.esprit.authentificationservice.Models.RoleRecord;
import tn.esprit.authentificationservice.Models.UserRecord;
import tn.esprit.authentificationservice.services.contrat.RoleServiceContrat;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/roles")
public class RolesAPI {

   private final RoleServiceContrat roleService;
//

    @PostMapping("/add")
    public ResponseEntity<?> createRole(@RequestBody RoleRecord newRoleRecord) {

        roleService.createRole(newRoleRecord);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{roleName}")
    public ResponseEntity<?> deleteRole(@PathVariable String roleName) {

        roleService.deleteRole(roleName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }



    @PutMapping("/assign/users/{userId}")
    public ResponseEntity<?> assignRole(@PathVariable String userId, @RequestParam String roleName) {

        roleService.assignRole(userId, roleName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @DeleteMapping("/remove/users/{userId}")
    public ResponseEntity<?> unAssignRole(@PathVariable String userId, @RequestParam String roleName) {

        roleService.deleteRoleFromUser(userId, roleName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
