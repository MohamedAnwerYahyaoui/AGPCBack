package tn.esprit.authentificationservice.Models;

import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;
import java.util.stream.Collectors;

public record UserDTO(String id,
                      String username,
                      String firstName,
                      String lastName,
                      String email,
                      boolean emailVerified,
                      List<String> roles) {

    // Static method to convert UserRepresentation to UserDTO
    public static UserDTO from(UserRepresentation user, UsersResource usersResource) {
        List<String> roles = getUserRoles(usersResource, user.getId());
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

    // Helper method to fetch roles of a user
    private static List<String> getUserRoles(UsersResource usersResource, String userId) {
        List<RoleRepresentation> roleRepresentations = usersResource.get(userId)
                .roles()
                .realmLevel()
                .listAll(); // Fetch all realm roles assigned to the user

        return roleRepresentations.stream()
                .map(RoleRepresentation::getName)
                .collect(Collectors.toList());
    }
}

