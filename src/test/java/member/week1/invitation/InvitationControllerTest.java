package member.week1.invitation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import member.week1.configuration.JwtTokenProvider;
import member.week1.invitation.dto.CreateInvitationDto;
import member.week1.member.domain.Member;

import member.week1.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;


@SpringBootTest
@AutoConfigureMockMvc
class InvitationControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private String adminToken;
    private String normalToken;
    @BeforeEach
    void cleanAndSetup() {
        invitationRepository.deleteAll();
        memberRepository.deleteAll();

        Member adminMember = Member.builder()
                .name("dean")
                .email("dean@gmail.com")
                .password("1234")
                .phone("010-1234-1234")
                .isTemporary(false)
                .roles(Collections.singletonList("ADMIN"))
                .build();
        memberRepository.save(adminMember);

        Member normalMember = Member.builder()
                .name("dean2")
                .email("dean2@gmail.com")
                .password("1234")
                .phone("010-1234-1234")
                .isTemporary(false)
                .roles(Collections.singletonList("NORMAL"))
                .build();
        memberRepository.save(normalMember);

        Long adminMemberId = adminMember.getId();
        this.adminToken = jwtTokenProvider.createToken(String.valueOf(adminMemberId), adminMember.getRoles());

        Long normalMemberId = normalMember.getId();
        this.normalToken = jwtTokenProvider.createToken(String.valueOf(normalMemberId), normalMember.getRoles());
    }

    @Test
    @DisplayName("운영자 회원만 초대장 발금 가능")
    void 초대장발급() throws Exception {
        CreateInvitationDto jackInvitation = CreateInvitationDto.builder()
                .email("Jackson@baledung.io")
                .name("jack")
                .phone("8282")
                .build();
        String json = objectMapper.writeValueAsString(jackInvitation);

        mockMvc.perform(post("/invitation")
                        .header("Authorization",this.adminToken)
                .contentType(APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk());
    }
    @Test
    @DisplayName("일반 회원은 초대장 발급 불가")
    void 초대장발급실패_일반회원() throws Exception {
        CreateInvitationDto jackInvitation = CreateInvitationDto.builder()
                .email("Jackson@baledung.io")
                .name("jack")
                .phone("8282")
                .build();
        String json = objectMapper.writeValueAsString(jackInvitation);

        mockMvc.perform(post("/invitation")
                .header("Authorization",this.normalToken)
                .contentType(APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("같은 메일로 초대장 발급시 동일한 링크 제공")
    void  초대장발급_중복이메일() throws Exception {
        CreateInvitationDto jackInvitation = CreateInvitationDto.builder()
                .email("Jackson@baledung.io")
                .name("jack")
                .phone("8282")
                .build();
        String json = objectMapper.writeValueAsString(jackInvitation);

        MvcResult result = mockMvc.perform(post("/invitation")
                .header("Authorization", this.adminToken)
                .contentType(APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk()).andReturn();

        String inviteCode = JsonPath.read(result.getResponse().getContentAsString(),"$.invitationCode");

        mockMvc.perform(post("/invitation")
                .header("Authorization", this.adminToken)
                .contentType(APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk()).andExpect(jsonPath("$.invitationCode").value(inviteCode));
    }
    @Test
    @DisplayName("이미 가입한 회원은 가입 불가")
    void 이미가입한회원_가입불가() throws Exception {
        CreateInvitationDto jackInvitation = CreateInvitationDto.builder()
                .name("dean2")
                .email("dean2@gmail.com")
                .phone("010-1234-1234")
                .build();
        String json = objectMapper.writeValueAsString(jackInvitation);

        mockMvc.perform(post("/invitation")
                .header("Authorization",this.adminToken)
                .contentType(APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isBadRequest()).andDo(print());
    }

}