package apiTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import model.myHeader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrelloTest {
    protected static ListClient listClient;
    protected static BoardClient boardClient;
    protected static CardClient cardClient;

    protected static Headers expectedGeneralHeaders;
    protected static Headers expectedHeadersFor404;
    protected static Headers expectedHeadersFor200;

    protected static Headers readHeaders(String filePath){
        Headers readHeaders = new Headers();
        FileInputStream fis;
        ObjectMapper mapper = new ObjectMapper();
        try{
            fis = new FileInputStream(filePath);
            List<myHeader> tempMyHeaderList = mapper.readValue(fis, mapper.getTypeFactory().constructCollectionType(List.class, myHeader.class));
            List<Header> headerList = new ArrayList<>();
            for(myHeader header: tempMyHeaderList){
                Header h = new Header(header.getName(), header.getValue());
                headerList.add(h);
            }
            readHeaders = new Headers(headerList);
        } catch (IOException e) {
            System.out.println("[ERROR] Cannot read headers file");
        }
        return readHeaders;
    }

    public static void initializationClientsAndHeaders(){
        String connectionPropertiesFile = System.getProperty("connectionPropertiesFile");
        listClient = new ListClient(connectionPropertiesFile);
        boardClient = new BoardClient(connectionPropertiesFile);
        cardClient = new CardClient(connectionPropertiesFile);

        expectedGeneralHeaders = readHeaders("src/test/java/model/generalHeaders.json");
        expectedHeadersFor404 = readHeaders("src/test/java/model/additionalHeadersFor404.json");
        expectedHeadersFor200 = readHeaders("src/test/java/model/additionalHeadersFor200.json");
    }
}
