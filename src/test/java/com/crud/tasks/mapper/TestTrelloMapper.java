package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.trello.facade.TrelloFacade;
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
public class TestTrelloMapper {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void testMapToBoards(){
        //Given
        TrelloList listTrello = new TrelloList("2","Bartosz", false);
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(listTrello);

        TrelloBoard trelloBoard = new TrelloBoard("1","Marcin",trelloLists);

        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard);
        //When
        List<TrelloBoardDto> dot = trelloMapper.mapToBoardsDto(trelloBoards);
        //Then
        assertEquals(1,dot.size());
    }
    @Test
    public void testMapToBoardsDto() {
        //Given
        TrelloListDto listTrello = new TrelloListDto("2","Bartosz", false);
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(listTrello);

        TrelloBoardDto boardDto = new TrelloBoardDto("1","Bartol",trelloLists);

        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(boardDto);
        //When
        List<TrelloBoard> dot = trelloMapper.mapToBoards(trelloBoardDtos);
        //Then
        System.out.println(dot.get(0).getId());
        assertEquals("1",dot.get(0).getId());
    }
    @Test
    public void testMapToList() {
        TrelloListDto listDto = new TrelloListDto("1","Bartoszek",true);

        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(listDto);
        List<TrelloList> dot = trelloMapper.mapToList(trelloLists);
        //Then
        assertEquals(1,dot.size());
    }
    @Test
    public void testMapToListDto() {
        TrelloList trelloList = new TrelloList("1","Marcin",true);

        List<TrelloList> listsTrello = new ArrayList<>();
        listsTrello.add(trelloList);
        //When
        List<TrelloListDto> dot = trelloMapper.mapToListDto(listsTrello);
        //Then
        assertEquals(true,dot.get(0).isClosed());
    }
    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("Marcin","name","3","4");
        //When
        TrelloCardDto dot = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertEquals("name",dot.getDescription());
    }
    @Test
    public void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Irenka","username","89","3");
        //When
        TrelloCard dot = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertEquals("Irenka",dot.getName());
    }
}
