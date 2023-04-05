package br.com.bruno.reactiveFlashcards.core;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Reactive Flashcards API",
                version = "1.0",
                description = "API for managing flashcards"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080/reactive-flashcards",
                        description = "Local server"
                ),
        }
)
public class OpenApiConfig {
}
