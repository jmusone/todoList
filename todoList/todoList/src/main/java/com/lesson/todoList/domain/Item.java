package com.lesson.todoList.domain;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import java.util.UUID;

@Data
@SuperBuilder
public class Item implements Trackable {
    public final UUID id;
    public final String description;
}
