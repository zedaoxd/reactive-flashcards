package br.com.bruno.reactiveFlashcards.api.exceptionhandler;

import br.com.bruno.reactiveFlashcards.api.controller.response.ProblemResponse;
import br.com.bruno.reactiveFlashcards.domain.exception.ReactiveFlashcardsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import static br.com.bruno.reactiveFlashcards.domain.exception.BaseErrorMessage.GENERIC_EXCEPTION;
import static br.com.bruno.reactiveFlashcards.domain.exception.BaseErrorMessage.GENERIC_METHOD_NOT_ALLOWED;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@Order(-2)
@Slf4j
@AllArgsConstructor
public class ApiExceptionHandler implements WebExceptionHandler {

    private final ObjectMapper mapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        return Mono.error(ex)
                .onErrorResume(MethodNotAllowedException.class, e -> handleMethodNotAllowedException(exchange, e))
                .onErrorResume(ReactiveFlashcardsException.class, e -> handleReactiveFlashcardsException(exchange, e))
                .onErrorResume(Exception.class, e -> handleException(exchange, e))
                .onErrorResume(JsonProcessingException.class, e -> handleJsonProcessingException(exchange, e))
                .then();
    }

    private Mono<Void> handleMethodNotAllowedException(ServerWebExchange exchange, MethodNotAllowedException ex) {
        return Mono.fromCallable(() -> {
                    prepareExchange(exchange, HttpStatus.METHOD_NOT_ALLOWED);
                    return GENERIC_METHOD_NOT_ALLOWED.getMessage();
                }).map(message -> buildError(HttpStatus.METHOD_NOT_ALLOWED, message))
                .doFirst(() -> log.error("===== MethodNotAllowedException: [{}] is not allowed at [{}]",
                        exchange.getRequest().getMethod(), exchange.getRequest().getPath().value(), ex))
                .flatMap(response -> writeResponse(exchange, response));
    }

    private Mono<Void> handleReactiveFlashcardsException(ServerWebExchange exchange, ReactiveFlashcardsException ex) {
        return Mono.fromCallable(() -> {
                    prepareExchange(exchange, HttpStatus.INTERNAL_SERVER_ERROR);
                    return GENERIC_EXCEPTION.getMessage();
                }).map(message -> buildError(HttpStatus.INTERNAL_SERVER_ERROR, message))
                .doFirst(() -> log.error("===== ReactiveFlashcardsException", ex))
                .flatMap(response -> writeResponse(exchange, response));
    }

    private Mono<Void> handleException(ServerWebExchange exchange, Exception ex) {
        return Mono.fromCallable(() -> {
            prepareExchange(exchange, HttpStatus.INTERNAL_SERVER_ERROR);
            return GENERIC_EXCEPTION.getMessage();
        }).map(message -> buildError(HttpStatus.INTERNAL_SERVER_ERROR, message))
                .doFirst(() -> log.error("===== Exception", ex))
                .flatMap(response -> writeResponse(exchange, response));
    }

    private Mono<Void> handleJsonProcessingException(ServerWebExchange exchange, JsonProcessingException ex) {
        return Mono.fromCallable(() -> {
                    prepareExchange(exchange, HttpStatus.METHOD_NOT_ALLOWED);
                    return GENERIC_METHOD_NOT_ALLOWED.getMessage();
                }).map(message -> buildError(HttpStatus.METHOD_NOT_ALLOWED, message))
                .doFirst(() -> log.error("===== JsonProcessingException: Failed to map exception for the request {}",
                        exchange.getRequest().getPath().value(), ex))
                .flatMap(response -> writeResponse(exchange, response));
    }

    private Mono<Void> writeResponse(ServerWebExchange exchange, ProblemResponse response) {
        return exchange.getResponse()
                .writeWith(Mono.fromCallable(() -> new DefaultDataBufferFactory().wrap(mapper.writeValueAsBytes(response))));
    }

    private void prepareExchange(ServerWebExchange exchange, HttpStatus status){
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(APPLICATION_JSON);
    }

    private ProblemResponse buildError(HttpStatus status, String errorDescription) {
        return ProblemResponse.builder()
                .status(status.value())
                .errorDescription(errorDescription)
                .build();
    }

}
