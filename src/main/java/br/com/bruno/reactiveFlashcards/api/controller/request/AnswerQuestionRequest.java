package br.com.bruno.reactiveFlashcards.api.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record AnswerQuestionRequest(@JsonProperty("answer")
                                    @Size(min = 1, max = 255)
                                    @NotBlank
                                    @Schema(example = "answer")
                                    String answer) {

    @Builder(toBuilder = true)
    public AnswerQuestionRequest {
    }
}
