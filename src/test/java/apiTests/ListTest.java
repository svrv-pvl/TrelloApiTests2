package apiTests;

import io.restassured.specification.RequestSpecification;
import model.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListTest {

    private static String boardId;
    private static ListClient listClient;
    private static BoardClient boardClient;

    @BeforeAll
    public static void connectionInitializationAndBoardCreation(){
        String connectionPropertiesFile = System.getProperty("connectionPropertiesFile");
        listClient = new ListClient(connectionPropertiesFile);
        boardClient = new BoardClient(connectionPropertiesFile);
        String boardName = "list_test_board";
        GetBoardResponse boardResponseBody = boardClient.createBoard(boardName);
        boardId = boardResponseBody.getId();
    }

    //TODO Add tests for headers
    //TODO Add timeouts
    //TODO Add tests of incorrect url parameters

    @Test
    public void shouldCreateNewEmptyList(){
        //Arrange

        //Act
        String listNameToCreate = "fromPostman";
        CreateListResponse responseBody = listClient.createList(listNameToCreate, boardId);
        //Assert
        assertEquals(listNameToCreate, responseBody.getName());
        assertEquals(false, responseBody.getClosed());
        assertEquals(boardId, responseBody.getIdBoard());
        //TearDown
        listClient.archiveList(responseBody.getId());
    }

    @RepeatedTest(3)
    public void shouldGetList(){
        //Arrange
        String listName = "testList";
        CreateListResponse createdPrerequisiteList = listClient.createList(listName, boardId);
        String listID = createdPrerequisiteList.getId();
        //Act
        GetListResponse getListResponseBody = listClient.getList(listID);
        //Assert
        assertEquals(listID, getListResponseBody.getId());
        assertEquals(listName, getListResponseBody.getName());
        assertEquals(false, getListResponseBody.getClosed());
        assertEquals(boardId, getListResponseBody.getIdBoard());
        //TearDown
        listClient.archiveList(listID);
    }

    @Test
    public void shouldRenameList(){
        //Arrange
        String listName = "testUpdatedList";
        CreateListResponse createdPrerequisiteList = listClient.createList(listName, boardId);
        String listId = createdPrerequisiteList.getId();
        //Act
        String newListName = "newName";
        UpdateListResponse renameResponseBody = listClient.renameList(listId, newListName);
        GetListResponse getListResponseBody = listClient.getList(listId);
        //Assert
        assertEquals(listId, renameResponseBody.getId());
        assertEquals(newListName, renameResponseBody.getName());
        assertEquals(newListName, getListResponseBody.getName());
        //TearDown
        listClient.archiveList(listId);
    }

    @Test
    public  void  shouldArchiveList(){
        //Arrange
        String listName = "testList";
        CreateListResponse createdPrerequisiteList = listClient.createList(listName, boardId);
        String listId = createdPrerequisiteList.getId();
        //Act
        UpdateListResponse archivedListBody = listClient.archiveList(listId);
        GetListResponse getListResponseBody = listClient.getList(listId);
        //Assert
        assertEquals(listId, archivedListBody.getId());
        assertEquals(listName, archivedListBody.getName());
        assertEquals(true, archivedListBody.getClosed());
        assertEquals(true, getListResponseBody.getClosed());
        //TearDown
        listClient.archiveList(listId);
    }

    @Test
    public  void  shouldUnarchiveList(){
        //Arrange
        String listName = "testList";
        CreateListResponse createdPrerequisiteList = listClient.createList(listName, boardId);
        String listId = createdPrerequisiteList.getId();
        listClient.archiveList(listId);
        //Act
        UpdateListResponse unarchivedListBody = listClient.unarchiveList(listId);
        GetListResponse getListResponseBody = listClient.getList(listId);
        //Assert
        assertEquals(listId, unarchivedListBody.getId());
        assertEquals(listName, unarchivedListBody.getName());
        assertEquals(false, unarchivedListBody.getClosed());
        assertEquals(false, getListResponseBody.getClosed());
        //TearDown
        listClient.archiveList(listId);
    }

    @Test
    public void shouldGetErrorOnGetListWhenListIdDoesNotExist(){
        //Arrange
        String listID = "1111111111";
        //Act
        int getListErrorCode = listClient.getErrorCodeWhenTryGetListWithListIdDoesNotExist(listID);
        //Assert
        assertEquals(getListErrorCode, 400);
    }

    @Test
    public void shouldGetErrorOnRenameListWhenListIdDoesNotExist(){
        //Arrange
        String listID = "1111111111";
        String listName = "test";
        //Act
        int getListErrorCode = listClient.getErrorCodeWhenTryRenameListWithListIdDoesNotExist(listID, listName);
        //Assert
        assertEquals(getListErrorCode, 400);
    }

    @Test
    public void shouldGetErrorOnArchiveListWhenListIdDoesNotExist(){
        //Arrange
        String listID = "1111111111";
        //Act
        int getListErrorCode = listClient.getErrorCodeWhenTryArchiveListWithListIdDoesNotExist(listID);
        //Assert
        assertEquals(getListErrorCode, 400);
    }

    @Test
    public void shouldGetErrorOnUnarchiveListWhenListIdDoesNotExist(){
        //Arrange
        String listID = "1111111111";
        //Act
        int getListErrorCode = listClient.getErrorCodeWhenTryUnarchiveListWithListIdDoesNotExist(listID);
        //Assert
        assertEquals(getListErrorCode, 400);
    }


    @ParameterizedTest
    @ValueSource(strings = {" ", "1", "a", "!", "dsfsdfsdfsdf dfgfdg fdgdf dfgdss s s  sfdg d fdf gdf"})
    public void shouldCreateNewEmptyListWithDifferentNames(String listNameToCreate){
        //Arrange

        //Act
        CreateListResponse responseBody = listClient.createList(listNameToCreate, boardId);
        //Assert
        assertEquals(listNameToCreate, responseBody.getName());
        //TearDown
        listClient.archiveList(responseBody.getId());
    }

    @Test
    public void shouldMoveListToOtherBoard(){
        //Arrange
        String secondaryBoardName = "secBoard";
        CreateBoardResponse secondaryBoardResponseBody = boardClient.createBoard(secondaryBoardName);
        String secondaryBoardId = secondaryBoardResponseBody.getId();
        String listName = "testList";
        CreateListResponse createListResponseBody = listClient.createList(listName, boardId);
        String listId = createListResponseBody.getId();
        //Act
        GetListResponse moveListResponseBody = listClient.moveList(listId, secondaryBoardId);
        GetListResponse getListResponse = listClient.getList(listId);
        //Assert
        assertEquals(secondaryBoardId, moveListResponseBody.getIdBoard());
        assertEquals(secondaryBoardId, getListResponse.getIdBoard());
        //Tear down
        boardClient.deleteBoard(secondaryBoardId);
    }

    @Test
    public void shouldReturn404WhenGetDeletedList(){
        //Arrange
        String listName = "testList";
        CreateListResponse createListResponseBody = listClient.createList(listName, boardId);
        String listId = createListResponseBody.getId();
        String secondaryBoardName = "secBoard";
        CreateBoardResponse createBoardResponseBody = boardClient.createBoard(secondaryBoardName);
        String secondaryBoardId = createBoardResponseBody.getId();
        //Act
        listClient.moveList(listId,secondaryBoardId);
        boardClient.deleteBoard(secondaryBoardId);
        int getListResponseCode = listClient.getErrorCodeWhenTryGetListWithListIdDoesNotExist(listId);
        //Assert
        assertEquals(404, getListResponseCode);
    }


    @AfterAll
    public static void deleteTestBoard(){
        boardClient.deleteBoard(boardId);
    }
}