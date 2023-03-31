package br.com.bruno.reactiveFlashcards.api.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public record DeckRequest(@JsonProperty("name")
                          @NotBlank
                          @Size(min = 1, max = 255)
                          String name,
                          @JsonProperty("description")
                          @NotBlank
                          @Size(min = 1, max = 255)
                          String description,
                          @JsonProperty("cards")
                          @Valid
                          @NotEmpty
                          @Size(min = 3, max = 255)
                          Set<CardRequest> cards) {

    @Builder(toBuilder = true)
    public DeckRequest {}
}
