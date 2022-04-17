package apiTests;

import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.CreateBoardResponse;
import model.GetBoardResponse;
import model.UnarchiveBoardResponse;
import org.apache.http.HttpStatus;

public class BoardClient extends RestConnector {
    public BoardClient(String connectionPropertiesFile) {
        trelloProductionEndpoints = new TrelloProductionEndpoints(connectionPropertiesFile);
    }

    public GetBoardResponse getBoard(String boardID) {
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.getBoard(boardID);
        Response response = request.get(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        GetBoardResponse responseBody = response.jsonPath().getObject("$", GetBoardResponse.class);
        return responseBody;
    }

    public CreateBoardResponse createBoard(String boardName) {
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.createBoard(boardName);
        Response response = request.post(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        CreateBoardResponse responseBody = response.jsonPath().getObject("$", CreateBoardResponse.class);
        return responseBody;
    }

    public int deleteBoard(String boardId) {
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.deleteBoard(boardId);
        Response response = request.delete(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        return response.statusCode();
    }

    public GetBoardResponse renameBoard(String boardId, String newBoardName) {
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.renameBoard(boardId, newBoardName);
        Response response = request.put(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        return response.getBody().as(GetBoardResponse.class);
    }

    public GetBoardResponse changeBoardDescription(String boardId, String newBoardDescription) {
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.changeBoardDescription(boardId, newBoardDescription);
        Response response = request.put(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        return response.getBody().as(GetBoardResponse.class);
    }

    public GetBoardResponse archiveBoard(String boardId) {
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.archiveBoard(boardId);
        Response response = request.put(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        return response.getBody().as(GetBoardResponse.class);
    }

    public UnarchiveBoardResponse unarchiveBoard(String boardId) {
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.unarchiveBoard(boardId);
        Response response = request.put(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        return response.getBody().as(UnarchiveBoardResponse.class);
    }

    public int returnErrorOnGettingBoardWhichDoesNotExist(String boardId){
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.getBoard(boardId);
        Response response = request.get(url);
        return response.statusCode();
    }

    public Headers returnHeadersOnGetBoard(String boardId){
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.getBoard(boardId);
        Response response = request.get(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        return response.getHeaders();
    }

    public Response returnResponseOnCreateBoard(String boardName){
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.createBoard(boardName);
        Response response = request.post(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        return response;
    }

    public Headers returnHeadersOnDeleteBoard(String boardId){
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.deleteBoard(boardId);
        Response response = request.delete(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        return response.getHeaders();
    }

    public Headers returnHeadersOnUpdateBoard(String boardId){
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.archiveBoard(boardId);
        Response response = request.put(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        return response.getHeaders();
    }
}