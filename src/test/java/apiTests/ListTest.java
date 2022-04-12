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
        ListClient listClient = new ListClient();
        CreateListResponse responseBody = listClient.createList(listNameToCreate);
        //Assert
        assertEquals(listNameToCreate, responseBody.getName());
        assertEquals(false, responseBody.getClosed());
        assertEquals(TrelloProductionEndpoints.BOARD_ID, responseBody.getIdBoard());
        //TearDown
        listClient.archiveList(responseBody.getId());
    }

    @Test
    public void shouldGetList(){
        //Arrange
        String listName = "testList";
        ListClient listClient = new ListClient();
        CreateListResponse createdPrerequisiteList = listClient.createList(listName);
        String listID = createdPrerequisiteList.getId();
        //Act
        GetListResponse getListResponseBody = listClient.getList(listID);
        //Assert
        assertEquals(listID, getListResponseBody.getId());
        assertEquals(listName, getListResponseBody.getName());
        assertEquals(false, getListResponseBody.getClosed());
        assertEquals(TrelloProductionEndpoints.BOARD_ID, getListResponseBody.getIdBoard());
        //TearDown
        listClient.archiveList(listID);
    }

    @Test
    public void shouldRenameList(){
        //Arrange
        String listName = "testUpdatedList";
        ListClient listClient = new ListClient();
        CreateListResponse createdPrerequisiteList = listClient.createList(listName);
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
        ListClient listClient = new ListClient();
        CreateListResponse createdPrerequisiteList = listClient.createList(listName);
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
        ListClient listClient = new ListClient();
        CreateListResponse createdPrerequisiteList = listClient.createList(listName);
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
    //TODO tests for different names of the list
    //TODO tests of operations with non existing list

    //TODO combine assertions
}