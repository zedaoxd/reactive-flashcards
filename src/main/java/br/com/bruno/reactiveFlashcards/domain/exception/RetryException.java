package br.com.bruno.reactiveFlashcards.domain.exception;

public class RetryException extends ReactiveFlashcardsException{

    public RetryException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
