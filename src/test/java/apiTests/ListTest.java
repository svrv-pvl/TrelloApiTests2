package apiTests;

import model.*;
import org.junit.Assert;
import org.junit.Test;

public class ListTest {

    @Test
    public void shouldCreateNewEmptyList(){
        //Arrange

        //Act
        String listNameToCreate = "fromPostman";
        ListClient listClient = new ListClient();
        CreateListResponse responseBody = listClient.createList(listNameToCreate);
        //Assert
        Assert.assertEquals(listNameToCreate, responseBody.getName());
        Assert.assertEquals(false, responseBody.getClosed());
        Assert.assertEquals(TrelloProductionEndpoints.BOARD_ID, responseBody.getIdBoard());
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
        Assert.assertEquals(listID, getListResponseBody.getId());
        Assert.assertEquals(listName, getListResponseBody.getName());
        Assert.assertEquals(false, getListResponseBody.getClosed());
        Assert.assertEquals(TrelloProductionEndpoints.BOARD_ID, getListResponseBody.getIdBoard());
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
        Assert.assertEquals(listId, renameResponseBody.getId());
        Assert.assertEquals(newListName, renameResponseBody.getName());
        Assert.assertEquals(newListName, getListResponseBody.getName());
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
        Assert.assertEquals(listId, archivedListBody.getId());
        Assert.assertEquals(listName, archivedListBody.getName());
        Assert.assertEquals(true, archivedListBody.getClosed());
        Assert.assertEquals(true, getListResponseBody.getClosed());
        //TearDown
        listClient.archiveList(listId);
    }

    @Test
    public  void  shouldUnarchiveList(){
        System.out.println("Test that archived list should be unarchived successfully");
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
        Assert.assertEquals(listId, unarchivedListBody.getId());
        Assert.assertEquals(listName, unarchivedListBody.getName());
        Assert.assertEquals(false, unarchivedListBody.getClosed());
        Assert.assertEquals(false, getListResponseBody.getClosed());
        //TearDown
        listClient.archiveList(listId);
    }
    //TODO tests for different names of the list
    //TODO tests of operations with non existing list

    //TODO combine assertions
}