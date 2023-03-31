package br.com.bruno.reactiveFlashcards.domain.exception;

public class EmailAlreadyExistsException extends ReactiveFlashcardsException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }

    public EmailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
