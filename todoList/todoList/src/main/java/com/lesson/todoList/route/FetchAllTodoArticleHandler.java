package com.lesson.todoList.route;

import com.lesson.todoList.domain.TodoArticle;
import com.lesson.todoList.service.TodoArticleService;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.Collection;
import java.util.stream.Collectors;

public class FetchAllTodoArticleHandler implements HandlerFunction<ServerResponse> {

    private final TodoArticleService service;

    public FetchAllTodoArticleHandler(TodoArticleService service) {
        this.service = service;
    }

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        return service.findAll()
                .collectList()
                .flatMap(new Function<List<TodoArticle>, Mono<? extends ServerResponse>>() {
                    @Override
                    public Mono<? extends ServerResponse> apply(List<TodoArticle> todoArticle) {
                        return ServerResponse.ok()
                                .body(BodyInserters.fromValue(Response.from(todoArticle)));
                    }
                });
    }

    @Data
    @Builder
    private static final class Response {

        private final List<Entry> entries;

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
        private final LocalDate dueDate;

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
