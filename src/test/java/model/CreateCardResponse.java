package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCardResponse extends GetCardResponse{
    private String[] attachments;
    private String[] stickers;
    private Object limits;
}
