package apiTests;

public final class TrelloProductionEndpoints {
    static final String BASE_URL = "https://api.trello.com/1/";
    static final String KEY = "999d1bdf19e2a37ef3ad781a42b456fb";
    static final String TOKEN = "d254d2de41b17424e1d964c40a86448481f5acad0a5b0ff651d590605f80f83a";

    public static String createList(String listName, String boardId){
        String url = BASE_URL;
        url += "lists?name=" + listName + "&idBoard=" + boardId + "&";
        url += addToken();
        return url;
    }

    public static String archiveList(String listId){
        String url = BASE_URL;
        url += "lists/" + listId + "/closed?value=true&";
        url += addToken();
        return url;
    }

    public static String unarchiveList(String listId){
        String url = BASE_URL;
        url += "lists/" + listId + "/closed?value=false&";
        url += addToken();
        return url;
    }

    public static String renameList(String listId, String newName){
        String url = BASE_URL;
        url += "lists/" + listId + "?name=" + newName + "&";
        url += addToken();
        return url;
    }

    public static String getList(String listId){
        String url = BASE_URL;
        url += "lists/" + listId + "?";
        url += addToken();
        return url;
    }

    /*public static String getAllListsOfBoard(){
        String url = BASE_URL;
        url += "boards/" + BOARD_ID + "/lists?";
        url += addToken();
        return url;
    }*/

    private static String addToken(){
        return  "key=" + KEY + "&token=" + TOKEN;
    }
}
