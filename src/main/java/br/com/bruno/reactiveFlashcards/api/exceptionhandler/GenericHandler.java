package br.com.bruno.reactiveFlashcards.api.exceptionhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static br.com.bruno.reactiveFlashcards.domain.exception.BaseErrorMessage.GENERIC_EXCEPTION;

@Slf4j
@Component
public class GenericHandler extends AbstractHandlerException<Exception>{
    public GenericHandler(ObjectMapper mapper) {
        super(mapper);
    }

    @Override
    public Mono<Void> handlerException(ServerWebExchange exchange, Exception ex) {
        return Mono.fromCallable(() -> {
                    prepareExchange(exchange, HttpStatus.INTERNAL_SERVER_ERROR);
                    return GENERIC_EXCEPTION.getMessage();
                }).map(message -> buildError(HttpStatus.INTERNAL_SERVER_ERROR, message))
                .doFirst(() -> log.error("===== Exception", ex))
                .flatMap(response -> writeResponse(exchange, response));
    }
}
