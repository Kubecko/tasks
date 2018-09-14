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
    public void dbServiceMethodAllTaskAndGetTaskIdTest() {
        //Given //When
        List<Task> dbList = dbService.getAllTask();
        Optional<Task> dbList2 = dbService.getTaskId((long) 2);
        //Then
        assertNotNull(dbList);
        assertNotNull(dbList2);
        assertEquals(8, dbList.size());
        assertEquals("First Test", dbList.get(0).getTitle());
        assertEquals("I need to be great coder!!!", dbList.get(1).getContent());
        assertEquals((long) 4, dbList.get(0).getId(), 0);
    }

    @Test
    public void dbServiceMethodSaveTaskTest() {
        //Given
        Task task = new Task((long) 1, "null", "nullContent");
        //When
        Task saveTask = dbService.saveTask(task);
        //Then
        assertNotNull(saveTask);
        assertEquals("null", task.getTitle());
        assertEquals((long) 1, task.getId(), 0);
        assertEquals("nullContent", task.getContent());
        try {
            dbService.deleteTaskId(saveTask.getId());
        } catch (Exception e) {

        }
    }
}
