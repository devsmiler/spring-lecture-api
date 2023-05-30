package member.week1.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import member.week1.member.domain.Member;
import member.week1.member.repository.MemberRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberDetailService {
    private final MemberRepository memberRepository;

    public Member getMemberId(String userId) {
        return memberRepository.findById(Long.valueOf(userId)).orElseThrow(RuntimeException::new);
    }
    public Member getMemberIdFromAuth(Authentication authentication) {
        Member m = (Member) authentication.getPrincipal();
        return memberRepository.findById(m.getId()).orElseThrow(RuntimeException::new);
    }
}
