package apiTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.CreateListResponse;
import model.GetListResponse;
import model.GetListsOnBoardResponse;
import model.method;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

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
        String url = TrelloEndpoints.createList(listName);
        Response response = doRequest(url, method.post);
        CreateListResponse responseBody = response.jsonPath().getObject("$", CreateListResponse.class);
        return responseBody;
    }

    @Before
    public void cleanBoard(){
        String url = TrelloEndpoints.getAllListsOfBoard();
        Response response = doRequest(url, method.get);
        GetListsOnBoardResponse[] allLists = response.jsonPath().getObject("$", GetListsOnBoardResponse[].class);
        for (GetListsOnBoardResponse list : allLists){
            String listId = list.getId();
            url = TrelloEndpoints.archiveList(listId);
            doRequest(url, method.put);
        }
    }

    @Test
    @DisplayName("Тестирование запроса создания листа")
    public void testCreateList(){
        String listNameToCreate = "fromPostman";
        int expectedResponseCode = 200;
        Boolean expectedClosed = false;

        String url = TrelloEndpoints.createList(listNameToCreate);
        Response response = doRequest(url, method.post);

        Assert.assertEquals(expectedResponseCode, response.statusCode());

        CreateListResponse responseBody = response.jsonPath().getObject("$", CreateListResponse.class);
        Assert.assertEquals(listNameToCreate, responseBody.getName());
        Assert.assertEquals(expectedClosed, responseBody.getClosed());
        Assert.assertEquals(TrelloEndpoints.BOARD_ID, responseBody.getIdBoard());
    }

    @Test
    public void testGetList(){
        //Create board as a prerequisite
        String listName = "testList";
        CreateListResponse createdPrerequisiteList = createList(listName);
        //Test
        String listID = createdPrerequisiteList.getId();
        int expectedResponseCode = 200;
        Boolean expectedClosed = false;

        String url = TrelloEndpoints.getList(listID);
        Response response = doRequest(url, method.get);

        Assert.assertEquals(expectedResponseCode, response.statusCode());

        GetListResponse getListResponseBody = response.jsonPath().getObject("$", GetListResponse.class);
        Assert.assertEquals(listID, getListResponseBody.getId());
        Assert.assertEquals(listName, getListResponseBody.getName());
        Assert.assertEquals(expectedClosed, getListResponseBody.getClosed());
        Assert.assertEquals(TrelloEndpoints.BOARD_ID, getListResponseBody.getIdBoard());
    }

    @Test
    public void testUpdateListName(){
        //Create prerequisite list
        String listName = "testUpdatedList";
        CreateListResponse createdPrerequisiteList = createList(listName);

        //Test
        String listId = createdPrerequisiteList.getId();
        String newListName = "newName";
        int expectedCode = 200;

        String url = TrelloEndpoints.renameList(listId, newListName);
        Response response = doRequest(url, method.put);
        Assert.assertEquals(expectedCode, response.statusCode());

        GetListResponse responseBody = response.jsonPath().getObject("$", GetListResponse.class);
        Assert.assertEquals(listId, responseBody.getId());
        Assert.assertEquals(newListName, responseBody.getName());

        url = TrelloEndpoints.getList(listId);
        Response getResponse = doRequest(url, method.get);
        GetListResponse getResponseBody = getResponse.jsonPath().getObject("$", GetListResponse.class);
        Assert.assertEquals(newListName, getResponseBody.getName());
    }

    @Test
    public  void  testArchiveList(){
        //Precondition
        String listName = "testList";
        CreateListResponse prerequisiteList = createList(listName);
        //Test
        String listId = prerequisiteList.getId();
        int expectedCode = 200;
        Boolean expectedClosed = true;
        String url = TrelloEndpoints.archiveList(listId);
        Response response = doRequest(url, method.put);
        Assert.assertEquals(expectedCode, response.statusCode());

        GetListResponse responseBody = response.jsonPath().getObject("$", GetListResponse.class);
        Assert.assertEquals(listId, responseBody.getId());
        Assert.assertEquals(listName, responseBody.getName());
        Assert.assertEquals(expectedClosed, responseBody.getClosed());

        url = TrelloEndpoints.getList(listId);
        Response getResponse = doRequest(url, method.get);
        GetListResponse getResponseBody = getResponse.jsonPath().getObject("$", GetListResponse.class);
        Assert.assertEquals(expectedClosed, getResponseBody.getClosed());
    }

    @Test
    public  void  testUnarchiveList(){
        //Precondition
        String listName = "testList";
        CreateListResponse prerequisiteList = createList(listName);
        String listId = prerequisiteList.getId();
        String url = TrelloEndpoints.archiveList(listId);
        Response response = doRequest(url, method.put);
        //Test
        int expectedCode = 200;
        Boolean expectedClosed = false;
        url = TrelloEndpoints.unarchiveList(listId);
        response = doRequest(url, method.put);
        Assert.assertEquals(expectedCode, response.statusCode());

        GetListResponse responseBody = response.jsonPath().getObject("$", GetListResponse.class);
        Assert.assertEquals(listId, responseBody.getId());
        Assert.assertEquals(listName, responseBody.getName());
        Assert.assertEquals(expectedClosed, responseBody.getClosed());

        url = TrelloEndpoints.getList(listId);
        Response getResponse = doRequest(url, method.get);
        GetListResponse getResponseBody = getResponse.jsonPath().getObject("$", GetListResponse.class);
        Assert.assertEquals(expectedClosed, getResponseBody.getClosed());
    }
    //TODO tests for different names of the list
    //TODO tests of operations with non existing list
}