package tn.esprit.authentificationservice.services.impl;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tn.esprit.authentificationservice.Models.UserDTO;
import tn.esprit.authentificationservice.Models.UserRecord;
import tn.esprit.authentificationservice.services.contrat.RoleServiceContrat;
import tn.esprit.authentificationservice.services.contrat.UserServiceContrat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserServiceContrat {

    @Value("${app.keycloak.realm}")
    private String realm;

    private final Keycloak keycloak;
   // private final RoleServiceContrat roleServiceContrat;
    @Override
    public void createUser(UserRecord newUserRecord) {
        // Step 1: Create UserRepresentation
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setFirstName(newUserRecord.firstName());
        userRepresentation.setLastName(newUserRecord.lastName());
        userRepresentation.setUsername(newUserRecord.username());
        userRepresentation.setEmail(newUserRecord.email());
        userRepresentation.setEmailVerified(false);

        // Step 2: Set Password
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(newUserRecord.password());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        userRepresentation.setCredentials(List.of(credentialRepresentation));

        // Step 3: Create User in Keycloak
        UsersResource usersResource = getUsersResource();
        Response response = usersResource.create(userRepresentation);
        log.info("Status Code: " + response.getStatus());

        if (response.getStatus() != 201) {
            throw new RuntimeException("User creation failed: Status code " + response.getStatus());
        }
        log.info("New user has been created");

        // Step 4: Get the newly created user
        List<UserRepresentation> users = usersResource.search(newUserRecord.username());
        if (users.isEmpty()) {
            throw new RuntimeException("User not found after creation");
        }
        UserRepresentation createdUser = users.get(0);
        String userId = createdUser.getId(); // Get the user's ID

        // Step 5: Assign Roles
        RoleMappingResource roleMappingResource = usersResource.get(userId).roles();
        RealmResource realmResource = getRealmResource();

        for (String roleName : newUserRecord.roles()) {
            RoleRepresentation role = realmResource.roles().get(roleName).toRepresentation();
            if (role != null) {
                roleMappingResource.realmLevel().add(List.of(role));
                log.info("Role '{}' assigned to user '{}'", roleName, newUserRecord.username());
            } else {
                log.warn("Role '{}' not found in Keycloak", roleName);
            }
        }
        log.info("New user has bee created");

        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(newUserRecord.username(), true);
        UserRepresentation userRepresentation1 = userRepresentations.get(0);
        sendVerificationEmail(userRepresentation1.getId());



    }

    @Override
    public void sendVerificationEmail(String userId) {
        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();

    }

    @Override
    public void deleteUser(String userId) {
        UsersResource usersResource = getUsersResource();
        usersResource.delete(userId);


    }

    @Override
    public void forgotPassword(String username) {
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(username, true);
        UserRepresentation userRepresentation1 = userRepresentations.get(0);
        UserResource userResource = usersResource.get(userRepresentation1.getId());
        userResource.executeActionsEmail(List.of("UPDATE_PASSWORD"));

    }

    @Override
    public UserDTO  getUser(String userId) {


        UsersResource usersResource = getUsersResource();
        UserRepresentation user = usersResource.get(userId).toRepresentation();

        // Fetch user roles
        List<String> roles = usersResource.get(userId).roles().realmLevel().listAll()
                .stream()
                .map(RoleRepresentation::getName)
                .collect(Collectors.toList());

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.isEmailVerified(),
                roles
        );



    }

    @Override
    public List<RoleRepresentation> getUserRoleEndpoint(String userId) {
       return getUserHelper(userId).roles().realmLevel().listAll();

    }

    @Override
    public List<GroupRepresentation> getUserGroups(String userId) {
        return null ;//getUser(userId).groups();
         }

    @Override
    public List<UserDTO> getAllUsers() {
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> users = usersResource.list();

        return users.stream()
                .map(user -> UserDTO.from(user, usersResource)) // Convert each user to UserDTO with roles
                .collect(Collectors.toList());
    }

    @Override
    public UserResource getUserHelper(String userId) {
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);

    }


    private UsersResource getUsersResource(){

        return keycloak.realm(realm).users();
    }


//    private RoleRepresentation getRoleByName(String roleName) {
//       RolesResource rolesResource = getRolesResource();
//        RoleResource roleResource = rolesResource.get(roleName); // Returns RoleResource
//        if (roleResource == null) {
//            log.warn("Role '{}' not found", roleName);
//            return null; // Handle case where role doesn't exist
//        }
//
//        return roleResource.toRepresentation();
//
//    }

  //  private RolesResource getRolesResource() {
//        return keycloak.realm(realm).roles();
//    }
    private RealmResource getRealmResource() {
        return keycloak.realm(realm);
    }


}
