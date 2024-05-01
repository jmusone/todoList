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

public class FetchTodoArticleByIDHandler implements HandlerFunction<ServerResponse> {

    private final TodoArticleService service;

    public FetchTodoArticleByIDHandler(TodoArticleService service) {
        this.service = service;
    }

    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        final Request request = Request.from(serverRequest);
        return service.findById(request.getId())
                .flatMap(new Function<TodoArticle, Mono<? extends ServerResponse>>() {
                    @Override
                    public Mono<? extends ServerResponse> apply(TodoArticle todo) {
                        return ServerResponse.ok()
                                .body(BodyInserters.fromValue(Response.from(todo)));
                    }
                })
                .switchIfEmpty(ServerResponse.notFound()
                        .build());
    }

    @Data
    @Builder
    private static final class Request {
        private UUID id;

        public static Request from(ServerRequest request) {
            return builder()
                    .id(UUID.fromString(request.pathVariable(RouteConfig.ID_VARIABLE)))
                    .build();
        }
    }

    @Data
    @Builder
    private static final class Response {
        private final UUID id;
        private final String description;
        private final TodoArticle.Status status;
        private final Date dueDate;

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
