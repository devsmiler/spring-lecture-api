package member.week1.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class InviteJoinDto {
    @NotBlank(message = "비밀번호를 입력하세요")
    private String password; //FIXME It would be better to add encrypting or decrypting logic for password

}
