package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbServiceTest {
    @Autowired
    private DbService dbService;

    @Test
    public void dbServiceMethodAllTask() {
        //Given & When
        List<Task> dbList = dbService.getAllTask();
        //Then
        assertNotNull(dbList);
        assertEquals(1, dbList.size());
        assertEquals("Updated Task", dbList.get(0).getTitle());
        assertEquals("Updated Description", dbList.get(0).getContent());
        assertEquals((long) 5, dbList.get(0).getId(), 0);
    }

    @Test
    public void getTaskIdTest() {
        //Given & When
        Optional<Task> dbList2 = dbService.getTaskId((long) 2);
        //Then
        assertNotNull(dbList2);
    }

    @Test
    public void dbServiceMethodSaveTaskTest() {
        //Given
        Task task = new Task( (long) 1, "null", "nullContent");
        //When
        Task saveTask = dbService.saveTask(task);

        long id = saveTask.getId();
        //Then
        assertNotNull(task);
        assertEquals("null", saveTask.getTitle());
        assertEquals("nullContent", saveTask.getContent());
        //Clean up
        dbService.deleteTaskId(id);
    }
}
