package br.com.bruno.reactiveFlashcards.domain.exception;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;

import java.text.MessageFormat;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class BaseErrorMessage {

    private final String DEFAULT_RESOURCE = "messages";
    public static final BaseErrorMessage GENERIC_EXCEPTION = new BaseErrorMessage("generic.exception");
    public static final BaseErrorMessage GENERIC_NOT_FOUND = new BaseErrorMessage("generic.not-found");
    public static final BaseErrorMessage GENERIC_METHOD_NOT_ALLOWED = new BaseErrorMessage("generic.method-not-allowed");
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
