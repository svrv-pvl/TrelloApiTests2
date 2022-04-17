package apiTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Timeout(2)
public class ListTest {

    private static String boardId;
    private static ListClient listClient;
    private static BoardClient boardClient;
    private static Headers expectedGeneralHeaders;

    @BeforeAll
    public static void connectionAndDataInitializationAndBoardCreation(){
        String connectionPropertiesFile = System.getProperty("connectionPropertiesFile");
        listClient = new ListClient(connectionPropertiesFile);
        boardClient = new BoardClient(connectionPropertiesFile);
        String boardName = "list_test_board";
        GetBoardResponse boardResponseBody = boardClient.createBoard(boardName);
        boardId = boardResponseBody.getId();

        expectedGeneralHeaders = readHeaders("src/test/java/model/generalHeaders.json");
    }

    //TODO Add tests for headers
    //TODO Add tests of incorrect url parameters

    private static Headers readHeaders(String filePath){
        Headers readHeaders = new Headers();
        FileInputStream fis;
        ObjectMapper mapper = new ObjectMapper();
        try{
            fis = new FileInputStream(filePath);
            List<myHeader> tempMyHeaderList = mapper.readValue(fis, mapper.getTypeFactory().constructCollectionType(List.class, myHeader.class));
            List<Header> headerList = new ArrayList<>();
            for(myHeader header: tempMyHeaderList){
                Header h = new Header(header.getName(), header.getValue());
                headerList.add(h);
            }
            readHeaders = new Headers(headerList);
        } catch (IOException e) {
            System.out.println("[ERROR] Cannot read headers file");
        }
        return readHeaders;
    }

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

    @Test
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
    public void shouldBeSameResultWhenGetListFewTimes(){
        //Arrange
        String listName = "testList";
        CreateListResponse createdPrerequisiteList = listClient.createList(listName, boardId);
        String listID = createdPrerequisiteList.getId();
        //Act
        GetListResponse getListResponseBody = listClient.getList(listID);
        GetListResponse secondGetListResponseBody = listClient.getList(listID);
        //Assert
        assertEquals(listID, getListResponseBody.getId());
        assertEquals(listName, getListResponseBody.getName());
        assertEquals(false, getListResponseBody.getClosed());
        assertEquals(boardId, getListResponseBody.getIdBoard());
        assertEquals(listID, secondGetListResponseBody.getId());
        assertEquals(listName, secondGetListResponseBody.getName());
        assertEquals(false, secondGetListResponseBody.getClosed());
        assertEquals(boardId, secondGetListResponseBody.getIdBoard());
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
    @Timeout(5)
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
    @Timeout(5)
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

    @Test
    public void shouldReturnCorrectHeadersOnGetList(){
        //Arrange
        String listName = "testList";
        CreateListResponse createListResponseBody = listClient.createList(listName, boardId);
        String listId = createListResponseBody.getId();
        //Act
        Headers getListHeaders = listClient.getHeadersAfterGetList(listId);
        //Assert
        for(Header header : expectedGeneralHeaders) {
            assertEquals(expectedGeneralHeaders.get(header.getName()), getListHeaders.get(header.getName()));
        }
    }

    @Test
    public void shouldReturnCorrectHeadersOnCreateList(){
        //Arrange

        //Act
        String listName = "testList";
        Headers createListHeaders = listClient.getHeadersAfterCreateList(listName, boardId);
        //Assert
        for(Header header : expectedGeneralHeaders) {
            assertEquals(expectedGeneralHeaders.get(header.getName()), createListHeaders.get(header.getName()));
        }
    }

    @Test
    public void shouldReturnCorrectHeadersOnRenameList(){
        //Arrange
        String listName = "testList";
        CreateListResponse createListResponseBody = listClient.createList(listName, boardId);
        String listId = createListResponseBody.getId();
        //Act
        String newListName = "newName";
        Headers renameListHeaders = listClient.getHeadersAfterRenameList(listId,newListName);
        //Assert
        for(Header header : expectedGeneralHeaders) {
            assertEquals(expectedGeneralHeaders.get(header.getName()), renameListHeaders.get(header.getName()));
        }
    }

    @AfterAll
    public static void deleteTestBoard(){
        boardClient.deleteBoard(boardId);
    }
}