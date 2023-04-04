package br.com.bruno.reactiveFlashcards.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CardDTO(@JsonProperty("ask")
                      String ask,
                      @JsonProperty("answer")
                      String answer) {
}
