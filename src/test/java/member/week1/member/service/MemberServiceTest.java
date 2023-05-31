package member.week1.member.service;

import member.week1.common.exception.AlreadyExistsMemberException;
import member.week1.common.exception.InvalidRequest;
import member.week1.invitation.InvitationRepository;
import member.week1.invitation.InvitationService;
import member.week1.invitation.dto.CreateInvitationDto;
import member.week1.invitation.dto.ResponseInvitation;
import member.week1.member.MemberRepository;
import member.week1.member.domain.Member;
import member.week1.member.dto.request.InviteJoinDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberServiceTest {
    @Autowired
    private InvitationService invitationService;
    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    @BeforeEach
    void clean(){
        invitationRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("초대장으로 회원가입")
    void 회원가입성공(){
        //given
        CreateInvitationDto createInvitationDto = CreateInvitationDto.builder()
                .email("dean@google.com")
                .phone("1234-1234")
                .name("hi")
                .build();
        ResponseInvitation invitation = invitationService.createInvitation(createInvitationDto);
        String invitationCode = invitation.getInvitationCode();
        InviteJoinDto inviteJoinDto = new InviteJoinDto();
        inviteJoinDto.setPassword("1234");

        // when
        memberService.joinByInvitation(invitationCode, inviteJoinDto);
        Optional<Member> byEmail = memberRepository.findByEmail("dean@google.com");

        // then
        assertTrue(byEmail.isPresent());
        assertEquals(byEmail.get().getIsTemporary(),false);

    }

    @Test
    @DisplayName("같은 초대장으로 회원가입")
    void 회원가입실패(){
        //given
        CreateInvitationDto createInvitationDto = CreateInvitationDto.builder()
                .email("dean@google.com")
                .phone("1234-1234")
                .name("hi")
                .build();
        ResponseInvitation invitation = invitationService.createInvitation(createInvitationDto);
        String invitationCode = invitation.getInvitationCode();
        InviteJoinDto inviteJoinDto = new InviteJoinDto();
        inviteJoinDto.setPassword("1234");
        memberService.joinByInvitation(invitationCode, inviteJoinDto);
        // when
        Assertions.assertThrows(InvalidRequest.class, () -> {
            memberService.joinByInvitation(invitationCode, inviteJoinDto);
        });

        // then

        Optional<Member> byEmail = memberRepository.findByEmail("dean@google.com");
        assertTrue(byEmail.isPresent());
        assertEquals(byEmail.get().getIsTemporary(),false);

    }
}