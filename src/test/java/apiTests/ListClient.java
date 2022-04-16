package apiTests;

import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.CreateListResponse;
import model.GetListResponse;
import model.UpdateListResponse;
import org.apache.http.HttpStatus;

public class ListClient extends RestConnector {
    public ListClient(String connectionPropertiesFile){
        trelloProductionEndpoints = new TrelloProductionEndpoints(connectionPropertiesFile);
    }

    public CreateListResponse createList(String listName, String boardID){
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.createList(listName, boardID);
        Response response = request.post(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        assertGeneralHeaders(response);
        CreateListResponse responseBody = response.jsonPath().getObject("$", CreateListResponse.class);
        return responseBody;
    }

    public GetListResponse getList(String listId){
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.getList(listId);
        Response response = request.get(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        assertGeneralHeaders(response);
        GetListResponse responseBody = response.jsonPath().getObject("$", GetListResponse.class);
        return responseBody;
    }

    public int getErrorCodeWhenTryGetListWithListIdDoesNotExist(String listId){
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.getList(listId);
        Response response = request.get(url);
        return response.statusCode();
    }

    public UpdateListResponse renameList(String listId, String listNewName){
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.renameList(listId, listNewName);
        Response response = request.put(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        assertGeneralHeaders(response);
        UpdateListResponse responseBody = response.jsonPath().getObject("$", UpdateListResponse.class);
        return responseBody;
    }

    public int getErrorCodeWhenTryRenameListWithListIdDoesNotExist(String listId, String listNewName){
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.renameList(listId, listNewName);
        Response response = request.put(url);
        return response.statusCode();
    }

    public UpdateListResponse archiveList(String listId){
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.archiveList(listId);
        Response response = request.put(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        assertGeneralHeaders(response);
        UpdateListResponse responseBody = response.jsonPath().getObject("$", UpdateListResponse.class);
        return responseBody;
    }

    public GetListResponse moveList(String listId, String newBoardName){
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.moveList(listId, newBoardName);
        Response response = request.put(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        assertGeneralHeaders(response);
        GetListResponse responseBody = response.jsonPath().getObject("$", GetListResponse.class);
        return responseBody;
    }

    public int getErrorCodeWhenTryArchiveListWithListIdDoesNotExist(String listId){
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.archiveList(listId);
        Response response = request.put(url);
        return response.statusCode();
    }

    public UpdateListResponse unarchiveList(String listId){
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.unarchiveList(listId);
        Response response = request.put(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        assertGeneralHeaders(response);
        UpdateListResponse responseBody = response.jsonPath().getObject("$", UpdateListResponse.class);
        return responseBody;
    }

    public int getErrorCodeWhenTryUnarchiveListWithListIdDoesNotExist(String listId){
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.unarchiveList(listId);
        Response response = request.put(url);
        return response.statusCode();
    }

    public Headers getHeadersAfterGetList(String listId){
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.getList(listId);
        Response response = request.get(url);
        return response.getHeaders();
    }

    public Headers getHeadersAfterCreateList(String listName, String boardId){
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.createList(listName, boardId);
        Response response = request.get(url);
        return response.getHeaders();
    }
}
