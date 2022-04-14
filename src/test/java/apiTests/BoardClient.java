package apiTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.CreateBoardResponse;
import model.GetBoardResponse;
import model.GetListResponse;
import org.apache.http.HttpStatus;

public class BoardClient {
    private TrelloProductionEndpoints trelloProductionEndpoints;

    public BoardClient(String connectionPropertiesFile) {
        trelloProductionEndpoints = new TrelloProductionEndpoints(connectionPropertiesFile);
    }

    private RequestSpecification prepareRequest() {
        RequestSpecification spec = RestAssured.given().header("Content-Type", "text/plain");
        return spec;
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
}