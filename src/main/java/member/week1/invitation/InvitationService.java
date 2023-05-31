package member.week1.invitation;

import lombok.RequiredArgsConstructor;
import member.week1.common.exception.AlreadyExistsMemberException;
import member.week1.invitation.domain.Invitation;
import member.week1.invitation.dto.CreateInvitationDto;
import member.week1.invitation.dto.ResponseInvitation;
import member.week1.member.MemberRepository;
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
    public ResponseInvitation createInvitation(
            CreateInvitationDto createInvitationDto
    ) {
        String uuid = String.valueOf(UUID.randomUUID());
        Optional<Member> optionalMember = memberRepository.findByEmail(createInvitationDto.getEmail());

        if (optionalMember.isPresent()) {
            return getInvitation(optionalMember.get());
        }
        return createNewInvitation(createInvitationDto, uuid);
    }

    private static ResponseInvitation getInvitation(Member member) {
        if(member.getIsTemporary()){
            Invitation invitation = member.getInvitation();
            return ResponseInvitation.builder().invitationCode(invitation.getUuid()).build();
        } else {
            throw new AlreadyExistsMemberException();
        }
    }

    private ResponseInvitation createNewInvitation(CreateInvitationDto createInvitationDto, String uuid) {
        Member member = memberRepository.save(createInvitationDto.toMemberEntity());
        Invitation invitation = Invitation.builder().uuid(uuid).member(member).build();
        invitationRepository.save(invitation);
        return ResponseInvitation.builder().invitationCode(invitation.getUuid()).build();
    }


}
