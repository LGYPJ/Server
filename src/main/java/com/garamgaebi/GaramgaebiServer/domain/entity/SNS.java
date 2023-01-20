package com.garamgaebi.GaramgaebiServer.domain.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "SNS")
public class SNS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sns_idx")
    private Long snsIdx;

    @Column(name = "address", nullable = false)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx",nullable = false)
    private Member member;

    //== 연관관계 메서드 == //
    public void setMember(Member member){
        this.member = member;
        if (!member.getSNSs().contains(this)) {
            member.getSNSs().add(this);
        }
    }

}
