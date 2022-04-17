package apiTests;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import model.CreateBoardResponse;
import model.GetBoardResponse;
import model.UnarchiveBoardResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class BoardTest extends TrelloTest{
    private static BoardClient boardClient;

    @BeforeAll
    public static void connectionInitialization(){
        String connectionPropertiesFile = System.getProperty("connectionPropertiesFile");
        boardClient = new BoardClient(connectionPropertiesFile);
    }

    //TODO Add tests for headers
    //TODO Add timeouts
    //TODO Add tests of incorrect url parameters

    @Test
    public void shouldCreateBoard(){
        //Arrange
        //Act
        String boardName = "testBoard2";
        CreateBoardResponse createBoardResponseBody = boardClient.createBoard(boardName);
        GetBoardResponse getBoardResponseBody = boardClient.getBoard(createBoardResponseBody.getId());
        //Assert
        assertEquals(boardName, createBoardResponseBody.getName());
        assertEquals(createBoardResponseBody.getId(), getBoardResponseBody.getId());
        assertEquals("", createBoardResponseBody.getDesc());
        assertNull(createBoardResponseBody.getDescData());
        assertEquals(false, createBoardResponseBody.getPinned());
        assertEquals("private", createBoardResponseBody.getPrefs().getPermissionLevel());
        //Tear down
        boardClient.deleteBoard(createBoardResponseBody.getId());
    }

    //TODO Try to get board which is not exist
    @Test
    public void shouldGetBoard(){
        //Arrange
        String boardName = "testBoard";
        CreateBoardResponse createBoardResponseBody = boardClient.createBoard(boardName);
        //Act
        String boardId = createBoardResponseBody.getId();
        GetBoardResponse getBoardResponseBody = boardClient.getBoard(boardId);
        //Assert
        assertEquals(boardId, getBoardResponseBody.getId());
        assertEquals(boardName, getBoardResponseBody.getName());
        //Tear down
        boardClient.deleteBoard(createBoardResponseBody.getId());
    }

    @Test
    public void shouldBeSameResultWhenGetBoardFewTimes(){
        //Arrange
        String boardName = "testBoard";
        CreateBoardResponse createBoardResponseBody = boardClient.createBoard(boardName);
        //Act
        String boardId = createBoardResponseBody.getId();
        GetBoardResponse getBoardResponseBody = boardClient.getBoard(boardId);
        GetBoardResponse secondGetBoardResponseBody = boardClient.getBoard(boardId);
        //Assert
        assertEquals(boardId, getBoardResponseBody.getId());
        assertEquals(boardName, getBoardResponseBody.getName());
        assertEquals(boardId, secondGetBoardResponseBody.getId());
        assertEquals(boardName, secondGetBoardResponseBody.getName());
        //Tear down
        boardClient.deleteBoard(createBoardResponseBody.getId());
    }

    @Test
    public void shouldRenameBoard(){
        //Arrange
        String boardName = "someBoard";
        CreateBoardResponse createBoardResponseBody = boardClient.createBoard(boardName);
        String boardId = createBoardResponseBody.getId();
        //Act
        String newBoardName ="newName";
        GetBoardResponse renameBoardResponseBody = boardClient.renameBoard(boardId, newBoardName);
        GetBoardResponse getBoardResponseBody = boardClient.getBoard(boardId);
        //Assert
        assertEquals(newBoardName, renameBoardResponseBody.getName());
        assertEquals(newBoardName, getBoardResponseBody.getName());
        //Tear down
        boardClient.deleteBoard(boardId);
    }

    @Test
    public void shouldChangeBoardDescription(){
        //Arrange
        String boardName = "testBoard";
        CreateBoardResponse createBoardResponseBody = boardClient.createBoard(boardName);
        String boardId = createBoardResponseBody.getId();
        //Act
        String newBoardDescription = "newDesc";
        GetBoardResponse changeBoardDescriptionResponseBody = boardClient.changeBoardDescription(boardId, newBoardDescription);
        GetBoardResponse getBoardResponseBody = boardClient.getBoard(boardId);
        //Assert
        assertEquals(newBoardDescription, changeBoardDescriptionResponseBody.getDesc());
        assertEquals(newBoardDescription, getBoardResponseBody.getDesc());
        //Tear down
        boardClient.deleteBoard(boardId);
    }

    @Test
    public void shouldArchiveBoard(){
        //Arrange
        String boardName = "testBoard";
        CreateBoardResponse createBoardResponseBody = boardClient.createBoard(boardName);
        String boardId = createBoardResponseBody.getId();
        //Act
        GetBoardResponse archiveBoardResponseBody = boardClient.archiveBoard(boardId);
        GetBoardResponse getBoardResponseBody = boardClient.getBoard(boardId);
        //Assert
        assertEquals(true, archiveBoardResponseBody.getClosed());
        assertEquals(true, getBoardResponseBody.getClosed());
        //Tear down
        boardClient.deleteBoard(boardId);
    }

    @Test
    public void shouldUnarchiveBoard(){
        //Arrange
        String boardName = "testBoard";
        CreateBoardResponse createBoardResponseBody = boardClient.createBoard(boardName);
        String boardId = createBoardResponseBody.getId();
        boardClient.archiveBoard(boardId);
        //Act
        UnarchiveBoardResponse unarchiveBoardResponseBody = boardClient.unarchiveBoard(boardId);
        GetBoardResponse getBoardResponseBody = boardClient.getBoard(boardId);
        //Assert
        assertEquals(false, unarchiveBoardResponseBody.getClosed());
        assertEquals(false, getBoardResponseBody.getClosed());
        //Tear down
        boardClient.deleteBoard(boardId);
    }
    //TODO add tests for prefs modification

    @Test
    public void shouldDeleteBoard(){
        //Arrange
        String boardName = "testBoard";
        CreateBoardResponse createBoardResponseBody = boardClient.createBoard(boardName);
        String boardId = createBoardResponseBody.getId();
        //Act
        boardClient.deleteBoard(boardId);
        int getBoardResponseCode = boardClient.returnErrorOnGettingBoardWhichDoesNotExist(boardId);
        //Assert
        assertEquals(404, getBoardResponseCode);
    }

    @Test
    public void shouldReturnCorrectHeadersOnGetBoard(){
        //Arrange
        String boardName = "testBoard";
        CreateBoardResponse createBoardResponseBody = boardClient.createBoard(boardName);
        String boardId = createBoardResponseBody.getId();
        //Act
        Headers getBoardHeaders = boardClient.returnHeadersOnGetBoard(boardId);
        //Assert
        for(Header header: expectedGeneralHeaders){
            assertEquals(expectedGeneralHeaders.get(header.getName()), getBoardHeaders.get(header.getName()));
        }
        for(Header header: expectedHeadersFor200){
            assertEquals(expectedHeadersFor200.get(header.getName()), getBoardHeaders.get(header.getName()));
        }
        //Tear down
        boardClient.deleteBoard(boardId);
    }

    @Test
    public void shouldReturnCorrectHeadersOnCreateBoard(){
        //Arrange

        //Act
        String boardName = "testBoard";
        Response response = boardClient.returnResponseOnCreateBoard(boardName);
        Headers createBoardHeaders = response.getHeaders();
        String boardId = response.as(CreateBoardResponse.class).getId();
        //Assert
        for(Header header: expectedGeneralHeaders){
            assertEquals(expectedGeneralHeaders.get(header.getName()), createBoardHeaders.get(header.getName()));
        }
        for(Header header: expectedHeadersFor200){
            assertEquals(expectedHeadersFor200.get(header.getName()), createBoardHeaders.get(header.getName()));
        }
        //Tear down
        boardClient.deleteBoard(boardId);
    }


    //TODO Update all possible fields of the board simultaneously
}
