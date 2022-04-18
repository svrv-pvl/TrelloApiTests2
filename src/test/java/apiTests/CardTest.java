package apiTests;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import model.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CardTest extends TrelloTest{
    private static String boardId;
    private static String listId;

    //TODO Write a method to create board and list with default names to reduce amount of code and returning ID

    @BeforeAll
    public static void connectionInitializationBordAndList(){
        initializationClientsAndHeaders();
        String boardName = "boardForCards";
        GetBoardResponse createBoardResponseBody = boardClient.createBoard(boardName);
        boardId = createBoardResponseBody.getId();
        String listName = "listForCards";
        CreateListResponse createListResponseBody = listClient.createList(listName, boardId);
        listId = createListResponseBody.getId();
    }

    @Test
    public void shouldCreateCard(){
        //Arrange

        //Act
        CreateCardResponse createCardResponseBody = cardClient.createCard(listId);
        //Assert
        assertEquals(boardId, createCardResponseBody.getIdBoard());
        //Tear down
        String cardId = createCardResponseBody.getId();
        cardClient.deleteCard(cardId);
    }

    @ParameterizedTest
    @ValueSource(strings = {"d", "  ", "newName", "$", "!"})
    public void shouldRenameCard(String newCardName){
        //Arrange
        String cardId = cardClient.createDefaultCardAndReturnId(listId);
        //Act
        GetCardResponse renameCardResponseBody = cardClient.renameCard(cardId, newCardName);
        GetCardResponse getCardResponseBody = cardClient.getCard(cardId);
        //Assert
        assertEquals(newCardName, renameCardResponseBody.getName());
        assertEquals(newCardName, getCardResponseBody.getName());
        //Tear down
    }

    @ParameterizedTest
    @ValueSource(strings = {"d", "  ", "newDesc", "$", "!"})
    public void shouldChangeCardDescription(String newCardDescription){
        //Arrange
        String cardId = cardClient.createDefaultCardAndReturnId(listId);
        //Act
        GetCardResponse updateCardResponseBody = cardClient.changeCardDescription(cardId, newCardDescription);
        GetCardResponse getCardResponseBody = cardClient.getCard(cardId);
        //Assert
        assertEquals(newCardDescription, updateCardResponseBody.getDesc());
        assertEquals(newCardDescription, getCardResponseBody.getDesc());
        //Tear down
    }

    @AfterAll
    public static void removeTestBoards(){
        boardClient.deleteBoard(boardId);
    }
}
