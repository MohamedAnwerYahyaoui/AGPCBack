package tn.esprit.authentificationservice.services.contrat;

import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.RoleRepresentation;
import tn.esprit.authentificationservice.Models.RoleDTO;
import tn.esprit.authentificationservice.Models.RoleRecord;

import java.util.List;

public interface RoleServiceContrat {

    void createRole(RoleRecord newRole);
    void deleteRole(String roleName);
    public List<RoleDTO> getAllRoles();
    void assignRole(String userId ,String roleName);
   void deleteRoleFromUser(String userId ,String roleName);


}
