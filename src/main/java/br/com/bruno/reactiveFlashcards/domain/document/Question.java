package br.com.bruno.reactiveFlashcards.domain.document;

public record Question(String asked,
                       String answered,
                       String expected) {
}
