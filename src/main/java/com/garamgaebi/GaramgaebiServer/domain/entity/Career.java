package com.garamgaebi.GaramgaebiServer.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "Career")
public class Career{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    public void setMember(Member member){
        this.member = member;
        if (!member.getCareers().contains(this)) {
            member.getCareers().add(this);
        }
    }
}