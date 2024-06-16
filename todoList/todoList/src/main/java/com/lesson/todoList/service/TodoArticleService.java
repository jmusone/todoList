package com.lesson.todoList.service;

import com.lesson.todoList.domain.TodoArticle;
import com.lesson.todoList.repo.TodoArticleEntity;
import com.lesson.todoList.repo.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
public class TodoArticleService implements ItemService<TodoArticle> {

    private final TodoRepository repository;
    public TodoArticleService(TodoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<TodoArticle> findAll() {
        return repository.findAll()
                .map(TodoArticle::from);
    }

    @Override
    public Mono<TodoArticle> findById(UUID id) {
        return repository.findById(id)
                .map(TodoArticle::from);
    }

    @Override
    public Mono<TodoArticle> create(TodoArticle item) {
        return repository.save(TodoArticleEntity.from(item)
                .toBuilder()
                .created(Instant.now())
                .build())
                .map(TodoArticle::from);
    }

    @Override
    public Mono<TodoArticle> update(TodoArticle item) {
        return repository.save(TodoArticleEntity.from(item)
                .toBuilder()
                        .description(item.getDescription())
                        .dueDate(item.getDueDate())
                        .status(item.getStatus())
                .updated(Instant.now())
                .build())
                .map(TodoArticle::from);
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return repository.deleteById(id).then();
    }

    public Flux<TodoArticle> findByStatus(TodoArticle.Status status) {
        return repository.findByStatus(status)
                .map(TodoArticle::from);
    }
}
