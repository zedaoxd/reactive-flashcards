package br.com.bruno.reactiveFlashcards.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record ErrorFieldResponse(@JsonProperty("name")
                                 @Schema(description = "Name of the field that has the error", example = "name")
                                 String name,
                                 @JsonProperty("message")
                                 @Schema(description = "Message of the error", example = "Name is required")
                                 String message) {

    @Builder(toBuilder = true)
    public ErrorFieldResponse {}
}
