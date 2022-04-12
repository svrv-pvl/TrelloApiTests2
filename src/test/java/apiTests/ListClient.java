package apiTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.CreateListResponse;
import model.GetListResponse;
import model.UpdateListResponse;
import org.apache.http.HttpStatus;

public class ListClient {
    private static RequestSpecification prepareRequest(){
        RequestSpecification spec = RestAssured.given().header("Content-Type", "text/plain");
        return spec;
    }

    public static CreateListResponse createList(String listName){
        RequestSpecification request = prepareRequest();
        String url = TrelloProductionEndpoints.createList(listName);
        Response response = request.post(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        CreateListResponse responseBody = response.jsonPath().getObject("$", CreateListResponse.class);
        return responseBody;
    }

    public static GetListResponse getList(String listId){
        RequestSpecification request = prepareRequest();
        String url = TrelloProductionEndpoints.getList(listId);
        Response response = request.get(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        GetListResponse responseBody = response.jsonPath().getObject("$", GetListResponse.class);
        return responseBody;
    }

    public static int getListResponseWithErrorCode(String listId){
        RequestSpecification request = prepareRequest();
        String url = TrelloProductionEndpoints.getList(listId);
        Response response = request.get(url);
        return response.statusCode();
    }

    public static UpdateListResponse renameList(String listId, String listNewName){
        RequestSpecification request = prepareRequest();
        String url = TrelloProductionEndpoints.renameList(listId, listNewName);
        Response response = request.put(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        UpdateListResponse responseBody = response.jsonPath().getObject("$", UpdateListResponse.class);
        return responseBody;
    }

    public static UpdateListResponse archiveList(String listId){
        RequestSpecification request = prepareRequest();
        String url = TrelloProductionEndpoints.archiveList(listId);
        Response response = request.put(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        UpdateListResponse responseBody = response.jsonPath().getObject("$", UpdateListResponse.class);
        return responseBody;
    }

    public static UpdateListResponse unarchiveList(String listId){
        RequestSpecification request = prepareRequest();
        String url = TrelloProductionEndpoints.unarchiveList(listId);
        Response response = request.put(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        UpdateListResponse responseBody = response.jsonPath().getObject("$", UpdateListResponse.class);
        return responseBody;
    }
}
