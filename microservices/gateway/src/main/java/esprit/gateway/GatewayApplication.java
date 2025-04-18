package esprit.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
    // static route
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes().route(
                "authMS",r->r.path("/um/**").uri("lb://authentificationService")

        )
                .route(
                        "RessourceMMS",r->r.path("/rm/**").uri("lb://Ressource-Management")

                )
                .route(
                        "FormationMMS",r->r.path("/formations/**").uri("lb://formations")

                )
                .route(
                        "BMMS",r->r.path("/bm/**").uri("lb://Budget")

                )
                .route(
                        "EnvMMS",r->r.path("/environnement/**").uri("lb://environnement")

                )
                .route(
                        "DOCMMS",r->r.path("/document/**").uri("lb://Document")

                )
                .route(
                        "EmplMMS",r->r.path("/membres/**").uri("lb://Employee")

                )
                .route(
                        "LivrableMMS",r->r.path("/livra/**").uri("lb://livrable")

                )
                .route(
                        "ReclamationMMS",r->r.path("/reclamation/**").uri("lb://reclamation")

                )
                .route(
                        "UserMMS",r->r.path("/user/**").uri("lb://user")

                )






                .build();
    }

}
