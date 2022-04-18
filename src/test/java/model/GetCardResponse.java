package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCardResponse {
    private String id;
    private BadgesSubResponse badges;
    private String[] checkItemStates;
    private Boolean closed;
    private Boolean dueComplete;
    private String dateLastActivity;
    private String desc;
    private DescDataSubResponse descData;
    private String due;
    private String dueReminder;
    private String email;
    private String idBoard;
    private String[] idChecklists;
    private String idList;
    private String[] idMembers;
    private String[] idMembersVoted;
    private int idShort;
    private String idAttachmentCover;
    private String[] labels;
    private String[] idLabels;
    private Boolean manualCoverAttachment;
    protected String name;
    private int pos;
    private String shortLink;
    private String shortUrl;
    private String start;
    private Boolean subscribed;
    private String url;
    private CoverSubResponse cover;
    private Boolean isTemplate;
    private String cardRole;
}
