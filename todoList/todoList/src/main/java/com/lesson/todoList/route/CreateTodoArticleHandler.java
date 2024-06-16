package com.lesson.todoList.route;

import com.lesson.todoList.domain.TodoArticle;
import com.lesson.todoList.service.TodoArticleService;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Function;

public class CreateTodoArticleHandler implements HandlerFunction<ServerResponse> {

    private final TodoArticleService service;
    public CreateTodoArticleHandler(TodoArticleService service) {
        this.service = service;
    }

    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Request.class)
                .flatMap(new Function<Request, Mono<? extends TodoArticle>>() {
                    @Override
                    public Mono<? extends TodoArticle> apply(Request request) {
                        return service.create(TodoArticle.builder()
                                .description(request.getDescription())
                                .status(request.getStatus())
                                .dueDate(request.getDueDate())
                                .build());
                    }
                })
                .flatMap(new Function<TodoArticle, Mono<? extends ServerResponse>>() {
                    @Override
                    public Mono<? extends ServerResponse> apply(TodoArticle todoArticle) {
                        return ServerResponse.ok()
                                .body(BodyInserters.fromValue(Response.from(todoArticle)));
                    }
                });
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static final class Request {
        private String description;
        private TodoArticle.Status status;
        private LocalDate dueDate;
    }

    @Data
    @Builder
    private static final class Response {
        private final UUID id;
        private TodoArticle.Status status;
        private LocalDate dueDate;

        public static Response from(TodoArticle todo) {
            return builder()
                    .id(todo.getId())
                    .status(todo.getStatus())
                    .dueDate(todo.getDueDate())
                    .build();
        }
    }
}
