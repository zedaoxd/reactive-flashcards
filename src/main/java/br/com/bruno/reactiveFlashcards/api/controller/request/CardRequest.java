package br.com.bruno.reactiveFlashcards.api.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record CardRequest(@JsonProperty("front")
                          @NotBlank
                          @Size(min = 1, max = 255)
                          @Schema(example = "study of English")
                          String front,
                          @JsonProperty("back")
                          @NotBlank
                          @Size(min = 1, max = 255)
                          @Schema(example = "estudo de inglês")
                          String back) {

    @Builder(toBuilder = true)
    public CardRequest {}
}
