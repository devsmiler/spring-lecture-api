package member.week1.member.service;

import lombok.RequiredArgsConstructor;
import member.week1.configuration.JwtTokenProvider;
import member.week1.invitation.InvitationRepository;
import member.week1.invitation.domain.Invitation;
import member.week1.member.domain.Member;
import member.week1.member.dto.InviteJoinDto;
import member.week1.member.dto.SignIn;
import member.week1.member.dto.SignUp;
import member.week1.member.dto.TokenResponse;
import member.week1.member.repository.MemberRepository;
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
        Invitation invitation = invitationRepository.findInvitationByUuid(invitationCode).orElseThrow(RuntimeException::new);
        Member member = invitation.getMember();
        member.setPassword(inviteJoinDto.getPassword());
        member.toPermanentMember();
        invitation.toExpired();
    }
    public TokenResponse signIn(SignIn signIn) {
        return getTokenResponse(signIn, memberRepository, jwtTokenProvider);
    }
    static TokenResponse getTokenResponse(SignIn signIn, MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider) {
        Member member = memberRepository.findByEmail(signIn.getEmail()).orElseThrow(RuntimeException::new); // 에러 핸들링 필요
        if (member.getPassword().equals(signIn.getPassword())){
            return TokenResponse.builder().token(jwtTokenProvider.createToken(String.valueOf(member.getId()), member.getRoles())).build();
        }
        return TokenResponse.builder().token("토큰 인증 실패했어율 에러 처리는 나중에하겠수다..").build();
    }

}
