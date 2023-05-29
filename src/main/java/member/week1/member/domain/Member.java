package member.week1.member.domain;

import lombok.Getter;
import member.week1.invitation.domain.Invitation;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
public class Member {
    @Id
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String password;
    @OneToMany
    private List<Invitation> invitations = new ArrayList<>();

}
