package com.garamgaebi.GaramgaebiServer.domain.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SNS")
public class SNS {

    @Id
    @GeneratedValue
    @Column(name = "sns_idx")
    private Long snsIdx;

    @Column(name = "address", nullable = false)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

    //== 연관관계 메서드 == //
    public void setSNS(Member member){
        if(this.member != null) {
            this.member.getSNSs().remove(this);
        }
        this.member = member;
        member.getSNSs().add(this);
    }

}
