package member.week1.invitation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import member.week1.member.domain.Member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collections;

@Getter @Setter
public class CreateInvitationDto {
    @NotBlank @NotNull
    private String email;
    @NotBlank @NotNull
    private String name;
    @NotBlank @NotNull
    private String phone;
    public Member toMemberEntity(){
        return Member.builder()
                .name(this.name)
                .email(this.email)
                .phone(this.phone)
                .roles(Collections.singletonList("GUEST"))
                .isTemporary(true)
                .build();
    }

    @Builder
    public CreateInvitationDto(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }
}
