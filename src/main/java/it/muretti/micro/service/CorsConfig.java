package it.muretti.micro.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
            	registry.addMapping("/**") // Imposta le rotte da cui accettare richieste
                .allowedOrigins("*") // Permetti solo il dominio React in fase di sviluppo
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH","HEAD") // I metodi HTTP consentiti
                .allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers") // Gli headers che il frontend può inviare
                .allowCredentials(false); // Consenti i cookie (se necessario)
            }
        };
    }
}
