package member.week1.member.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SignIn {
    @NotBlank @NotNull
    String email;
    @NotBlank @NotNull
    String password; // 나중에 인크립트 ㄱ ㄱ

}
