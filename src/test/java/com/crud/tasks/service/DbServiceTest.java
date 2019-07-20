package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTest {
    @InjectMocks
    private DbService dbService;
    @Mock
    private TaskRepository taskRepository;

    @Test
    public void testGetAllTasks() {
        //given
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "title", "content"));
        taskList.add(new Task(2L, "title2", "content2"));

        when(taskRepository.findAll()).thenReturn(taskList);

        //when
        List<Task> testTaskList = dbService.getAllTasks();
        String testTitle = testTaskList.get(0).getTitle();

        //then
        assertFalse(testTaskList.isEmpty());
        assertEquals(2, testTaskList.size());
        assertEquals("title",testTitle);
    }

    @Test
    public void testSaveTask() {
        //given
        Task task = new Task(1L, "title", "content");
        Long taskId = task.getId();

        when(taskRepository.save(task)).thenReturn(task);

        //when
        Task testTask = dbService.saveTask(task);
        Long testTaskId = testTask.getId();
        String testTaskTitle = testTask.getTitle();
        String testTaskContent = testTask.getContent();

        //then
        assertEquals(taskId, testTaskId);
        assertEquals("title", testTaskTitle);
        assertEquals("content", testTaskContent);
    }

    @Test
    public void testGetTask() {
        //given
        Task task = new Task(1L, "title", "content");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        //when
        Optional<Task> testTask = dbService.getTask(1L);
        String taskTitle = testTask.get().getTitle();

        //then
        assertTrue(testTask.isPresent());
        assertEquals("title", taskTitle);
    }

    @Test
    public void testDeleteById() {
        //given

        //when
        dbService.deleteTaskById(1L);

        //then
        verify(taskRepository, times(1)).deleteTaskById(1L);
    }
}