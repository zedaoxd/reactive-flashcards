package br.com.bruno.reactiveFlashcards.api.controller.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.OffsetDateTime;

public record AnswerQuestionResponse(@JsonProperty("asked")
                                     @Schema(example = "What is the answer to the ultimate question of life, the universe, and everything?")
                                     String asked,
                                     @JsonProperty("askedIn")
                                     @Schema(example = "2021-01-01T00:00:00Z", format = "date-time")
                                     OffsetDateTime askedIn,
                                     @JsonProperty("answered")
                                     @Schema(example = "42")
                                     String answered,
                                     @JsonProperty("answeredIn")
                                     @Schema(example = "2021-01-01T00:00:00Z", format = "date-time")
                                     OffsetDateTime answeredIn,
                                     @JsonProperty("expected")
                                     @Schema(example = "42")
                                     String expected) {

    @Builder(toBuilder = true)
    public AnswerQuestionResponse { }
}
