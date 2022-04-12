package apiTests;

import model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListTest {

    @Test
    public void shouldCreateNewEmptyList(){
        //Arrange

        //Act
        String listNameToCreate = "fromPostman";
        CreateListResponse responseBody = ListClient.createList(listNameToCreate);
        //Assert
        assertEquals(listNameToCreate, responseBody.getName());
        assertEquals(false, responseBody.getClosed());
        assertEquals(TrelloProductionEndpoints.BOARD_ID, responseBody.getIdBoard());
        //TearDown
        ListClient.archiveList(responseBody.getId());
    }

    @Test
    public void shouldGetList(){
        //Arrange
        String listName = "testList";
        CreateListResponse createdPrerequisiteList = ListClient.createList(listName);
        String listID = createdPrerequisiteList.getId();
        //Act
        GetListResponse getListResponseBody = ListClient.getList(listID);
        //Assert
        assertEquals(listID, getListResponseBody.getId());
        assertEquals(listName, getListResponseBody.getName());
        assertEquals(false, getListResponseBody.getClosed());
        assertEquals(TrelloProductionEndpoints.BOARD_ID, getListResponseBody.getIdBoard());
        //TearDown
        ListClient.archiveList(listID);
    }

    @Test
    public void shouldRenameList(){
        //Arrange
        String listName = "testUpdatedList";
        CreateListResponse createdPrerequisiteList = ListClient.createList(listName);
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
        CreateListResponse createdPrerequisiteList = ListClient.createList(listName);
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
        CreateListResponse createdPrerequisiteList = ListClient.createList(listName);
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
    //TODO tests for different names of the list
    //TODO tests of operations with non existing list

    //TODO combine assertions
}