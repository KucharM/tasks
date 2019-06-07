package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTestSuite {
    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void testMapToBoardsAndLists() {
        //Given
        TrelloListDto trelloListDto = new TrelloListDto("trelloListId", "trelloListName", false);
        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        trelloListDtoList.add(trelloListDto);

        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("trelloBoardId", "trelloBoardName", trelloListDtoList);
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(trelloBoardDto);

        //When
        List<TrelloList> testTrelloListList = trelloMapper.mapToList(trelloListDtoList);
        List<TrelloBoard> testTrelloBoardList = trelloMapper.mapToBoards(trelloBoardDtoList);

        //Then
        TrelloBoard testTrelloBoard = testTrelloBoardList.get(0);
        String testTrelloBoardName = testTrelloBoard.getName();
        TrelloList testTrelloList = testTrelloListList.get(0);
        String trelloListId = testTrelloList.getId();

        Assert.assertEquals("trelloBoardName", testTrelloBoardName);
        Assert.assertEquals("trelloListId", trelloListId);
        Assert.assertFalse(testTrelloListList.isEmpty());
        Assert.assertEquals(1, testTrelloBoardList.size());
    }

    @Test
    public void testMapToBoardsDtoAndListDto() {
        //Given
        TrelloList trelloList = new TrelloList("listId", "listName", false);
        List<TrelloList> trelloListList = new ArrayList<>();
        trelloListList.add(trelloList);

        TrelloBoard trelloBoard = new TrelloBoard("boardId", "boardName", trelloListList);
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(trelloBoard);

        //When
        List<TrelloListDto> testTrelloListList = trelloMapper.mapToListDto(trelloListList);
        List<TrelloBoardDto> testTrelloBoardList = trelloMapper.mapToBoardsDto(trelloBoardList);

        //Then
        TrelloListDto testTrelloListDto = testTrelloListList.get(0);
        String testTrelloListDtoId = testTrelloListDto.getId();
        TrelloBoardDto testTrelloBoardDto = testTrelloBoardList.get(0);
        String testTrelloBoardDtoName = testTrelloBoardDto.getName();

        Assert.assertEquals("listId", testTrelloListDtoId);
        Assert.assertEquals("boardName", testTrelloBoardDtoName);
        Assert.assertFalse(testTrelloListList.isEmpty());
        Assert.assertEquals(1, testTrelloBoardList.size());
    }

    @Test
    public void testMapToCard() {
        //Given
        TrelloCard trelloCard = new TrelloCard("cardName", "cardDesc",
                "cardPosition", "listId");
        TrelloCardDto trelloCardDto = new TrelloCardDto("cardDtoName", "cardDtoDesc",
                "cardDtoPosition", "listDtoId");

        //When
        TrelloCardDto mappedTrelloCard = trelloMapper.mapToCardDto(trelloCard);
        TrelloCard mappedTrelloCardDto = trelloMapper.mapToCard(trelloCardDto);

        //Then
        String mappedCardName = mappedTrelloCard.getName();
        String mappedCardDesc = mappedTrelloCard.getDescription();
        String mappedCardDtoListId = mappedTrelloCardDto.getListId();
        String mappedCardDtoPosition = mappedTrelloCardDto.getPos();

        Assert.assertEquals("cardName", mappedCardName);
        Assert.assertEquals("cardDesc", mappedCardDesc);
        Assert.assertEquals("listDtoId", mappedCardDtoListId);
        Assert.assertEquals("cardDtoPosition", mappedCardDtoPosition);
    }
}
