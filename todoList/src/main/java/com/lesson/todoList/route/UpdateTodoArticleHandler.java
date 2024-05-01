package com.lesson.todoList.route;

import com.lesson.todoList.config.RouteConfig;
import com.lesson.todoList.domain.TodoArticle;
import com.lesson.todoList.service.TodoArticleService;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

public class UpdateTodoArticleHandler implements HandlerFunction<ServerResponse> {

    private final TodoArticleService service;

    public UpdateTodoArticleHandler(TodoArticleService service) {
        this.service = service;
    }

    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Request.class)
                .flatMap(new Function<Request, Mono<? extends TodoArticle>>() {
                    @Override
                    public Mono<? extends TodoArticle> apply(Request request) {
                        return service.update(TodoArticle.builder()
                                .id(UUID.fromString(serverRequest.pathVariable(RouteConfig.ID_VARIABLE)))
                                .description(request.getDescription())
                                .status(request.getStatus())
                                .dueDate(request.getDueDate())
                                .build());
                    }
                }).flatMap(new Function<TodoArticle, Mono<? extends ServerResponse>>() {
                    @Override
                    public Mono<? extends ServerResponse> apply(TodoArticle todoArticle) {
                        return ServerResponse.ok()
                                .body(BodyInserters.fromValue(Response.from(todoArticle)));
                    }
                });
    }

    @Data
    @Builder
    private static final class Request {
        private UUID id;
        private String description;
        private TodoArticle.Status status;
        private Date dueDate;
    }

    @Data
    @Builder
    private static final class Response {
        private UUID id;
        private String description;
        private TodoArticle.Status status;
        private Date dueDate;

        public static Response from(TodoArticle todo) {
            return builder()
                    .id(todo.getId())
                    .description(todo.getDescription())
                    .status(todo.getStatus())
                    .dueDate(todo.getDueDate())
                    .build();
        }
    }
}
