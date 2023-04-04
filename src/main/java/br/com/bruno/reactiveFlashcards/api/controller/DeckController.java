package br.com.bruno.reactiveFlashcards.api.controller;

import br.com.bruno.reactiveFlashcards.api.controller.request.DeckRequest;
import br.com.bruno.reactiveFlashcards.api.controller.response.DeckResponse;
import br.com.bruno.reactiveFlashcards.api.mapper.DeckMapper;
import br.com.bruno.reactiveFlashcards.core.validation.MongoId;
import br.com.bruno.reactiveFlashcards.domain.service.DeckService;
import br.com.bruno.reactiveFlashcards.domain.service.query.DeckQueryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequestMapping("decks")
@Slf4j
@AllArgsConstructor
public class DeckController {

    public final DeckService deckService;
    public final DeckQueryService deckQueryService;
    public final DeckMapper deckMapper;


    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<DeckResponse> save(@Valid @RequestBody final DeckRequest request){
        return deckService.save(deckMapper.toDocument(request))
                .doFirst(() -> log.info("==== Saving a deck with follow data {}", request))
                .map(deckMapper::toResponse);
    }

    @PostMapping(value = "sync")
    public Mono<Void> sync(){
        return deckService.sync();
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "{id}")
    public Mono<DeckResponse> findById(@PathVariable @Valid @MongoId(message = "{deckController.id}") final String id){
        return deckQueryService.findById(id)
                .doFirst(() -> log.info("==== Finding a deck with follow id {}", id))
                .map(deckMapper::toResponse);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Flux<DeckResponse> findAll(){
        return deckQueryService.findAll()
                .doFirst(() -> log.info("==== Finding all decks"))
                .map(deckMapper::toResponse);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE, value = "{id}")
    public Mono<DeckResponse> update(@PathVariable @Valid @MongoId(message = "{deckController.id}") final String id,
                                     @Valid @RequestBody final DeckRequest request){
        return deckService.update(deckMapper.toDocument(request, id))
                .doFirst(() -> log.info("==== Updating a deck with follow info [body: {}, id: {}]", request, id))
                .map(deckMapper::toResponse);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(NO_CONTENT)
    public Mono<Void> delete(@PathVariable @Valid @MongoId(message = "{deckController.id}") final String id){
        return deckService.delete(id)
                .doFirst(() -> log.info("==== Deleting a user with follow id {}", id));
    }

}

