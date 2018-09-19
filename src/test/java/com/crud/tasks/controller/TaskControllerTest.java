package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.repository.TaskRepository;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    @MockBean
    private TaskRepository repository;

    @MockBean
    private TaskController taskController;

    @Test
    public void shouldFetchEmptyTasks() throws Exception {
        //Given
        List<TaskDto> taskDtos = new ArrayList<>();
        //when(taskController.getTasks()).thenReturn(taskDtos); Why not like that?
        when(taskMapper.mapToTaskDtoList(any())).thenReturn(taskDtos);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchTasks() throws Exception {
        List<TaskDto> dtos = new ArrayList<>();
        dtos.add(new TaskDto((long) 1, "task1", "content1"));

        when(taskController.getTasks()).thenReturn(dtos);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //Task list fields
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("task1")))
                .andExpect(jsonPath("$[0].content", is("content1")));
    }

    @Test
    public void shouldUpdateFetchTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto((long) 1, "taskDto", "ContentDto");

        Task task = new Task((long) 1, "task", "controllerTask");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(service.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        Gson gson = new Gson();
        String update = gson.toJson(task);
        //When & Then
        mockMvc.perform(put("/v1/task/updateTask").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(update))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateFetchTask() throws Exception {
        //Given
        Task task = new Task(
                (long) 1,
                "Title Task",
                "Content Task");

        when(repository.save(any(Task.class))).thenReturn(task);
        //When & Then
        verify(repository, times(0)).save(any(Task.class));
    }
    @Test
    public void shouldDeleteFetchTask() throws Exception {
        //Given
        List<Task> listTask = new ArrayList<>();
        listTask.add(new Task((long)1,"task1","content1"));
        listTask.add(new Task((long)2,"task2","content2"));

        //When & Then
        verify(service,times(0)).deleteTaskId(listTask.get(1).getId());
    }

}
