package apiTests;

import model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListTest {

    static final String BOARD_ID = "62445ffced32e95e1629e783";
    private ListClient listClient = new ListClient();

    @Test
    public void shouldCreateNewEmptyList(){
        //Arrange

        //Act
        String listNameToCreate = "fromPostman";
        CreateListResponse responseBody = listClient.createList(listNameToCreate, BOARD_ID);
        //Assert
        assertEquals(listNameToCreate, responseBody.getName());
        assertEquals(false, responseBody.getClosed());
        assertEquals(BOARD_ID, responseBody.getIdBoard());
        //TearDown
        listClient.archiveList(responseBody.getId());
    }

    @Test
    public void shouldGetList(){
        //Arrange
        String listName = "testList";
        CreateListResponse createdPrerequisiteList = listClient.createList(listName, BOARD_ID);
        String listID = createdPrerequisiteList.getId();
        //Act
        GetListResponse getListResponseBody = listClient.getList(listID);
        //Assert
        assertEquals(listID, getListResponseBody.getId());
        assertEquals(listName, getListResponseBody.getName());
        assertEquals(false, getListResponseBody.getClosed());
        assertEquals(BOARD_ID, getListResponseBody.getIdBoard());
        //TearDown
        listClient.archiveList(listID);
    }

    @Test
    public void shouldRenameList(){
        //Arrange
        String listName = "testUpdatedList";
        CreateListResponse createdPrerequisiteList = listClient.createList(listName, BOARD_ID);
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
        CreateListResponse createdPrerequisiteList = listClient.createList(listName, BOARD_ID);
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
        CreateListResponse createdPrerequisiteList = listClient.createList(listName, BOARD_ID);
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
        CreateListResponse responseBody = listClient.createList(listNameToCreate, BOARD_ID);
        //Assert
        assertEquals(listNameToCreate, responseBody.getName());
        //TearDown
        listClient.archiveList(responseBody.getId());
    }
}