package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnarchiveBoardResponse extends GetBoardResponse{
    private OrganizationSubResponse organization;
}
