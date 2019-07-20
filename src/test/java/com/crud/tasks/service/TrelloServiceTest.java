package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {
    @InjectMocks
    private TrelloService trelloService;
    @Mock
    private TrelloClient trelloClient;
    @Mock
    private SimpleEmailService simpleEmailService;
    @Mock
    private AdminConfig adminConfig;

    @Test
    public void testFetchTrelloBoards() {
        //given
        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        trelloListDtoList.add(new TrelloListDto("id", "name", false));

        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(new TrelloBoardDto("id", "name", trelloListDtoList));

        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtos);

        //when
        List<TrelloBoardDto> testTrelloBoardDtoList = trelloService.fetchTrelloBoards();
        String testName = testTrelloBoardDtoList.get(0).getName();

        //then
        assertFalse(testTrelloBoardDtoList.isEmpty());
        assertEquals(1, testTrelloBoardDtoList.size());
        assertEquals("name", testName);
    }

    @Test
    public void testCreateTrelloCard() {
        //given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "name", "description", "pos", "listId");

        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(new CreatedTrelloCardDto(
                "id", "name", "shortUrl"));

        when(adminConfig.getAdminMail()).thenReturn("mail");

        //when
        CreatedTrelloCardDto testCreatedTrelloCardDto = trelloService.createTrelloCard(trelloCardDto);

        //then
        assertNotNull(testCreatedTrelloCardDto);
    }
}