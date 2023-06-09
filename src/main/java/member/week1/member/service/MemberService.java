package member.week1.member.service;

import lombok.RequiredArgsConstructor;
import member.week1.common.exception.InvalidRequest;
import member.week1.common.exception.InvalidSignInInformation;
import member.week1.configuration.JwtTokenProvider;
import member.week1.invitation.InvitationRepository;
import member.week1.invitation.domain.Invitation;
import member.week1.member.domain.Member;
import member.week1.member.dto.request.InviteJoinDto;
import member.week1.member.dto.request.SignIn;
import member.week1.member.dto.request.SignUp;
import member.week1.member.dto.response.TokenResponse;
import member.week1.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final InvitationRepository invitationRepository;
    public void join(SignUp signUp) {
        memberRepository.save(signUp.toMemberEntity());
    }
    public void joinByInvitation(String invitationCode, InviteJoinDto inviteJoinDto) {
        System.out.println(invitationCode+",  "+inviteJoinDto.getPassword());//FIXME CRITICAL Vulnerability. Do not print or log password.
        Invitation invitation = invitationRepository.findInvitationByUuid(invitationCode).orElseThrow(InvalidRequest::new);
        Member member = invitation.getMember();
        member.setPassword(inviteJoinDto.getPassword());
        member.toPermanentMember();
        invitation.toExpired();
        // FIXME Need to save member, invitation
    }
    public TokenResponse signIn(SignIn signIn) {
        return getTokenResponse(signIn, memberRepository, jwtTokenProvider);
    }
    static TokenResponse getTokenResponse(SignIn signIn, MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider) { //FIXME Is there any reason to made this method as static?
        Member member = memberRepository.findByEmail(signIn.getEmail()).orElseThrow(InvalidSignInInformation::new);
        if (member.getPassword().equals(signIn.getPassword())){
            return TokenResponse.builder().token(jwtTokenProvider.createToken(String.valueOf(member.getId()), member.getRoles())).build();
        }
        throw new InvalidSignInInformation();
    }
}
