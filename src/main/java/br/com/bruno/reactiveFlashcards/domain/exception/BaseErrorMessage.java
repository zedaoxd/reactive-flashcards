package br.com.bruno.reactiveFlashcards.domain.exception;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;

import java.text.MessageFormat;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class BaseErrorMessage {

    private final String DEFAULT_RESOURCE = "messages";
    public static final BaseErrorMessage GENERIC_EXCEPTION = new BaseErrorMessage("generic");
    public static final BaseErrorMessage GENERIC_NOT_FOUND = new BaseErrorMessage("generic.notFound");
    public static final BaseErrorMessage GENERIC_METHOD_NOT_ALLOWED = new BaseErrorMessage("generic.methodNotAllowed");
    public static final BaseErrorMessage GENERIC_BAD_REQUEST = new BaseErrorMessage("generic.badRequest");
    public static final BaseErrorMessage USER_NOT_FOUND = new BaseErrorMessage("user.notFound");
    public static final BaseErrorMessage DECK_NOT_FOUND = new BaseErrorMessage("deck.notFound");
    public static final BaseErrorMessage EMAIL_ALREADY_EXISTS = new BaseErrorMessage("email.alreadyExists");
    public static final BaseErrorMessage STUDY_DECK_NOT_FOUND = new BaseErrorMessage("studyDeck.notFound");
    public static final BaseErrorMessage STUDY_NOT_FOUND = new BaseErrorMessage("study.notFound");
    public static final BaseErrorMessage DECK_IN_STUDY = new BaseErrorMessage("study.deckInStudy");
    public static final BaseErrorMessage STUDY_QUESTION_NOT_FOUND = new BaseErrorMessage("studyQuestion.notFound");
    public static final BaseErrorMessage GENERIC_MAX_RETRIES = new BaseErrorMessage("generic.maxRetries");

    private final String key;
    private String[] params;

    public BaseErrorMessage params(String... params) {
        this.params = params;
        return this;
    }

    public String getMessage() {
        var message = tryGetMessageFromBundle();
        if (ArrayUtils.isNotEmpty(params)) {
            final var fmt = new MessageFormat(message);
            message = fmt.format(params);
        }
        return message;
    }

    private String tryGetMessageFromBundle() {
        return getResourceBundle().getString(key);
    }

    public ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle(DEFAULT_RESOURCE);
    }
}
