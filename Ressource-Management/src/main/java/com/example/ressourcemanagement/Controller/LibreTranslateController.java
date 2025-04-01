package com.example.ressourcemanagement.Controller;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.HashMap;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/translate")
public class LibreTranslateController {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LibreTranslateController() {
        this.restTemplate = createRestTemplateWithTimeout();
    }

    private RestTemplate createRestTemplateWithTimeout() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(50);  // Timeout de connexion de 5 secondes
        factory.setReadTimeout(50);     // Timeout de lecture de 5 secondes
        return new RestTemplate(factory);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> translate(
            @RequestBody Map<String, String> request) {

        try {
            if (request.get("text") == null || request.get("target") == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "Paramètres manquants",
                        "message", "Les paramètres 'text' et 'target' sont requis"
                ));
            }

            // Préparation de la requête
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("q", request.get("text"));
            requestBody.put("source", "auto");
            requestBody.put("target", request.get("target"));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Envoi de la requête
            String response = restTemplate.postForObject(
                    "https://libretranslate.de/translate",
                    new HttpEntity<>(requestBody, headers),
                    String.class
            );

            // Traitement de la réponse
            JsonNode jsonResponse = objectMapper.readTree(response);
            String translatedText = jsonResponse.path("translatedText").asText();

            return ResponseEntity.ok(Map.of(
                    "translatedText", translatedText,
                    "status", "success"
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Erreur de traduction",
                            "message", e.getMessage()
                    ));
        }
    }
}