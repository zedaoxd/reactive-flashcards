package br.com.bruno.reactiveFlashcards.domain.service;

import br.com.bruno.reactiveFlashcards.domain.document.UserDocument;
import br.com.bruno.reactiveFlashcards.domain.exception.EmailAlreadyExistsException;
import br.com.bruno.reactiveFlashcards.domain.exception.NotFoundException;
import br.com.bruno.reactiveFlashcards.domain.repository.UserRepository;
import br.com.bruno.reactiveFlashcards.domain.service.query.UserQueryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static br.com.bruno.reactiveFlashcards.domain.exception.BaseErrorMessage.EMAIL_ALREADY_EXISTS;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserQueryService userQueryService;

    public Mono<UserDocument> save(final UserDocument user){
        return userQueryService.findByEmail(user.email())
                .doFirst(() -> log.info("Saving user: {}", user))
                .filter(Objects::isNull)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new EmailAlreadyExistsException(EMAIL_ALREADY_EXISTS
                        .params(user.email()).getMessage()))))
                .onErrorResume(NotFoundException.class, e -> userRepository.save(user));
    }

    public Mono<UserDocument> update(final UserDocument document){
        return userQueryService.findById(document.id())
                .map(user -> document.toBuilder()
                        .createdAt(user.createdAt())
                        .updatedAt(user.updatedAt())
                        .build())
                .flatMap(userRepository::save)
                .doFirst(() -> log.info("Updating user: {}", document));
    }

    public Mono<Void> delete(final String id){
        return userQueryService.findById(id)
                .flatMap(userRepository::delete)
                .doFirst(() -> log.info("Deleting user: {}", id));
    }
}
