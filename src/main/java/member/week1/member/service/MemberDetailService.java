package member.week1.member.service;

import lombok.RequiredArgsConstructor;
import member.week1.member.domain.Member;
import member.week1.member.MemberRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberDetailService {
    private final MemberRepository memberRepository;
    public Member getMemberId(String userId) {
        return memberRepository.findById(Long.valueOf(userId)).orElseThrow(RuntimeException::new);
    }
}
