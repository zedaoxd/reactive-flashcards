package br.com.bruno.reactiveFlashcards.api.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record UserRequest(@JsonProperty("name")
                          @NotBlank
                          @Size(min = 1, max = 255)
                          String name,
                          @JsonProperty("email")
                          @NotBlank
                          @Size(min = 1, max = 255)
                          @Email
                          String email) {

    @Builder(toBuilder = true)
    public UserRequest {}
}
