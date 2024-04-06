package de.aittr.gr26_proj_fin.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Online Book Store app",
                description = "APIs to shop books online",
                version = "1.0.0",
                contact = @Contact(
                        name = "Yurii Voronyi",
                        email = " ",
                        url = " "
                )
        )
)
public class SwaggerConfig {
}