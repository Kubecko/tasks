package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;

import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrelloFacade {

    @Autowired
    private TrelloService trelloService;

    @Autowired
    private TrelloMapper trelloMapper;

    @Autowired
    private TrelloValidator trelloValidator;

    public List<TrelloBoardDto> fetchTrelloBoards() {
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloService.fetchTrelloBoards());
        List<TrelloBoard> filteresBoards = trelloValidator.validatorTrelloBoards(trelloBoards);
        return trelloMapper.mapToBoardsDto(filteresBoards);
    }

    public CreatedTrelloCardDto createCard(final TrelloCardDto trelloCartDto){
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCartDto);
        trelloValidator.validatorCard(trelloCard);
        return trelloService.createTrelloCard(trelloMapper.mapToCardDto(trelloCard));
    }
}
