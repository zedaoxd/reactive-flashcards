package br.com.bruno.reactiveFlashcards.api.controller.documentation;

import br.com.bruno.reactiveFlashcards.api.controller.request.UserPageRequest;
import br.com.bruno.reactiveFlashcards.api.controller.request.UserRequest;
import br.com.bruno.reactiveFlashcards.api.controller.response.UserPageResponse;
import br.com.bruno.reactiveFlashcards.api.controller.response.UserResponse;
import br.com.bruno.reactiveFlashcards.core.validation.MongoId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Tag(name = "User", description = "Endpoints to manage users")
public interface IDocUserController {

    @Operation(summary = "Save a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "409", description = "User already exists")
    })
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    Mono<UserResponse> save(@Valid @RequestBody UserRequest request);

    @Operation(summary = "Find paginated users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    Mono<UserPageResponse> findOnDemand(@Valid UserPageRequest request);

    @Operation(summary = "Find a user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "/{id}")
    Mono<UserResponse> findById(@Parameter(description = "identifier to user")
                                @Valid @PathVariable @MongoId(message = "{userController.id}") String id);

    @Operation(summary = "Update a user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE, value = "/{id}")
    Mono<UserResponse> update(@Valid @PathVariable @MongoId(message = "{userController.id}") String id,
                              @Valid @RequestBody UserRequest request);

    @Operation(summary = "Delete a user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(NO_CONTENT)
    Mono<Void> delete(@Valid @PathVariable @MongoId(message = "{userController.id}") String id);
}
