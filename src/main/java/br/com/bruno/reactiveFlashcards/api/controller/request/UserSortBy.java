package br.com.bruno.reactiveFlashcards.api.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserSortBy {
    NAME("name"),
    EMAIL("email");

    private final String field;
}
