package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTest {
    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void testMapToTask() {
        //given
        TaskDto taskDto = new TaskDto(1L, "taskDto title", "taskDto content");
        Long taskDtoId = taskDto.getId();

        //when
        Task testTask = taskMapper.mapToTask(taskDto);
        Long testTaskId = testTask.getId();
        String testTaskTitle = testTask.getTitle();
        String testTaskContent = testTask.getContent();

        //then
        assertEquals(taskDtoId, testTaskId);
        assertEquals("taskDto title", testTaskTitle);
        assertEquals("taskDto content", testTaskContent);
    }

    @Test
    public void testMapToTaskDto() {
        //given
        Task task = new Task(1L, "task title", "task content");
        Long taskId = task.getId();

        //when
        TaskDto testTaskDto = taskMapper.mapToTaskDto(task);
        Long testTaskDtoId = testTaskDto.getId();
        String testTaskDtoTitle = testTaskDto.getTitle();
        String testTaskDtoContent = testTaskDto.getContent();

        //then
        assertEquals(taskId, testTaskDtoId);
        assertEquals("task title", testTaskDtoTitle);
        assertEquals("task content", testTaskDtoContent);
    }

    @Test
    public void testMapToTaskDtoList() {
        //given
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "task title 1", "task content 1"));
        taskList.add(new Task(2L, "task title 2", "task content 2"));

        //when
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);
        int size = taskDtoList.size();
        String taskDtoTitle1 = taskDtoList.get(0).getTitle();
        String taskDtoTitle2 = taskDtoList.get(1).getTitle();

        //then
        assertFalse(taskDtoList.isEmpty());
        assertEquals(2, size);
        assertEquals("task title 1", taskDtoTitle1);
        assertEquals("task title 2", taskDtoTitle2);
    }

}