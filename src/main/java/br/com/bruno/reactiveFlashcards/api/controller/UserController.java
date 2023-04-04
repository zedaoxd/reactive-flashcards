package br.com.bruno.reactiveFlashcards.api.controller;

import br.com.bruno.reactiveFlashcards.api.controller.request.UserPageRequest;
import br.com.bruno.reactiveFlashcards.api.controller.request.UserRequest;
import br.com.bruno.reactiveFlashcards.api.controller.response.UserPageResponse;
import br.com.bruno.reactiveFlashcards.api.controller.response.UserResponse;
import br.com.bruno.reactiveFlashcards.api.mapper.UserMapper;
import br.com.bruno.reactiveFlashcards.core.validation.MongoId;
import br.com.bruno.reactiveFlashcards.domain.service.UserService;
import br.com.bruno.reactiveFlashcards.domain.service.query.UserQueryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequestMapping("/users")
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserQueryService userQueryService;
    private final UserMapper userMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<UserResponse> save(@Valid @RequestBody UserRequest request) {
        return userService.save(userMapper.toDocument(request))
                .doFirst(() -> log.info("Saving user: {}", request))
                .map(userMapper::toResponse);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Mono<UserPageResponse> findOnDemand(@Valid final UserPageRequest request){
        return userQueryService.findOnDemand(request)
                .doFirst(() -> log.info("==== Finding users on demand with follow request {}", request))
                .map(page -> userMapper.toResponse(page, request.limit()));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "/{id}")
    public Mono<UserResponse> findById(@Valid @PathVariable @MongoId(message = "{userController.id}") String id) {
        return userQueryService.findById(id)
                .doFirst(() -> log.info("Finding user by id: {}", id))
                .map(userMapper::toResponse);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE, value = "/{id}")
    public Mono<UserResponse> update(@Valid @PathVariable @MongoId(message = "{userController.id}") String id,
                                     @Valid @RequestBody UserRequest request) {
        return userService.update(userMapper.toDocument(request, id))
                .doFirst(() -> log.info("Updating user: {}", request))
                .map(userMapper::toResponse)
        ;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(NO_CONTENT)
    public Mono<Void> delete(@Valid @PathVariable @MongoId(message = "{userController.id}") String id) {
        return userService.delete(id)
                .doFirst(() -> log.info("Deleting user: {}", id));
    }

}
