package br.com.bruno.reactiveFlashcards.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record CardResponse(@JsonProperty("front")
                           @Schema(description = "Front of the card")
                           String front,
                           @JsonProperty("back")
                           @Schema(description = "Back of the card")
                           String back) {

    @Builder(toBuilder = true)
    public CardResponse {
    }
}
