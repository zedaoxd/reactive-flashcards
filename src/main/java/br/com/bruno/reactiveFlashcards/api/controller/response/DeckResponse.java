package br.com.bruno.reactiveFlashcards.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Set;

public record DeckResponse(@JsonProperty("id")
                           @Schema(example = "6428e91643a3b71e01ad89cb", format = "uuid")
                           String id,
                           @JsonProperty("name")
                           @Schema(example = "My deck")
                           String name,
                           @JsonProperty("description")
                           @Schema(example = "My deck description")
                           String description,
                           @JsonProperty("cards")
                           Set<CardResponse> cards) {

    @Builder(toBuilder = true)
    public DeckResponse {}
}
