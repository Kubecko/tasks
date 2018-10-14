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
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.hamcrest.Matchers.hasSize;
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
    private DbService service;
    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldFetchEmptyTasks() throws Exception {
        //Given
        List<TaskDto> taskDtos = new ArrayList<>();

        when(taskMapper.mapToTaskDtoList(any())).thenReturn(taskDtos);

        //When & Then
        mockMvc.perform(get("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchTasks() throws Exception {
        List<TaskDto> dtos = new ArrayList<>();
        dtos.add(new TaskDto((long) 1, "task1", "content1"));
        dtos.add(new TaskDto((long) 2, "task2", "content2"));

        when(taskMapper.mapToTaskDtoList(any())).thenReturn(dtos);

        //When & Then
        mockMvc.perform(get("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //Task list fields
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("task1")))
                .andExpect(jsonPath("$[0].content", is("content1")));
    }

    @Test
    public void shouldFetchTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto((long) 1, "taskDto", "contentDto");
        Task task = new Task((long) 1, "task", "content");

        when(service.getTaskId(task.getId())).thenReturn(Optional.of(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When & Then
        mockMvc.perform(get("/v1/tasks/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

    }

    @Test(expected = NestedServletException.class)
    public void shouldFetchTaskNotFoundException() throws Exception {
        //Given
        when(service.getTaskId(any())).thenReturn(Optional.empty());

        //When & Then
        mockMvc.perform(get("/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void shouldUpdateFetchTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto((long) 1, "task", "content");

        Task task = new Task((long) 1, "task", "content");

        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(service.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        Gson gson = new Gson();
        String update = gson.toJson(task);
        //When & Then
        mockMvc.perform(put("/v1/tasks").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(update))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("task")))
                .andExpect(jsonPath("$.content", is("content")));
    }

    @Test
    public void shouldCreateFetchTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto((long) 1, "task", "content");

        Task task = new Task((long) 1, "task", "content");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);

        Gson gson = new Gson();
        String createTask = gson.toJson(task);
        //When & Then
        mockMvc.perform(post("/v1/tasks").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(createTask))
                .andExpect(status().isOk());

        verify(service, times(1)).saveTask(any());
    }

    @Test
    public void shouldDeleteFetchTask() throws Exception {
        //Given

        //When & Then
        mockMvc.perform(delete("/v1/tasks/1"))
                .andExpect(status().is(200));

        verify(service, times(1)).deleteTaskId(any());
    }
}
