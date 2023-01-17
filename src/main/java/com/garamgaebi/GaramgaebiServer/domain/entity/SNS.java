package com.garamgaebi.GaramgaebiServer.domain.entity;


import jakarta.persistence.*;

@Entity
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

    // == 연관관계 메서드 -- //
//    public void setMemberSNS(Member member) {
//        this.member = member;
//        member.getMemberSNS().add(this);
//    }

}
