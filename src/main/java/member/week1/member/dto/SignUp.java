package member.week1.member.dto;

import lombok.Getter;
import lombok.Setter;
import member.week1.member.domain.Member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collections;

@Getter
@Setter
public class SignUp {
    @NotBlank @NotNull
    String name;
    @NotBlank @NotNull
    String email;
    @NotBlank @NotNull
    String password; // 나중에 인크립트 ㄱ ㄱ
    @NotBlank @NotNull
    String phone;
    public Member toMemberEntity(){
        return Member.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .phone(this.phone)
                .isTemporary(false)
                .roles(Collections.singletonList("GUEST"))
                .build();
    }
}
