package apiTests;

import model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListTest {

    static final String BOARD_ID = "62445ffced32e95e1629e783";

    @Test
    public void shouldCreateNewEmptyList(){
        //Arrange

        //Act
        String listNameToCreate = "fromPostman";
        CreateListResponse responseBody = ListClient.createList(listNameToCreate, BOARD_ID);
        //Assert
        assertEquals(listNameToCreate, responseBody.getName());
        assertEquals(false, responseBody.getClosed());
        assertEquals(BOARD_ID, responseBody.getIdBoard());
        //TearDown
        ListClient.archiveList(responseBody.getId());
    }

    @Test
    public void shouldGetList(){
        //Arrange
        String listName = "testList";
        CreateListResponse createdPrerequisiteList = ListClient.createList(listName, BOARD_ID);
        String listID = createdPrerequisiteList.getId();
        //Act
        GetListResponse getListResponseBody = ListClient.getList(listID);
        //Assert
        assertEquals(listID, getListResponseBody.getId());
        assertEquals(listName, getListResponseBody.getName());
        assertEquals(false, getListResponseBody.getClosed());
        assertEquals(BOARD_ID, getListResponseBody.getIdBoard());
        //TearDown
        ListClient.archiveList(listID);
    }

    @Test
    public void shouldRenameList(){
        //Arrange
        String listName = "testUpdatedList";
        CreateListResponse createdPrerequisiteList = ListClient.createList(listName, BOARD_ID);
        String listId = createdPrerequisiteList.getId();
        //Act
        String newListName = "newName";
        UpdateListResponse renameResponseBody = ListClient.renameList(listId, newListName);
        GetListResponse getListResponseBody = ListClient.getList(listId);
        //Assert
        assertEquals(listId, renameResponseBody.getId());
        assertEquals(newListName, renameResponseBody.getName());
        assertEquals(newListName, getListResponseBody.getName());
        //TearDown
        ListClient.archiveList(listId);
    }

    @Test
    public  void  shouldArchiveList(){
        //Arrange
        String listName = "testList";
        CreateListResponse createdPrerequisiteList = ListClient.createList(listName, BOARD_ID);
        String listId = createdPrerequisiteList.getId();
        //Act
        UpdateListResponse archivedListBody = ListClient.archiveList(listId);
        GetListResponse getListResponseBody = ListClient.getList(listId);
        //Assert
        assertEquals(listId, archivedListBody.getId());
        assertEquals(listName, archivedListBody.getName());
        assertEquals(true, archivedListBody.getClosed());
        assertEquals(true, getListResponseBody.getClosed());
        //TearDown
        ListClient.archiveList(listId);
    }

    @Test
    public  void  shouldUnarchiveList(){
        //Arrange
        String listName = "testList";
        CreateListResponse createdPrerequisiteList = ListClient.createList(listName, BOARD_ID);
        String listId = createdPrerequisiteList.getId();
        ListClient.archiveList(listId);
        //Act
        UpdateListResponse unarchivedListBody = ListClient.unarchiveList(listId);
        GetListResponse getListResponseBody = ListClient.getList(listId);
        //Assert
        assertEquals(listId, unarchivedListBody.getId());
        assertEquals(listName, unarchivedListBody.getName());
        assertEquals(false, unarchivedListBody.getClosed());
        assertEquals(false, getListResponseBody.getClosed());
        //TearDown
        ListClient.archiveList(listId);
    }

    @Test
    public void shouldGetErrorOnGetListWhenListIdDoesNotExist(){
        //Arrange
        String listID = "1111111111";
        //Act
        int getListErrorCode = ListClient.getErrorCodeWhenTryGetListWithListIdDoesNotExist(listID);
        //Assert
        assertEquals(getListErrorCode, 400);
    }

    @Test
    public void shouldGetErrorOnRenameListWhenListIdDoesNotExist(){
        //Arrange
        String listID = "1111111111";
        String listName = "test";
        //Act
        int getListErrorCode = ListClient.getErrorCodeWhenTryRenameListWithListIdDoesNotExist(listID, listName);
        //Assert
        assertEquals(getListErrorCode, 400);
    }

    @Test
    public void shouldGetErrorOnArchiveListWhenListIdDoesNotExist(){
        //Arrange
        String listID = "1111111111";
        //Act
        int getListErrorCode = ListClient.getErrorCodeWhenTryArchiveListWithListIdDoesNotExist(listID);
        //Assert
        assertEquals(getListErrorCode, 400);
    }

    @Test
    public void shouldGetErrorOnUnarchiveListWhenListIdDoesNotExist(){
        //Arrange
        String listID = "1111111111";
        //Act
        int getListErrorCode = ListClient.getErrorCodeWhenTryUnarchiveListWithListIdDoesNotExist(listID);
        //Assert
        assertEquals(getListErrorCode, 400);
    }


    @ParameterizedTest
    @ValueSource(strings = {" ", "1", "a", "!", "dsfsdfsdfsdf dfgfdg fdgdf dfgdss s s  sfdg d fdf gdf"})
    public void shouldCreateNewEmptyListWithDifferentNames(String listNameToCreate){
        //Arrange

        //Act
        CreateListResponse responseBody = ListClient.createList(listNameToCreate, BOARD_ID);
        //Assert
        assertEquals(listNameToCreate, responseBody.getName());
        //TearDown
        ListClient.archiveList(responseBody.getId());
    }
}