package com.lesson.todoList.config;

import com.lesson.todoList.service.TodoArticleService;
import com.lesson.todoList.route.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouteConfig {
    public static final String TODO_PATH = "/todo";
    public static final String ID_VARIABLE = "id";
    public static final String STATUS_VARIABLE = "status";
    public static final String FIND_TODO_BY_ID_PATH = TODO_PATH + "/{" + ID_VARIABLE + "}";
    public static final String FIND_TODO_BY_STATUS_PATH = TODO_PATH + "/{" + STATUS_VARIABLE + "}";
    private final TodoArticleService todoArticleService;

    @Autowired
    public RouteConfig(TodoArticleService todoArticleService) {
        this.todoArticleService = todoArticleService;
    }

    @Bean
    public RouterFunction<ServerResponse> fetchAllTodoArticleRoute() {
        return route(GET(TODO_PATH), fetchAllTodoArticleHandler());
    }

    @Bean
    public HandlerFunction<ServerResponse> fetchAllTodoArticleHandler() {
        return new FetchAllTodoArticleHandler(todoArticleService);
    }

    @Bean
    public RouterFunction<ServerResponse> createTodoRoute() {
        return route(POST(TODO_PATH), createTodoArticleHandler());
    }

    @Bean
    public HandlerFunction<ServerResponse> createTodoArticleHandler() {
        return new CreateTodoArticleHandler(todoArticleService);
    }

    @Bean
    public RouterFunction<ServerResponse> fetchTodoArticleByIdRoute() {
        return route(GET(FIND_TODO_BY_ID_PATH), fetchTodoArticleByIdHandler());
    }

    @Bean
    public HandlerFunction<ServerResponse> fetchTodoArticleByIdHandler() {
        return new FetchTodoArticleByIDHandler(todoArticleService);
    }

    @Bean
    public RouterFunction<ServerResponse> fetchTodoArticleByStatusRoute() {
        return route(GET(FIND_TODO_BY_STATUS_PATH), fetchTodoArticleByStatusHandler());
    }

    @Bean
    public HandlerFunction<ServerResponse> fetchTodoArticleByStatusHandler() {
        return new FetchTodoArticleByStatusHandler(todoArticleService);
    }

    @Bean
    public RouterFunction<ServerResponse> updateTodoArticleRoute() {
        return route(PUT(FIND_TODO_BY_ID_PATH), updateTodoArticleHandler());
    }

    @Bean
    public HandlerFunction<ServerResponse> updateTodoArticleHandler() {
        return new UpdateTodoArticleHandler(todoArticleService);
    }

    @Bean
    public RouterFunction<ServerResponse> deleteTodoArticleRoute() {
        return route(DELETE(FIND_TODO_BY_ID_PATH), deleteTodoArticleHandler());
    }

    @Bean
    public HandlerFunction<ServerResponse> deleteTodoArticleHandler() {
        return new DeleteTodoArticleHandler(todoArticleService);
    }
}
