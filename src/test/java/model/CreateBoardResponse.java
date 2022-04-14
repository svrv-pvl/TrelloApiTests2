package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBoardResponse extends GetBoardResponse{
    private Object limits;
}
