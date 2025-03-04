package tn.esprit.authentificationservice.services.impl;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tn.esprit.authentificationservice.Models.RoleDTO;
import tn.esprit.authentificationservice.Models.RoleRecord;
import tn.esprit.authentificationservice.services.contrat.RoleServiceContrat;
import tn.esprit.authentificationservice.services.contrat.UserServiceContrat;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService implements RoleServiceContrat {


    private final UserServiceContrat userService;

    @Value("${app.keycloak.realm}")
    private String realm;
    private final Keycloak keycloak;

@Override
public void createRole(RoleRecord newRoleRecord) {
    RolesResource rolesResource = getRolesResource();

    RoleRepresentation roleRepresentation = new RoleRepresentation();
    roleRepresentation.setName(newRoleRecord.roleName());
    roleRepresentation.setDescription(newRoleRecord.description());

     rolesResource.create(roleRepresentation);




    log.info("Role '{}' created successfully!", newRoleRecord.roleName());


}

    @Override
    public void deleteRole(String roleName) {
        RolesResource rolesResource = getRolesResource();
        rolesResource.deleteRole(roleName);
        log.info("Role '{}' deleted successfully!", roleName);


    }

    @Override
    public List<RoleDTO> getAllRoles() {
        RolesResource rolesResource = getRolesResource();

        return rolesResource.list()
                .stream()
                .map(role -> new RoleDTO(
                        role.getId(),
                        role.getName(),
                        role.getDescription()
                ))
                .collect(Collectors.toList());

    }

    @Override
    public void assignRole(String userId, String roleName) {
        UserResource user = userService.getUserHelper(userId);
        RolesResource rolesResource = getRolesResource();
        RoleRepresentation representation = rolesResource.get(roleName).toRepresentation();
        user.roles().realmLevel().add(Collections.singletonList(representation));


    }

    @Override
    public void deleteRoleFromUser(String userId, String roleName) {
        UserResource user = userService.getUserHelper(userId);
        RolesResource rolesResource = getRolesResource();
        RoleRepresentation representation = rolesResource.get(roleName).toRepresentation();
        user.roles().realmLevel().remove(Collections.singletonList(representation));

    }

    @Override
    public void updateRole(String originalRoleName, RoleRecord updatedRole) {
        RolesResource rolesResource = getRolesResource();
        RoleResource roleResource = rolesResource.get(originalRoleName);
        RoleRepresentation roleRep = roleResource.toRepresentation();

        roleRep.setName(updatedRole.roleName());
        roleRep.setDescription(updatedRole.description());

        roleResource.update(roleRep);
        log.info("Role '{}' updated successfully!", originalRoleName);

    }


    private RolesResource getRolesResource() {
        return keycloak.realm(realm).roles();
    }


}
