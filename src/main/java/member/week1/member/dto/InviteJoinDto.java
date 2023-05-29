package member.week1.member.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class InviteJoinDto {
    @NotNull @NotBlank
    private String password;
}
