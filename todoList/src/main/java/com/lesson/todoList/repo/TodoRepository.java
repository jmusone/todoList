package com.lesson.todoList.repo;

import com.lesson.todoList.domain.TodoArticle;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;
@Repository
public interface TodoRepository extends R2dbcRepository<TodoArticleEntity, UUID> {
    @Query("SELECT * FROM todo WHERE status = $0;")
    Flux<TodoArticleEntity> findByStatus(TodoArticle.Status status);
}
