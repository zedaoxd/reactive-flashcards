package br.com.bruno.reactiveFlashcards.domain.exception;

public class DeckInStudyException extends ReactiveFlashcardsException{
    public DeckInStudyException(String message) {
        super(message);
    }

    public DeckInStudyException(String message, Throwable cause) {
        super(message, cause);
    }
}
