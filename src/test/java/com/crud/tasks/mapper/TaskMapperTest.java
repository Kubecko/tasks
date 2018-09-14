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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTest {
    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void mapToTaskDtoListTest() {
        //Given
        List<Task> task = new ArrayList<>();
        task.add(new Task((long) 1, "my_task", "my_content"));
        task.add(new Task((long) 1, "my_task", "my_content"));

        //When
        List<TaskDto> mapperTask = taskMapper.mapToTaskDtoList(task);
        //Then
        assertNotNull(mapperTask);

        assertEquals(2, mapperTask.size());
        assertEquals("my_task", mapperTask.get(0).getTitle());
        assertEquals("my_content", mapperTask.get(0).getContent());
        assertEquals(1, mapperTask.get(0).getId(), 0);
    }

    @Test
    public void mapToTaskDtoTest() {
        //Given
        Task task = new Task((long) 1, "my_task", "my_content");
        //When
        TaskDto mapperTaskDto = taskMapper.mapToTaskDto(task);
        //Then
        assertNotNull(mapperTaskDto);
        assertEquals(1, mapperTaskDto.getId(), 0);
        assertEquals("my_task", mapperTaskDto.getTitle());
        assertEquals("my_content", mapperTaskDto.getContent());
    }

    @Test
    public void mapToTaskDto() {
        //Given
        TaskDto taskDto = new TaskDto((long) 2, "user", "456");
        //When
        Task mapToTask = taskMapper.mapToTask(taskDto);

        //Then
        assertEquals("456", mapToTask.getContent());
        assertEquals("user", mapToTask.getTitle());
        assertEquals(2, mapToTask.getId(), 0);
    }
}
