package br.com.bruno.reactiveFlashcards.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

public record UserPageResponse(@JsonProperty("currentPage")
                               @Schema(description = "Current page number", example = "1")
                               Long currentPage,
                               @JsonProperty("totalPages")
                               @Schema(description = "Total pages", example = "1")
                               Long totalPages,
                               @JsonProperty("totalItems")
                               @Schema(description = "Total items", example = "1")
                               Long totalItems,
                               @JsonProperty("content")
                               @Schema(description = "Content")
                               List<UserResponse> content) {

    public static UserPageResponseBuilder builder(){
        return new UserPageResponseBuilder();
    }

    public UserPageResponseBuilder toBuilder(Integer limit){
        return new UserPageResponseBuilder(limit, currentPage, totalItems, content);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserPageResponseBuilder{
        private Integer limit;
        private Long currentPage;
        private Long totalItems;
        private List<UserResponse> content;

        public UserPageResponseBuilder limit(Integer limit){
            this.limit = limit;
            return this;
        }

        public UserPageResponseBuilder currentPage(Long currentPage){
            this.currentPage = currentPage;
            return this;
        }

        public UserPageResponseBuilder totalItems(Long totalItems){
            this.totalItems = totalItems;
            return this;
        }

        public UserPageResponseBuilder content(List<UserResponse> content){
            this.content = content;
            return this;
        }

        public UserPageResponse build(){
            var totalPages = (totalItems / limit) + ((totalItems % limit > 0) ? 1 : 0);
            return new UserPageResponse(currentPage, totalPages, totalItems, content);
        }

    }

}