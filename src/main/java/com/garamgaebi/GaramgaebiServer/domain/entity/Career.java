package com.garamgaebi.GaramgaebiServer.domain.entity;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "Career")
public class Career{
    @Id
    @GeneratedValue
    @Column(name = "career_idx")
    private Long careerIdx;

    @Column(name = "company", nullable = false)
    private String company;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_working")
    private IsWorking isWorking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

    // == 연관관계 메서드 == //
//    public void setMember(Member member){
//        this.member = member;
//        member.getCareers().add(this);
//    }
}