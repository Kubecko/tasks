package com.crud.tasks.trello.client;

import com.crud.tasks.domain.BadgesDto;
import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    @Value("${trello.datasource.username}")
    private String trelloUsername;

    @Autowired
    private RestTemplate restTemplate;

    private URI urlBuilder(String trelloApiEndpoint,String trelloUsername, String trelloAppKey, String trelloToken){
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + trelloUsername + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("field","name,id")
                .queryParam("lists","all").build().encode().toUri();

    }

    public List<TrelloBoardDto> getTrelloBoards() {
        TrelloClient trelloClient = new TrelloClient();
        URI url = trelloClient.urlBuilder(trelloApiEndpoint,trelloUsername,trelloAppKey,trelloToken);

        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url,TrelloBoardDto[].class);
        return Arrays.asList(Optional.ofNullable(boardsResponse).orElse(new TrelloBoardDto[0]));
    }

    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto, BadgesDto badgesDto){
        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/cards")
                .queryParam("key",trelloAppKey)
                .queryParam("token",trelloToken)
                .queryParam("name",trelloCardDto.getName())
                .queryParam("desc",trelloCardDto.getDescription())
                .queryParam("pos",trelloCardDto.getPos())
                .queryParam("idList",trelloCardDto.getListId())
                .queryParam("votes",badgesDto.getVotes())
                .queryParam("attachmentsByType", badgesDto.getAttachmentsDto()).build().encode().toUri();

        return restTemplate.postForObject(url,null,CreatedTrelloCard.class);
    }
}