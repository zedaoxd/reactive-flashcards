package br.com.bruno.reactiveFlashcards.api.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProblemResponse(@JsonProperty("status")
                              @Schema(description = "HTTP status code", example = "400")
                              Integer status,
                              @JsonProperty("timestamp")
                              @Schema(description = "Timestamp of the error", example = "2021-08-01T12:00:00Z", format = "date-time")
                              OffsetDateTime timestamp,
                              @JsonProperty("errorDescription")
                              @Schema(description = "Description of the error", example = "Invalid fields")
                              String errorDescription,
                              @JsonProperty("fields")
                              @Schema(description = "List of fields that have errors")
                              List<ErrorFieldResponse> fields) {

    @Builder(toBuilder = true)
    public ProblemResponse {}
}
