package tn.esprit.authentificationservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tn.esprit.authentificationservice.services.contrat.GroupServiceContrat;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService implements GroupServiceContrat {
    private final Keycloak keycloak;
    private final UserService userService;

    @Value("${app.keycloak.realm}")
    private String realm;

    @Override
    public void assignGroup(String userId, String groupId) {


//        UserResource user = userService.getUser(userId);
//        user.joinGroup(groupId);
    }

    @Override
    public void deleteGroupFromUser(String userId, String groupId) {

//        UserResource user = userService.getUser(userId);
//        user.leaveGroup(groupId);
    }


}
