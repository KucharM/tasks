package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TrelloValidatorTest {
    @InjectMocks
    private TrelloValidator trelloValidator;

    @Test
    public void testValidateTrelloBoard() {
        //given
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("1", "list name", false));

        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("11", "name", trelloLists));

        //when
        List<TrelloBoard> validateBoards = trelloValidator.validateTrelloBoard(trelloBoards);

        //then
        assertFalse(validateBoards.isEmpty());
        assertEquals(1, validateBoards.size());
    }
}