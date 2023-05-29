package member.week1.invitation;

import lombok.RequiredArgsConstructor;
import member.week1.invitation.domain.Invitation;
import member.week1.invitation.dto.CreateInvitationDto;
import member.week1.member.repository.MemberRepository;
import member.week1.member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service @Transactional
@RequiredArgsConstructor
public class InvitationService {
    private final InvitationRepository invitationRepository;
    private final MemberRepository memberRepository;
    public String createInvitation(
            CreateInvitationDto createInvitationDto
    ) {
        String uuid = String.valueOf(UUID.randomUUID());

        Optional<Member> optionalMember = memberRepository.findByEmail(createInvitationDto.getEmail());

        if (optionalMember.isPresent()){
            if(optionalMember.get().getIsTemporary()){
                Invitation invitation = optionalMember.get().getInvitation();
                return invitation.getUuid();
            } else {
                throw new RuntimeException("이미 존재하는 회원입니다.");
            }
        }

        Member member = memberRepository.save(createInvitationDto.toMemberEntity());
        Invitation invitation = Invitation.builder().uuid(uuid).member(member).build();

        invitationRepository.save(invitation);
        return invitation.getUuid();
    }


}
