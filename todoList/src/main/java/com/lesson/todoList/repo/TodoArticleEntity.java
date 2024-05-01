package com.lesson.todoList.repo;

import com.lesson.todoList.domain.TodoArticle;
import com.lesson.todoList.domain.Item;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.util.UUID;
import java.util.Date;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@Table("todo")
public class TodoArticleEntity {

    @Id
    private UUID id;

    @Column
    private String description;

    @Column
    private Date dueDate;

    @Column
    private TodoArticle.Status status;

    @Column
    private Instant created;

    @Column
    private Instant updated;

    public static TodoArticleEntity from(TodoArticle todo) {
        return TodoArticleEntity.builder()
                .id(todo.getId())
                .description(todo.getDescription())
                .dueDate(todo.getDueDate())
                .status(todo.getStatus())
                .build();
    }

}
