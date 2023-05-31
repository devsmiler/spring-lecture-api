package member.week1.member;

import lombok.RequiredArgsConstructor;
import member.week1.member.dto.request.InviteJoinDto;
import member.week1.member.dto.request.SignIn;
import member.week1.member.dto.request.SignUp;
import member.week1.member.dto.response.TokenResponse;
import member.week1.member.service.MemberService;
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
/**
 * author Edgar Kim
 * 아래는 개인적으로 테스트용도로 사용했던 코드입니다.
 * */
//    @PostMapping("/join")
//    public void join(@RequestBody SignUp signUp) {
//        memberService.join(signUp);
//    }
//    @PostMapping("/sign-in")
//    public TokenResponse signIn(@RequestBody SignIn signIn) {
//        return memberService.signIn(signIn);
//    }

}
