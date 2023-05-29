package member.week1.member;

import lombok.RequiredArgsConstructor;
import member.week1.member.dto.InviteJoinDto;
import member.week1.member.dto.SignIn;
import member.week1.member.dto.SignUp;
import member.week1.member.dto.TokenResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    @PutMapping("/join/{invitationCode}")
    public void joinByInvitationCode(@PathVariable String invitationCode, @RequestBody InviteJoinDto inviteJoinDto) {
        memberService.joinByInvitation(invitationCode, inviteJoinDto);
    }
    @PostMapping("/join")
    public void join(@RequestBody SignUp signUp) {
        memberService.join(signUp);
    }
    @PostMapping("/sign-in")
    public TokenResponse signIn(@RequestBody SignIn signIn) {
        return memberService.signIn(signIn);
    }

}
