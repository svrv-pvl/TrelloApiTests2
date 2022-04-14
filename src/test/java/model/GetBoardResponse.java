package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetBoardResponse {
    private String id;
    private String name;
    private String desc;
    private String descData;
    private Boolean closed;
    private String idOrganization;
    private String idEnterprise;
    private Boolean pinned;
    private String url;
    private String shortUrl;
    private PrefsSubResponse prefs;
    private LabelNamesSubResponse labelNames;
}
