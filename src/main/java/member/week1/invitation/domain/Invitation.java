package member.week1.invitation.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import member.week1.common.BaseTimeEntity;
import member.week1.member.domain.Member;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor
public class Invitation extends BaseTimeEntity {
    @Id @Column(name = "invitation_id")
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String uuid;
    private Boolean isExpired;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @Builder
    public Invitation(String uuid, Member member) {
        this.uuid = uuid;
        this.isExpired = false;
        this.member = member;
    }

    public void toExpired(){
        this.isExpired = true;
    }
}
