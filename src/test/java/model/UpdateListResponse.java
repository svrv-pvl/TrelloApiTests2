package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateListResponse {
    private String id;
    private String name;
    private Boolean closed;
    private String idBoard;
    private int pos;
}