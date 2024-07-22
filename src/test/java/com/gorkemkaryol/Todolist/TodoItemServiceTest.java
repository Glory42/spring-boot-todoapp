package com.gorkemkaryol.Todolist;

import com.gorkemkaryol.Todolist.models.TodoItem;
import com.gorkemkaryol.Todolist.repositories.TodoItemRepository;
import com.gorkemkaryol.Todolist.services.TodoItemService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class TodoItemServiceTest {

	@Autowired
	private TodoItemService service;

	@Autowired
	private TodoItemRepository repository;

	@AfterEach
	void deleteAllItems() {
		repository.deleteAll();
	}

	@Test
	@DisplayName("find a given TodoItem by its Id")
	void findATodoItemById() {
		TodoItem item = new TodoItem();
		item.setDescription("todo item1");
		item.setIsComplete(false);
		item = service.save(item);

		Optional<TodoItem> testItem = service.getById(item.getId());
		assertTrue(testItem.isPresent());
		assertEquals(testItem.get().getId(), item.getId());
	}

	@Test
	void getAllTodoItems() {
		TodoItem item1 = new TodoItem();
		item1.setDescription("todo item1");
		item1.setIsComplete(false);
		item1 = service.save(item1);

		TodoItem item2 = new TodoItem();
		item2.setDescription("todo item2");
		item2.setIsComplete(false);
		item2 = service.save(item2);

		Iterable<TodoItem> items = service.getAll();
		List<TodoItem> list = new ArrayList<>();
		items.forEach(list::add);
		assertFalse(list.isEmpty());
		assertEquals(list.size(), 2);
		assertEquals(list.get(0).getId(), item1.getId());
		assertEquals(list.get(1).getId(), item2.getId());
	}

	@Test
	void savingAValidTodoItemSucceeds() {
		TodoItem item = new TodoItem();
		item.setDescription("todo item1");
		item.setIsComplete(false);
		item = service.save(item);
		assertNotNull(item.getId());
	}

	@Test
	void savingAnInvalidTodoItemFails() {
		TodoItem item = new TodoItem();
		Exception exception = assertThrows(Exception.class, () -> service.save(item));
		assertEquals("Could not commit JPA transaction", exception.getMessage());
	}

	@Test
	void deletingAValidTodoItemSucceeds() {
		TodoItem item = new TodoItem();
		item.setDescription("todo item1");
		item.setIsComplete(false);
		item = service.save(item);
		service.delete(item);

		Iterable<TodoItem> items = service.getAll();
		List<TodoItem> list = new ArrayList<>();
		items.forEach(list::add);
		assertTrue(list.isEmpty());
	}
}
