package apiTests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.CreateCardResponse;
import org.apache.http.HttpStatus;

public class CardClient extends RestConnector{
    public CardClient(String connectionPropertiesFile) {
        trelloProductionEndpoints = new TrelloProductionEndpoints(connectionPropertiesFile);
    }

    public CreateCardResponse createCard(String listId){
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.createCard(listId);
        Response response = request.post(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        CreateCardResponse createCardResponseBody = response.as(CreateCardResponse.class);
        return createCardResponseBody;
    }

    public int deleteCard(String cardId){
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.deleteCard(cardId);
        Response response = request.delete(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        return response.statusCode();
    }
}
