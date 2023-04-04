package br.com.bruno.reactiveFlashcards.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthDTO(@JsonProperty("token")
                      String token,
                      @JsonProperty("expiresIn")
                      Long expiresIn) {

}
