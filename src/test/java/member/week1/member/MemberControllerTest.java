package member.week1.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import member.week1.invitation.InvitationRepository;
import member.week1.invitation.InvitationService;
import member.week1.invitation.dto.CreateInvitationDto;
import member.week1.invitation.dto.ResponseInvitation;
import member.week1.member.dto.request.InviteJoinDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private InvitationService invitationService;
    @BeforeEach
    void cleanAndSetup() {
        invitationRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void 초대장_회원가입_성공() throws Exception {
        //given
        CreateInvitationDto jackInvitation = CreateInvitationDto.builder()
                .email("Jackson@baledung.io")
                .name("jack")
                .phone("8282")
                .build();
        ResponseInvitation invitation = invitationService.createInvitation(jackInvitation);

        InviteJoinDto inviteJoinDto = new InviteJoinDto();
        inviteJoinDto.setPassword("1234");

        String json = objectMapper.writeValueAsString(inviteJoinDto);

        //when
        mockMvc.perform(put("/member/join/{invitationCode}",invitation.getInvitationCode())
                .contentType(APPLICATION_JSON)
                .content(json)
        ).andDo(print()).andExpect(status().isOk());

        //then
    }

    @Test
    public void 초대장_회원가입_실패() throws Exception {
        //given
        CreateInvitationDto jackInvitation = CreateInvitationDto.builder()
                .email("Jackson@baledung.io")
                .name("jack")
                .phone("8282")
                .build();
        ResponseInvitation invitation = invitationService.createInvitation(jackInvitation);

        InviteJoinDto inviteJoinDto = new InviteJoinDto();
        inviteJoinDto.setPassword("1234");

        String json = objectMapper.writeValueAsString(inviteJoinDto);
        mockMvc.perform(put("/member/join/"+invitation.getInvitationCode())
                .contentType(APPLICATION_JSON)
                .content(json)
        );
        //when

        mockMvc.perform(put("/member/join/"+invitation.getInvitationCode())
                .contentType(APPLICATION_JSON)
                .content(json)
        ).andDo(print()).andExpect(status().isBadRequest());
        //then
    }

}