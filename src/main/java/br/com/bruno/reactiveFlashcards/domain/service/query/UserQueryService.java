package br.com.bruno.reactiveFlashcards.domain.service.query;

import br.com.bruno.reactiveFlashcards.domain.document.UserDocument;
import br.com.bruno.reactiveFlashcards.domain.exception.NotFoundException;
import br.com.bruno.reactiveFlashcards.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static br.com.bruno.reactiveFlashcards.domain.exception.BaseErrorMessage.USER_NOT_FOUND;

@Service
@AllArgsConstructor
@Slf4j
public class UserQueryService {

    private final UserRepository userRepository;

    public Mono<UserDocument> findById(String id) {
        return userRepository.findById(id)
                .doFirst(() -> log.info("Finding user by id: {}", id))
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException(USER_NOT_FOUND.params("id",id).getMessage()))));
    }

    public Mono<UserDocument> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .doFirst(() -> log.info("Finding user by email: {}", email))
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException(USER_NOT_FOUND.params("email", email).getMessage()))));
    }
}
