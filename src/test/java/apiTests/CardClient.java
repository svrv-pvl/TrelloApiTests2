package apiTests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.CreateCardResponse;
import model.GetCardResponse;
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

    public String createDefaultCardAndReturnId(String listId){
        CreateCardResponse createCardResponseBody = createCard(listId);
        return createCardResponseBody.getId();
    }

    public GetCardResponse renameCard(String cardId, String newCardName) {
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.renameCard(cardId, newCardName);
        Response response = request.put(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        GetCardResponse renameCardResponseBody = response.as(GetCardResponse.class);
        return renameCardResponseBody;
    }

    public GetCardResponse getCard(String cardId) {
        RequestSpecification request = prepareRequest();
        String url = trelloProductionEndpoints.getCard(cardId);
        Response response = request.get(url);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        GetCardResponse getCardResponseBody = response.as(GetCardResponse.class);
        return getCardResponseBody;
    }
}
