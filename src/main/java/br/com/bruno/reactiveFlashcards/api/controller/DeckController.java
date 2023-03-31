package br.com.bruno.reactiveFlashcards.api.controller;

import br.com.bruno.reactiveFlashcards.api.controller.request.DeckRequest;
import br.com.bruno.reactiveFlashcards.api.controller.response.DeckResponse;
import br.com.bruno.reactiveFlashcards.api.mapper.DeckMapper;
import br.com.bruno.reactiveFlashcards.domain.service.DeckService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequestMapping("/decks")
@Slf4j
@AllArgsConstructor
public class DeckController {

    private final DeckService deckService;
    private final DeckMapper deckMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<DeckResponse> save(@Valid @RequestBody DeckRequest request) {
        return deckService.save(deckMapper.toDocument(request))
                .doFirst(() -> log.info("Saving deck: {}", request))
                .map(deckMapper::toResponse);
    }

}
