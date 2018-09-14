package com.crud.tasks.ClassDtoTest;

import com.crud.tasks.domain.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClassDtoTest {

    @Test
    public void trelloDtoTest() {
        //Given & When
        TrelloDto trelloDto = new TrelloDto(2, 5);
        //Then
        assertNotNull(trelloDto);
        assertEquals(5, trelloDto.getCard());
        assertEquals(2, trelloDto.getBoard());
    }

    @Test
    public void badgesDtoTest() {
        //Given
        TrelloDto trelloDto = new TrelloDto(2, 5);
        AttachmentsDto attachmentsDto = new AttachmentsDto(trelloDto);
        BadgesDto badgesDto = new BadgesDto(100, attachmentsDto);

        assertEquals(100, badgesDto.getVotes());
        assertEquals(2, badgesDto.getAttachmentsDto().getTrelloDto().getBoard());
        assertEquals(5, badgesDto.getAttachmentsDto().getTrelloDto().getCard());
    }
}
