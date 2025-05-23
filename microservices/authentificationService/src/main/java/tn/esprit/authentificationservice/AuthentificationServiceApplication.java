package tn.esprit.authentificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthentificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthentificationServiceApplication.class, args);
    }

}
