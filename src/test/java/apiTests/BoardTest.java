package apiTests;

import model.CreateBoardResponse;
import model.GetBoardResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BoardTest {
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
        assertEquals(null, createBoardResponseBody.getDescData());
        assertEquals(false, createBoardResponseBody.getPinned());
        assertEquals("private", createBoardResponseBody.getPrefs().getPermissionLevel());
        //Tear down
        boardClient.deleteBoard(createBoardResponseBody.getId());
    }

    //TODO Try to send get a few times
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


    //TODO check that deleted board cannot be retrieved
}
