package com.lesson.todoList.domain;

import com.lesson.todoList.repo.TodoArticleEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import java.util.Date;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TodoArticle extends Item {
    private final Date dueDate;
    private final Status status;

    public enum Status {
        TODO,
        INPROGRESS,
        DONE,
        CANCELLED
    }

    public static TodoArticle from(TodoArticleEntity entity) {
        return TodoArticle.builder()
                    .id(entity.getId())
                    .description(entity.getDescription())
                    .dueDate(entity.getDueDate())
                    .status(entity.getStatus())
                    .build();
    }
}
