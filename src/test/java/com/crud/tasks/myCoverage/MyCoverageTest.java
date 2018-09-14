package com.crud.tasks.myCoverage;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TaskMapper;

import com.crud.tasks.service.DbService;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyCoverageTest {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TrelloValidator trelloValidator;
    @Autowired
    private DbService dbService;
    @Autowired
    private TrelloService trelloService;

    @Test
    public void myCoverageTest1() {
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
    public void myCoverageTest2() {
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
    public void myCoverageTest3() {
        //Given
        TrelloCard trelloCard = new TrelloCard("user1", "username", "9", "3");
        //When & Then
        trelloValidator.validatorCard(trelloCard);
    }

    @Test
    public void myCoverageTest4() {
        //Given & When
        TrelloDto trelloDto = new TrelloDto(2, 5);
        //Then
        assertNotNull(trelloDto);
        assertEquals(5, trelloDto.getCard());
        assertEquals(2, trelloDto.getBoard());
    }

    @Test
    public void myCoverageTest5() {
        //Given
        TrelloDto trelloDto = new TrelloDto(2, 5);
        AttachmentsDto attachmentsDto = new AttachmentsDto(trelloDto);
        BadgesDto badgesDto = new BadgesDto(100, attachmentsDto);

        assertEquals(100, badgesDto.getVotes());
        assertEquals(2, badgesDto.getAttachmentsDto().getTrelloDto().getBoard());
        assertEquals(5, badgesDto.getAttachmentsDto().getTrelloDto().getCard());
    }

    @Test
    public void myCoverageTest6() {
        //Given
        TaskDto taskDto = new TaskDto((long) 2, "user", "456");
        //When
        Task mapToTask = taskMapper.mapToTask(taskDto);

        //Then
        assertEquals("456", mapToTask.getContent());
        assertEquals("user", mapToTask.getTitle());
        assertEquals(2, mapToTask.getId(), 0);
    }

    @Test
    public void myCoverageTest7() {
        //Given & When
        AdminConfig adminConfig = new AdminConfig();
        adminConfig.getAdminMail();
        //Then
        assertNotNull(adminConfig);
    }

    @Test
    public void myCoverageTest8() {
        //Given
        TrelloDto trelloDto = new TrelloDto(2, 5);
        AttachmentsDto attachmentsDto = new AttachmentsDto(trelloDto);
        BadgesDto badgesDto = new BadgesDto(100, attachmentsDto);

        CreatedTrelloCardDto cardDto =
                new CreatedTrelloCardDto("1", "user", null, badgesDto);

        assertEquals("user", cardDto.getName());
        assertEquals("1", cardDto.getId());
        assertEquals(null, cardDto.getShortUrl());
        assertEquals(badgesDto, cardDto.getBadges());
        assertEquals(attachmentsDto, cardDto.getBadges().getAttachmentsDto());
        assertEquals(attachmentsDto.getTrelloDto(), cardDto.getBadges().getAttachmentsDto().getTrelloDto());
        assertEquals(attachmentsDto.getTrelloDto().getBoard(),
                cardDto.getBadges().getAttachmentsDto().getTrelloDto().getBoard());
        assertEquals(attachmentsDto.getTrelloDto().getCard(), cardDto.getBadges().getAttachmentsDto().getTrelloDto().getCard());
    }

    @Test
    public void myCoverageTest9() {
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
    public void myCoverage10() {
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
    @Test
    public void myCoverage11() {
        //Given
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("1","DtosList",false));
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(new TrelloBoardDto( "1","Dtos",trelloListDto));
        //When //Then
        trelloService.fetchTrelloBoards();
    }
}
