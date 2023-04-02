package br.com.bruno.reactiveFlashcards.api.exceptionhandler;

import br.com.bruno.reactiveFlashcards.domain.exception.DeckInStudyException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class DeckInStudyHandler extends AbstractHandlerException<DeckInStudyException> {
    public DeckInStudyHandler(ObjectMapper mapper) {
        super(mapper);
    }

    @Override
    Mono<Void> handlerException(ServerWebExchange exchange, DeckInStudyException ex) {
        return Mono.fromCallable(() -> {
                    prepareExchange(exchange, HttpStatus.CONFLICT);
                    return ex.getMessage();
                }).map(message -> buildError(HttpStatus.CONFLICT, message))
                .doFirst(() -> log.error("===== DeckInStudyException", ex))
                .flatMap(response -> writeResponse(exchange, response));
    }
}
