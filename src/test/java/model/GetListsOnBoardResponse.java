package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetListsOnBoardResponse {
    private String id;
    private String name;
    private Boolean closed;
    private String idBoard;
    private Integer pos;
    private Boolean subscribed;
    private String softLimit;
}

