package tn.esprit.authentificationservice.SecurityConfiguration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

   private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//
//        return (web) -> {
//            web.ignoring().requestMatchers(
//                    HttpMethod.POST,
//                    "/public/**",
//                    "/users",
//                    "/roles",
//                    "/login",
//                    "/logout/{userId}",
//                    "/loginIn",
//                    "/logoutOut"
//            );
//
//            web.ignoring().requestMatchers(
//                    HttpMethod.GET,
//                    "/public/**"
//            );
//            web.ignoring().requestMatchers(
//                    HttpMethod.DELETE,
//                    "/public/**",
//                    "/auth/users/{id}"
//            );
//            web.ignoring().requestMatchers(
//                    HttpMethod.PUT,
//                    "/public/**",
//                    "/auth/users/{id}/send-verification-email",
//                    "/auth/users/forgot-password"
//
//            );
//            web.ignoring().requestMatchers(
//                            HttpMethod.OPTIONS,
//                            "/**"
//                    )
//                    .requestMatchers("/v3/api-docs/**", "/configuration/**", "/swagger-ui/**",
//                            "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/api-docs/**");
//
//        };
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:4200"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(csrf -> csrf.disable()) // Disable for simplicity (enable for production)
                .authorizeHttpRequests(auth -> auth
                        // use authentication login logout
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/logout").permitAll()
                        // User API Endpoints
                        .requestMatchers(HttpMethod.POST, "/users/add").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/users/*/send-verification-email").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/users/*").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/users/forgot-password").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/*/roles").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/all").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/helper/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/*/groups").permitAll()

                        // Role API Endpoints
                        .requestMatchers(HttpMethod.POST, "/roles/add").permitAll()
                        .requestMatchers(HttpMethod.GET, "/roles/all").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/roles/*").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/roles/assign/users/*").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/roles/remove/users/*").permitAll()

                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }



}
