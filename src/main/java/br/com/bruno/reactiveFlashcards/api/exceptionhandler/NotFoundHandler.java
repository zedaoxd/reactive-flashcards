package br.com.bruno.reactiveFlashcards.api.exceptionhandler;

import br.com.bruno.reactiveFlashcards.domain.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class NotFoundHandler extends AbstractHandlerException<NotFoundException> {

    public NotFoundHandler(ObjectMapper mapper) {
        super(mapper);
    }

    @Override
    public Mono<Void> handlerException(ServerWebExchange exchange, NotFoundException ex) {
        return Mono.fromCallable(() -> {
                    prepareExchange(exchange, HttpStatus.NOT_FOUND);
                    return ex.getMessage();
                }).map(message -> buildError(HttpStatus.NOT_FOUND, message))
                .doFirst(() -> log.error("===== NotFoundException", ex))
                .flatMap(response -> writeResponse(exchange, response));
    }
}
