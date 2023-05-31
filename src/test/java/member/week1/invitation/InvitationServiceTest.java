package member.week1.invitation;

import member.week1.common.exception.AlreadyExistsMemberException;
import member.week1.invitation.dto.CreateInvitationDto;
import member.week1.invitation.dto.ResponseInvitation;
import member.week1.member.MemberRepository;
import member.week1.member.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class InvitationServiceTest {
    @Autowired
    private InvitationService invitationService;
    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private MemberRepository memberRepository;
    @BeforeEach
    void clean(){
        invitationRepository.deleteAll();
        memberRepository.deleteAll();
    }
    @Test
    @DisplayName("새 초대장 생성 성공")
    void 초대장_생성(){
        // given
        CreateInvitationDto createInvitationDto = CreateInvitationDto.builder()
                .email("dean@google.com")
                .phone("1234-1234")
                .name("hi")
                .build();
        // when
        invitationService.createInvitation(createInvitationDto);

        //then
        assertEquals(1L, invitationRepository.count());
        assertEquals(1L, memberRepository.count());
    }

    @Test
    @DisplayName("중복 초대장 생성시 같은 생성 코드")
    void 중복_초대장_생성(){
        // given
        CreateInvitationDto createInvitationDto = CreateInvitationDto.builder()
                .email("dean@google.com")
                .phone("1234-1234")
                .name("hi")
                .build();

        String invitationCode = invitationService.createInvitation(createInvitationDto).getInvitationCode();

        // when
        ResponseInvitation invitation = invitationService.createInvitation(createInvitationDto);

        //then
        assertEquals(invitationCode, invitation.getInvitationCode());
        assertEquals(1L, memberRepository.count());
        assertEquals(1L, invitationRepository.count());
    }

    @Test
    @DisplayName("이미 가입되어있는 회원은 초대장 발급 불가")
    void 가입한회원_초대장_생성(){
        // given
        Member adminMember = Member.builder()
                .name("dean")
                .email("dean@gmail.com")
                .password("1234")
                .phone("010-1234-1234")
                .isTemporary(false)
                .roles(Collections.singletonList("ADMIN"))
                .build();
        memberRepository.save(adminMember);

        CreateInvitationDto createInvitationDto = CreateInvitationDto.builder()
                .email("dean@gmail.com")
                .phone("010-1234-1234")
                .name("dean")
                .build();



        // when
        Assertions.assertThrows(AlreadyExistsMemberException.class, () -> {
            invitationService.createInvitation(createInvitationDto);
        });

        //then
        assertEquals(1L, memberRepository.count());
        assertEquals(0, invitationRepository.count());
    }
}