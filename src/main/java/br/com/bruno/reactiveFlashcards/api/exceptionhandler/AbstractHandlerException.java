package br.com.bruno.reactiveFlashcards.api.exceptionhandler;

import br.com.bruno.reactiveFlashcards.api.controller.response.ProblemResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RequiredArgsConstructor
public abstract class AbstractHandlerException<T extends Exception> {

    private final ObjectMapper mapper;

    abstract Mono<Void> handlerException(ServerWebExchange exchange, T ex);

    protected Mono<Void> writeResponse(ServerWebExchange exchange, ProblemResponse response) {
        return exchange.getResponse()
                .writeWith(Mono.fromCallable(() -> new DefaultDataBufferFactory().wrap(mapper.writeValueAsBytes(response))));
    }

    protected void prepareExchange(ServerWebExchange exchange, HttpStatus status){
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(APPLICATION_JSON);
    }

    protected ProblemResponse buildError(HttpStatus status, String errorDescription) {
        return ProblemResponse.builder()
                .status(status.value())
                .errorDescription(errorDescription)
                .timestamp(OffsetDateTime.now())
                .build();
    }
}
