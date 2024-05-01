package com.lesson.todoList.route;

import com.lesson.todoList.config.RouteConfig;
import com.lesson.todoList.domain.TodoArticle;
import com.lesson.todoList.service.TodoArticleService;
import lombok.Builder;
import lombok.Data;
import org.reactivestreams.Publisher;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.Date;
import java.util.Collection;
import java.util.stream.Collectors;

public class FetchTodoArticleByStatusHandler implements HandlerFunction<ServerResponse> {

    private final TodoArticleService service;

    public FetchTodoArticleByStatusHandler(TodoArticleService service) {
        this.service = service;
    }

    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        final Request request = Request.from(serverRequest);
        return service.findByStatus(request.getStatus())
                .collectList()
                .flatMap(new Function<List<TodoArticle>, Mono<? extends ServerResponse>>() {
                    @Override
                    public Mono<? extends ServerResponse> apply(List<TodoArticle> todos) {
                        return ServerResponse.ok()
                                .body(BodyInserters.fromValue(Response.from(todos)));
                    }
                })
                .switchIfEmpty(ServerResponse.notFound()
                        .build());
    }

    @Data
    @Builder
    private static final class Request {
        private TodoArticle.Status status;

        public static Request from(ServerRequest request) {
            return builder()
                    .status(TodoArticle.Status.valueOf(request.pathVariable(RouteConfig.STATUS_VARIABLE)))
                    .build();
        }
    }

    @Data
    @Builder
    private static final class Response {
        private List<Entry> entries;

        public static Response from(Collection<TodoArticle> todos) {
            return builder()
                    .entries(todos.stream()
                            .map(Entry::from)
                            .collect(Collectors.toList()))
                    .build();
        }
    }

    @Data
    @Builder
    private static final class Entry {

        private final UUID id;
        private final String description;
        private final TodoArticle.Status status;
        private final Date dueDate;

        public static Entry from(TodoArticle todo) {
            return builder()
                    .id(todo.getId())
                    .description(todo.getDescription())
                    .status(todo.getStatus())
                    .dueDate(todo.getDueDate())
                    .build();
        }
    }
}
