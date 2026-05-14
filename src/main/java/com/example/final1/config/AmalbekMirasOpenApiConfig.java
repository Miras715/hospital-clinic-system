package com.example.final1.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Hospital / Clinic System API",
                version = "1.0",
                description = "REST API for Hospital Clinic System. Student: Amalbekuulu Miras.",
                contact = @Contact(name = "Amalbekuulu Miras", email = "miras@hospital.com")
        ),
        servers = @Server(url = "http://localhost:8080", description = "Local server")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class AmalbekMirasOpenApiConfig {
    // config is done via annotations above
}
