package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
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
        assertNotNull(dot);
        assertEquals(1,dot.size());
        assertEquals("Marcin",dot.get(0).getName());
        assertEquals("1",dot.get(0).getId());
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
        assertNotNull(dot);
        assertEquals("1",dot.get(0).getId());
        assertEquals("Bartol",dot.get(0).getName());
    }
    @Test
    public void testMapToList() {
        TrelloListDto listDto = new TrelloListDto("1","Bartoszek",true);

        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(listDto);
        List<TrelloList> dot = trelloMapper.mapToList(trelloLists);
        //Then
        assertNotNull(dot);
        assertEquals(1,dot.size());
        assertEquals("1",dot.get(0).getId());
        assertEquals("Bartoszek",dot.get(0).getName());
    }
    @Test
    public void testMapToListDto() {
        TrelloList trelloList = new TrelloList("1","Marcin",true);

        List<TrelloList> listsTrello = new ArrayList<>();
        listsTrello.add(trelloList);
        //When
        List<TrelloListDto> dot = trelloMapper.mapToListDto(listsTrello);
        //Then
        assertNotNull(dot);
        assertEquals(true,dot.get(0).isClosed());
        assertEquals("Marcin",dot.get(0).getName());
        assertEquals("1",dot.get(0).getId());

    }
    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("Marcin","name","3","4");
        //When
        TrelloCardDto dot = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertNotNull(dot);
        assertEquals("name",dot.getDescription());
        assertEquals("4",dot.getListId());
        assertEquals("Marcin",dot.getName());
        assertEquals("3",dot.getPos());
    }
    @Test
    public void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Irenka","username","89","3");
        //When
        TrelloCard dot = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertNotNull(dot);
        assertEquals("Irenka",dot.getName());
        assertEquals("username",dot.getDescription());
        assertEquals("89",dot.getPos());
        assertEquals("3",dot.getListId());
    }
}
