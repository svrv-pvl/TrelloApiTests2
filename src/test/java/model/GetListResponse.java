package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetListResponse {
    private String id;
    private String name;
    private Boolean closed;
    private String idBoard;
    private int pos;
}