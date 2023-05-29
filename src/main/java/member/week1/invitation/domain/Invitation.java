package member.week1.invitation.domain;

import lombok.Getter;
import member.week1.common.BaseTimeEntity;
import member.week1.member.domain.Member;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity @Getter
public class Invitation extends BaseTimeEntity {
    @Id
    private Long id;
    private String url;
    private Boolean isExpired;
    @ManyToOne
    private Member member;
}
