package apiTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ListTests {



    private Response doRequest(String url, method meth){
        RequestSpecification httpRequest = RestAssured.given().header("Content-Type", "text/plain");
        Response response = null;
        switch (meth){
            case get -> response = httpRequest.get(url);
            case post -> response = httpRequest.post(url);
            case put -> response = httpRequest.put(url);
        }
        return response;
    }

    private CreateListResponse createList(String listName){
        String url = TrelloProductionEndpoints.createList(listName);
        Response response = doRequest(url, method.post);
        CreateListResponse responseBody = response.jsonPath().getObject("$", CreateListResponse.class);
        return responseBody;
    }

    @Before
    public void cleanBoard(){
        String url = TrelloProductionEndpoints.getAllListsOfBoard();
        Response response = doRequest(url, method.get);
        GetListsOnBoardResponse[] allLists = response.jsonPath().getObject("$", GetListsOnBoardResponse[].class);
        for (GetListsOnBoardResponse list : allLists){
            String listId = list.getId();
            url = TrelloProductionEndpoints.archiveList(listId);
            doRequest(url, method.put);
        }
    }

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
        Assert.assertEquals(listId, unarchivedListBody.getId());
        Assert.assertEquals(listName, unarchivedListBody.getName());
        Assert.assertEquals(false, unarchivedListBody.getClosed());
        Assert.assertEquals(false, getListResponseBody.getClosed());
    }
    //TODO tests for different names of the list
    //TODO tests of operations with non existing list
}