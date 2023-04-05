package br.com.bruno.reactiveFlashcards.api.controller.request;

import br.com.bruno.reactiveFlashcards.core.validation.MongoId;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record StudyRequest(@MongoId
                           @JsonProperty("userId")
                           @Schema(example = "6428e91643a3b71e01ad89cb", format = "uuid")
                           String userId,
                           @MongoId
                           @JsonProperty("deckId")
                           @Schema(example = "6428e91643a3b71e01ad89cb", format = "uuid")
                           String deckId) {

    @Builder(toBuilder = true)
    public StudyRequest {}
}
