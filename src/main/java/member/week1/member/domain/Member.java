package member.week1.member.domain;

import lombok.Getter;
import member.week1.common.BaseTimeEntity;
import member.week1.invitation.domain.Invitation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
public class Member extends BaseTimeEntity {
    @Id
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String password;
    @OneToMany
    private List<Invitation> invitations = new ArrayList<>();
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();
}
