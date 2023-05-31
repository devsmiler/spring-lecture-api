package member.week1.invitation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseInvitation {
    private String invitationCode;
    @Builder
    public ResponseInvitation(String invitationCode) {
        this.invitationCode = invitationCode;
    }
}
