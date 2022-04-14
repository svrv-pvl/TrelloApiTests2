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

        //TODO Add tear down
    }

    //TODO Try to send get a few times
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

        //TODO Add tear down
    }
}
