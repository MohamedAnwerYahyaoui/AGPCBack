package tn.esprit.authentificationservice.services.contrat;

public interface GroupServiceContrat {

   void assignGroup(String userId ,String groupId);
   void deleteGroupFromUser(String userId ,String groupId);

}
