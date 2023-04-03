package br.com.bruno.reactiveFlashcards.domain.helper;

import br.com.bruno.reactiveFlashcards.core.RetryConfig;
import br.com.bruno.reactiveFlashcards.domain.exception.RetryException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.util.retry.Retry;

import java.util.function.Predicate;

import static br.com.bruno.reactiveFlashcards.domain.exception.BaseErrorMessage.GENERIC_MAX_RETRIES;

@AllArgsConstructor
@Component
@Slf4j
public class RetryHelper {

    private final RetryConfig retryConfig;

    public Retry processRetry(String retryIdentifier, Predicate<? super Throwable> errorFilter){
        return Retry.backoff(retryConfig.maxRetries(), retryConfig.minDurationSeconds())
                .filter(errorFilter)
                .doBeforeRetry(retrySignal -> log.warn("==== Retrying {} - {} times(s)", retryIdentifier,
                        retrySignal.totalRetries()))
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                        new RetryException(GENERIC_MAX_RETRIES.getMessage(), retrySignal.failure()));
    }

}