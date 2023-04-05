package br.com.bruno.reactiveFlashcards.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.OffsetDateTime;

public record QuestionResponse(@JsonProperty("id")
                               @Schema(example = "6428e91643a3b71e01ad89cb", format = "uuid")
                               String id,
                               @JsonProperty("asked")
                               @Schema(example = "What is the capital of Brazil?")
                               String asked,
                               @JsonProperty("askedIn")
                               @Schema(example = "2021-01-01T00:00:00Z", format = "date-time")
                               OffsetDateTime askedIn) {

    @Builder(toBuilder = true)
    public QuestionResponse {}
}
