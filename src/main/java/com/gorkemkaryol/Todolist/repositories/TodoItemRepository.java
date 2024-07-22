package com.gorkemkaryol.Todolist.repositories;

import com.gorkemkaryol.Todolist.models.TodoItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoItemRepository extends CrudRepository<TodoItem, Long> {
}
