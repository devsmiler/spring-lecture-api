package member.week1.member;

import lombok.RequiredArgsConstructor;

import member.week1.member.dto.request.InviteJoinDto;
import member.week1.member.service.MemberService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    @PutMapping("/join/{invitationCode}")
    public void joinByInvitationCode(
            @PathVariable String invitationCode,
            @RequestBody InviteJoinDto joinByInvitation
    ) {
        memberService.joinByInvitation(invitationCode, joinByInvitation);
    }
}
