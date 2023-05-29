package member.week1.invitation;

import lombok.RequiredArgsConstructor;
import member.week1.invitation.dto.CreateInvitationDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/invitation")
public class InvitationController {
    private final InvitationService invitationService;

    @PostMapping
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String createInvitation(
            @RequestBody CreateInvitationDto createInvitationDto
    ) {
        return invitationService.createInvitation(createInvitationDto);
    }
}
