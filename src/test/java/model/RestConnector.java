package model;

import apiTests.TrelloProductionEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestConnector {
    protected TrelloProductionEndpoints trelloProductionEndpoints;

    protected RequestSpecification prepareRequest(){
        RequestSpecification spec = RestAssured.given().header("Content-Type", "text/plain");
        return spec;
    }

    protected void assertGeneralHeader(Response response){
        response.then().header("X-Trello-Environment", trelloProductionEndpoints.getEnvironmentName());
    }
}
