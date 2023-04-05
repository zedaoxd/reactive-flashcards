package br.com.bruno.reactiveFlashcards.api.controller.documentation;

import br.com.bruno.reactiveFlashcards.api.controller.request.DeckRequest;
import br.com.bruno.reactiveFlashcards.api.controller.response.DeckResponse;
import br.com.bruno.reactiveFlashcards.core.validation.MongoId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Tag(name = "Deck", description = "Endpoints to manage decks")
public interface IDocDeckController {

    @Operation(summary = "Save a new deck")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Deck created"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "409", description = "Deck already exists")
    })
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    Mono<DeckResponse> save(@Valid @RequestBody DeckRequest request);

    @Operation(summary = "Search for decks in external API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Decks found"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping(value = "sync")
    Mono<Void> sync();

    @Operation(summary = "Find a deck by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deck found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Deck not found")
    })
    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "{id}")
    Mono<DeckResponse> findById(@PathVariable @Valid @MongoId(message = "{deckController.id}") String id);

    @Operation(summary = "Find all decks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Decks found")
    })
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    Flux<DeckResponse> findAll();

    @Operation(summary = "Update a deck")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deck updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Deck not found")
    })
    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE, value = "{id}")
    Mono<DeckResponse> update(@PathVariable @Valid @MongoId(message = "{deckController.id}") String id,
                              @Valid @RequestBody DeckRequest request);

    @Operation
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deck deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Deck not found")
    })
    @DeleteMapping(value = "{id}")
    @ResponseStatus(NO_CONTENT)
    Mono<Void> delete(@PathVariable @Valid @MongoId(message = "{deckController.id}") String id);
}
