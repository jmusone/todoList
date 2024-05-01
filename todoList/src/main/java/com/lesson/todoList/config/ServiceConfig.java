package com.lesson.todoList.config;

import com.lesson.todoList.repo.TodoRepository;
import com.lesson.todoList.service.TodoArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    private final TodoRepository repository;

    @Autowired
    public ServiceConfig(TodoRepository repository) {
        this.repository = repository;
    }

    @Bean
    public TodoArticleService todoArticleService() {
        return new TodoArticleService(repository);
    }
}
