package model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BadgesSubResponse {
    private AttachmentsByTypeSubResponse attachmentsByType;
    private Boolean location;
    private int votes;
    private Boolean viewingMemberVoted;
    private Boolean subscribed;
    private String fogbugz;
    private int checkItems;
    private int checkItemsChecked;
    private String checkItemsEarliestDue;
    private int comments;
    private int attachments;
    private Boolean description;
    private String due;
    private Boolean dueComplete;
    private String start;
}
