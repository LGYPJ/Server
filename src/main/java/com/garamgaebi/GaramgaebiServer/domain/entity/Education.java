package com.garamgaebi.GaramgaebiServer.domain.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Education")
public class Education{
    @Id
    @GeneratedValue
    @Column(name = "education_idx")
    private Long educationIdx;

    @Column(name = "institution", nullable = false)
    private String institution;

    @Column(name = "major", nullable = false)
    private String major;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_learning", nullable = false)
    private IsLearning isLearning;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

    // == 연관관계 메서드 == //
//    public void setUser(Member member){
//        this.member = member;
//        member.getEducations().add(this);
//    }
}
