package com.crud.tasks.service;

import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloServiceTest {
    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Test
    public void trelloServiceMethodOfFetchTrelloBoards() {
        //Given
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("1","DtosList",false));

        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(new TrelloBoardDto( "1","Dtos",trelloListDto));
        //When
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtos);

        List<TrelloBoardDto> listBoard = trelloService.fetchTrelloBoards();
        // Then
        Assert.assertEquals(1,listBoard.size());
        Assert.assertEquals(trelloBoardDtos,listBoard);
    }
}
