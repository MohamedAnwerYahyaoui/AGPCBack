package tn.esprit.authentificationservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import tn.esprit.authentificationservice.Models.LoginRecord;
import tn.esprit.authentificationservice.services.contrat.AuthServiceContrat;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl {

    @Value("${app.keycloak.serverUrl}")
    private String keycloakUrl;

    @Value("${app.keycloak.realm}")
    private String realm;

    @Value("${app.keycloak.admin.clientId}")
    private String clientId;

    @Value("${app.keycloak.admin.clientSecret}")
    private String clientSecret;

    private final Keycloak keycloak;

//    @Override
//    public Map<String, Object> login(LoginRecord loginRecord) {
//        try {
//            Keycloak keycloak = KeycloakBuilder.builder()
//                    .serverUrl(keycloakUrl)
//                    .realm(realm)
//                    .clientId(clientId)
//                    .clientSecret(clientSecret)
//                    .username(loginRecord.username()) // Use username from LoginRecord
//                    .password(loginRecord.password()) // Use password from LoginRecord
//                    .grantType("password")
//                    .build();
//
//            String accessToken = keycloak.tokenManager().getAccessToken().getToken();
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("token", accessToken);
//            response.put("message", "Login successful");
//            return response;
//        } catch (Exception e) {
//            throw new RuntimeException("Invalid username or password");
//        }
//    }
//
//    @Override
//    public void logout(String userId) {
//        RealmResource realmResource = keycloak.realm(realm);
//        UserResource userResource = realmResource.users().get(userId);
//        userResource.logout();
//    }


    private final WebClient webClient = WebClient.create();

    public ResponseEntity<?> login(LoginRecord request) {
        String tokenUrl = String.format("%s/realms/%s/protocol/openid-connect/token",
                keycloakUrl, realm);

        return webClient.post()
                .uri(tokenUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(
                        "grant_type=client_credentials&" +
                                "client_id=" + clientId + "&" +
                                "client_secret=" + clientSecret + "&" +
                                "username=" + request.username() + "&" +
                                "password=" + request.password()
                )
                .retrieve()
                .toEntity(Object.class)
                .block();
    }

    public ResponseEntity<?> logout(String refreshToken) {
        String logoutUrl = String.format("%s/realms/%s/protocol/openid-connect/logout",
                keycloakUrl, realm);

        return webClient.post()
                .uri(logoutUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(
                        "client_id=" + clientId + "&" +
                                "client_secret=" + clientSecret + "&" +
                                "refresh_token=" + refreshToken
                )
                .retrieve()
                .toEntity(Object.class)
                .block();
    }



}
