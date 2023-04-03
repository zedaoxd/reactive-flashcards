package br.com.bruno.reactiveFlashcards.api.controller;

import br.com.bruno.reactiveFlashcards.api.controller.request.StudyRequest;
import br.com.bruno.reactiveFlashcards.api.controller.response.QuestionResponse;
import br.com.bruno.reactiveFlashcards.api.mapper.StudyMapper;
import br.com.bruno.reactiveFlashcards.core.validation.MongoId;
import br.com.bruno.reactiveFlashcards.domain.service.StudyService;
import br.com.bruno.reactiveFlashcards.domain.service.query.StudyQueryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/studies")
@Slf4j
@AllArgsConstructor
public class StudyController {

    private final StudyService studyService;
    private final StudyQueryService studyQueryService;
    private final StudyMapper studyMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<QuestionResponse> start(@Valid @RequestBody StudyRequest request) {
        return studyService.start(studyMapper.toDocument(request))
                .doFirst(() -> log.info("Starting study: {}", request))
                .map(document -> studyMapper.toResponse(document.getLastPendingQuestion(), document.id()));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "{id}")
    public Mono<QuestionResponse> getCurrentQuestion(@Valid @PathVariable @MongoId(message = "{studyController.id}") String id) {
        return studyQueryService.getLastPendingQuestion(id)
                .doFirst(() -> log.info("Getting current question for study: {}", id))
                .map(question -> studyMapper.toResponse(question, id));
    }
}
