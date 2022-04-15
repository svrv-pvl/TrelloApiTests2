package apiTests;

import java.io.*;
import java.util.Objects;
import java.util.Properties;

public final class TrelloProductionEndpoints {
    private String baseUrl;
    private String key;
    private String token;

    public TrelloProductionEndpoints(String connectionPropertiesFile){
        if (Objects.nonNull(connectionPropertiesFile)) {
            FileInputStream fis;
            Properties property = new Properties();

            try {
                fis = new FileInputStream(connectionPropertiesFile);
                property.load(fis);

                baseUrl = property.getProperty("BASE_URL");
                key = property.getProperty("KEY");
                token = property.getProperty("TOKEN");

            } catch (IOException e) {
                System.out.println("[ERROR] Cannot read property file by the path");

            }
        }else {
            System.out.println("[ERROR] No property file in the argument");
            baseUrl = "https://api.trello.com/1/";
            key = "999d1bdf19e2a37ef3ad781a42b456fb";
            token = "d254d2de41b17424e1d964c40a86448481f5acad0a5b0ff651d590605f80f83a";
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

    public String moveList(String listId, String newBoardId){
        String url = baseUrl;
        url += "lists/" + listId + "/idBoard?value=" + newBoardId + "&";
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

    public String getBoard(String boardId){
        String url = baseUrl;
        url += "boards/" + boardId + "?";
        url += addToken();
        return url;
    }

    public String createBoard(String boardName){
        String url = baseUrl;
        url += "boards?name=" + boardName + "&";
        url += addToken();
        return url;
    }

    public String deleteBoard(String boardId){
        String url = baseUrl;
        url += "boards/" + boardId + "?";
        url += addToken();
        return url;
    }

    private String addToken(){
        return  "key=" + key + "&token=" + token;
    }
}
