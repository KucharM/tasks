package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskMapper taskMapper;
    @MockBean
    private DbService dbService;

    @Test
    public void testGetTasks() throws Exception {
        //Given
        List<TaskDto> taskDtos = new ArrayList<>();
        taskDtos.add(new TaskDto(1l, "test-title", "test-content"));

        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1l, "test-title", "test-content"));

        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(taskDtos);
        when(dbService.getAllTasks()).thenReturn(tasks);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("test-title")))
                .andExpect(jsonPath("$[0].content", is("test-content")));
    }

    @Test
    public void testGetTasksReturnsEmptyList() throws Exception {
        //Given
        List<TaskDto> taskDtos = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();

        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(taskDtos);
        when(dbService.getAllTasks()).thenReturn(tasks);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    public void testGetTask() throws Exception {
        //Given
        Task task = new Task(1L, "test-title", "test-content");
        TaskDto taskDto = new TaskDto(1L, "test-title", "test-content");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        when(dbService.getTask(1L)).thenReturn(Optional.of(task));
        when(dbService.saveTask(task)).thenReturn(task);

        //When & Then
        mockMvc.perform(get("/v1/task/getTask").param("taskId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test-title")))
                .andExpect(jsonPath("$.content", is("test-content")));
    }

    @Test
    public void testDeleteTask() throws Exception{
        //given
        Task task = new Task(1L, "test-title", "test-content");

        //when then
        mockMvc.perform(delete("/v1/task/deleteTask").param("taskId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateTask() throws Exception{
        //given
        TaskDto taskDto = new TaskDto(1L, "title", "content");
        Task task = new Task(1L, "title", "content");

        //when then
        when(taskMapper.mapToTask(any())).thenReturn(task);
        when(dbService.saveTask(any())).thenReturn(task);
        when(taskMapper.mapToTaskDto(any())).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("title")))
                .andExpect(jsonPath("$.content", is("content")));

    }

    @Test
    public void testCreateTask() throws Exception{
        //given
        TaskDto taskDto = new TaskDto(1L, "title", "content");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //when then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());

        verify(dbService, times(1)).saveTask(any());
    }
}