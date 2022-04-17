package model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MembershipSubResponse {
    private String idMember;
    private String memberType;
    private Boolean unconfirmed;
    private Boolean deactivated;
    private String id;
}
