package com.crud.tasks.service;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloServiceTest {
    @Autowired
    private TrelloService trelloService;

    @Test
    public void createdTrelloCardDtoTest() {
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
    public void trelloServiceMethodOfFetchTrelloBoards() {
        //Given
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("1","DtosList",false));
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(new TrelloBoardDto( "1","Dtos",trelloListDto));
        //When //Then
        trelloService.fetchTrelloBoards();
    }
}
