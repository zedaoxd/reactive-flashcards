package br.com.bruno.reactiveFlashcards.api.exceptionhandler;

import br.com.bruno.reactiveFlashcards.domain.exception.EmailAlreadyExistsException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class EmailAlreadyExistsHandler extends AbstractHandlerException<EmailAlreadyExistsException> {

    public EmailAlreadyExistsHandler(ObjectMapper mapper) {
        super(mapper);
    }

    @Override
    public Mono<Void> handlerException(ServerWebExchange exchange, EmailAlreadyExistsException ex) {
        return Mono.fromCallable(() -> {
                    prepareExchange(exchange, HttpStatus.BAD_REQUEST);
                    return ex.getMessage();
                }).map(message -> buildError(HttpStatus.BAD_REQUEST, message))
                .doFirst(() -> log.error("===== EmailAlreadyExistsException", ex))
                .flatMap(response -> writeResponse(exchange, response));
    }
}
