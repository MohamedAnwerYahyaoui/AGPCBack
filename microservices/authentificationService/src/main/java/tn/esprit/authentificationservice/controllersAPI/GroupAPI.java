package tn.esprit.authentificationservice.controllersAPI;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.authentificationservice.services.contrat.GroupServiceContrat;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupAPI {

    private final GroupServiceContrat groupService;


    @PutMapping("/{groupId}/assign/users/{userId}")
    public ResponseEntity<?> assignGroup(@PathVariable String userId, @PathVariable String groupId) {

        groupService.assignGroup(userId, groupId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{groupId}/remove/users/{userId}")
    public ResponseEntity<?> unAssignGroup(@PathVariable String userId, @PathVariable String groupId) {

        groupService.deleteGroupFromUser(userId, groupId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
