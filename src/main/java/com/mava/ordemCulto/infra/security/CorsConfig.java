package com.mava.ordemCulto.infra.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:3000", // Para seu desenvolvimento local
                        "http://localhost:5173", // Porta padrão do Vite, caso use
                        "https://ordem-culto-front.vercel.app" // <<< SEU LINK CORRETO DA VERCEL
                ) // Ou especifique os domínios permitidos
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Adicione "OPTIONS"
                .allowedHeaders("*") // Permite todos os cabeçalhos
                .allowCredentials(true); // Permite envio de credenciais (como cookies ou autenticação)
    }
}