package com.samir.task_manager_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.samir.task_manager_api.model.Task;
import com.samir.task_manager_api.repository.TaskRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

        @Mock
        private TaskRepository repository;

        @InjectMocks
        private TaskService service;

        @Test
        void shouldReturnAllTasks() {

                List<Task> tasks = List.of(
                        new Task("Task 1", false),
                        new Task("Task 2", true)
                );

                when(repository.findAll())
                        .thenReturn(tasks);

                List<Task> result =
                        service.findAll();

                assertEquals(
                        2,
                        result.size());
        }

        @Test
        void shouldSaveTask() {

                Task task =
                        new Task("Nueva tarea", false);

                when(repository.save(task))
                        .thenReturn(task);

                Task saved =
                        service.save(task);

                assertEquals(
                        "Nueva tarea",
                        saved.getTitle());
        }

        @Test
        void shouldFindTaskById() {

                Task task =
                        new Task("Buscar tarea", false);

                task.setId(1L);

                when(repository.findById(1L))
                        .thenReturn(
                                Optional.of(task));

                Optional<Task> result =
                        service.findById(1L);
/*
                assertEquals(
                        true,
                        result.isPresent());
*/

                assertTrue(result.isPresent());

                assertEquals(
                        "Buscar tarea",
                        result.get().getTitle());
        }

        @Test
        void shouldReturnEmptyWhenTaskNotFound() {

                when(repository.findById(99L))
                        .thenReturn(Optional.empty());

                Optional<Task> result =
                        service.findById(99L);
/*
                assertEquals(
                        false,
                        result.isPresent());
*/
                assertFalse(result.isPresent());

        }

        @Test
        void shouldReturnCompletedTasks() {

                List<Task> tasks = List.of(
                        new Task("Task 1", true),
                        new Task("Task 2", true)
                );

                when(repository.findByCompleted(true))
                        .thenReturn(tasks);

                List<Task> result =
                        service.findByCompleted(true);

                assertEquals(
                        2,
                        result.size());
        }

        @Test
        void shouldSearchTasksByTitle() {

                List<Task> tasks = List.of(
                        new Task("Spring Boot", false)
                );

                when(repository.findByTitleContainingIgnoreCase("spring"))
                        .thenReturn(tasks);

                List<Task> result =
                        service.searchByTitle("spring");

                assertEquals(
                        1,
                        result.size());

                assertEquals(
                        "Spring Boot",
                        result.get(0).getTitle());
        }

        @Test
        void shouldReturnPagedTasks() {

                Pageable pageable =
                        PageRequest.of(0, 3);

                List<Task> tasks = List.of(
                        new Task("Task 1", false),
                        new Task("Task 2", false)
                );

                Page<Task> page =
                        new PageImpl<>(tasks);

                when(repository.findAll(pageable))
                        .thenReturn(page);

                Page<Task> result =
                        service.findAll(pageable);

                assertEquals(
                        2,
                        result.getContent().size());
        }

        @Test
        void shouldDeleteTask() {

        service.delete(1L);

        verify(repository)
                .deleteById(1L);
        }

}