package br.com.bruno.reactiveFlashcards.api.controller.documentation;

import br.com.bruno.reactiveFlashcards.api.controller.request.AnswerQuestionRequest;
import br.com.bruno.reactiveFlashcards.api.controller.request.StudyRequest;
import br.com.bruno.reactiveFlashcards.api.controller.response.AnswerQuestionResponse;
import br.com.bruno.reactiveFlashcards.api.controller.response.QuestionResponse;
import br.com.bruno.reactiveFlashcards.core.validation.MongoId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Study", description = "Endpoints to manage the study of a deck")
public interface IDocStudyController {

    @Operation(summary = "Start a study session", description = "Start a study session for a deck")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Study session started"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Deck not found")
    })
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    Mono<QuestionResponse> start(@Valid @RequestBody StudyRequest request);

    @Operation(summary = "Get current question", description = "Get the current question of a study session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Current question"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Study session not found")
    })
    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "{id}")
    Mono<QuestionResponse> getCurrentQuestion(@Valid @PathVariable @MongoId(message = "{studyController.id}") String id);

    @Operation(summary = "Answer a question", description = "Answer a question of a study session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question answered"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Study session not found")
    })
    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE, value = "{id}/answer")
    Mono<AnswerQuestionResponse> answer(@Valid @PathVariable @MongoId(message = "{studyController.id}") String id,
                                        @Valid @RequestBody AnswerQuestionRequest request);
}
