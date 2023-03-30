package br.com.bruno.reactiveFlashcards.api.exceptionhandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static br.com.bruno.reactiveFlashcards.domain.exception.BaseErrorMessage.GENERIC_NOT_FOUND;

@Slf4j
@Component
public class JsonProcessingHandler extends AbstractHandlerException<JsonProcessingException>{
    public JsonProcessingHandler(ObjectMapper mapper) {
        super(mapper);
    }

    @Override
    public Mono<Void> handlerException(ServerWebExchange exchange, JsonProcessingException ex) {
        return Mono.fromCallable(() -> {
                    prepareExchange(exchange, HttpStatus.NOT_FOUND);
                    return GENERIC_NOT_FOUND.getMessage();
                }).map(message -> buildError(HttpStatus.NOT_FOUND, message))
                .doFirst(() -> log.error("===== ResponseStatusException", ex))
                .flatMap(response -> writeResponse(exchange, response));
    }
}
