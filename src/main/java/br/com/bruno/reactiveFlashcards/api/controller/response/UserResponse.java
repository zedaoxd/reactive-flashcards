package br.com.bruno.reactiveFlashcards.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record UserResponse(@JsonProperty("id") @Schema(format = "uuid", example = "6428e91643a3b71e01ad89cb")
                           String id,
                           @JsonProperty("name")
                           @Schema(example = "John Doe")
                           String name,
                           @JsonProperty("email")
                           @Schema(example = "mail@mail.com.br")
                           String email) {

    @Builder(toBuilder = true)
    public UserResponse {}
}
