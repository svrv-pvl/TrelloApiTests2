package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationSubResponse {
    private String id;
    private String name;
    private String displayName;
    private MembershipSubResponse[] memberships;
}
