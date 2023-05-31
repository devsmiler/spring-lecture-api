package member.week1.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import member.week1.common.BaseTimeEntity;
import member.week1.invitation.domain.Invitation;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter @NoArgsConstructor
public class Member extends BaseTimeEntity {
    @GeneratedValue
    @Id @Column(name = "member_id")
    private Long id;
    private String name;
    private String phone;
    @Column(unique = true)
    private String email;
    private String password;
    private Boolean isTemporary;
    @OneToOne(mappedBy = "member")
    private Invitation invitation;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();
    @Builder
    public Member(String name, String email, String password, String phone, List<String> roles, Boolean isTemporary) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.roles = roles;
        this.isTemporary = isTemporary;
    }
    public void setInvitation(Invitation invitation){
        this.invitation = invitation;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void toPermanentMember(){
        this.isTemporary = false;
    }
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> collect = this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return collect;
    }
}
