package com.lesson.todoList.route;

import com.lesson.todoList.config.RouteConfig;
import lombok.Builder;
import lombok.Data;

import com.lesson.todoList.domain.TodoArticle;
import com.lesson.todoList.service.TodoArticleService;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.Function;

public class DeleteTodoArticleHandler implements HandlerFunction<ServerResponse> {

    private final TodoArticleService service;

    public DeleteTodoArticleHandler(TodoArticleService service) {
        this.service = service;
    }

    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        final Request request = Request.from(serverRequest);
        return service.delete(request.getId())
                .flatMap(new Function<Void, Mono<? extends ServerResponse>>() {
                    @Override
                    public Mono<? extends ServerResponse> apply(Void unused) {
                        return ServerResponse.ok()
                                .build();
                    }
                });
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
}
