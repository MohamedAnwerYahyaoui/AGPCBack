package tn.esprit.authentificationservice.services.contrat;

import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import tn.esprit.authentificationservice.Models.UserDTO;
import tn.esprit.authentificationservice.Models.UserRecord;

import java.util.List;

public interface UserServiceContrat {

    void createUser(UserRecord newUserRecord);
  void sendVerificationEmail(String userId);
   void deleteUser(String userId);
   void forgotPassword(String username);
    UserDTO getUser(String userId);
    List<RoleRepresentation> getUserRoleEndpoint(String userId);
    List<GroupRepresentation> getUserGroups(String userId);

    public List<UserDTO> getAllUsers();
    public UserResource getUserHelper(String userId);

}
