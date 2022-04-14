package model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PrefsSubResponse {
    private String permissionLevel;
    private Boolean hideVotes;
    private String voting;
    private String comments;
    private String invitations;
    private Boolean selfJoin;
    private Boolean cardCovers;
    private Boolean isTemplate;
    private String cardAging;
    private Boolean calendarFeedEnabled;
    private String[] hiddenPluginBoardButtons;
    private String background;
    private String backgroundColor;
    private String backgroundImage;
    private ImageSubResponse[] backgroundImageScaled;
    private Boolean backgroundTile;
    private String backgroundBrightness;
    private String backgroundBottomColor;
    private String backgroundTopColor;
    private Boolean canBePublic;
    private Boolean canBeEnterprise;
    private Boolean canBeOrg;
    private Boolean canBePrivate;
    private Boolean canInvite;
}
