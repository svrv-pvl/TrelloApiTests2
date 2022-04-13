package apiTests;

import java.io.*;
import java.util.Properties;

public final class TrelloProductionEndpoints {
    private String baseUrl;
    private String key;
    private String token;

    public TrelloProductionEndpoints(){
        FileInputStream fis;
        Properties property = new Properties();

        try {
            fis = new FileInputStream("src/test/resources/productionConnection.properties");
            property.load(fis);

            baseUrl = property.getProperty("BASE_URL");
            key = property.getProperty("KEY");
            token = property.getProperty("TOKEN");

        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }

    }

    public String createList(String listName, String boardId){
        String url = baseUrl;
        url += "lists?name=" + listName + "&idBoard=" + boardId + "&";
        url += addToken();
        return url;
    }

    public String archiveList(String listId){
        String url = baseUrl;
        url += "lists/" + listId + "/closed?value=true&";
        url += addToken();
        return url;
    }

    public String unarchiveList(String listId){
        String url = baseUrl;
        url += "lists/" + listId + "/closed?value=false&";
        url += addToken();
        return url;
    }

    public String renameList(String listId, String newName){
        String url = baseUrl;
        url += "lists/" + listId + "?name=" + newName + "&";
        url += addToken();
        return url;
    }

    public String getList(String listId){
        String url = baseUrl;
        url += "lists/" + listId + "?";
        url += addToken();
        return url;
    }

    public String getBoardListIsOn(String listId){
        String url = baseUrl;
        url += "lists/" + listId + "/board?";
        url += addToken();
        return url;
    }

    /*public static String getAllListsOfBoard(){
        String url = BASE_URL;
        url += "boards/" + BOARD_ID + "/lists?";
        url += addToken();
        return url;
    }*/

    private String addToken(){
        return  "key=" + key + "&token=" + token;
    }
}
