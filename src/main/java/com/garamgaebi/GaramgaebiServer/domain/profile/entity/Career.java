package com.garamgaebi.GaramgaebiServer.domain.profile.entity;

import com.garamgaebi.GaramgaebiServer.domain.profile.entity.vo.IsWorking;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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

    @Enumerated(EnumType.STRING)
    @Column(name = "is_working")
    private IsWorking isWorking;

    @Column(name = "start_date", nullable = false)
    private String startDate;

    @Column(name = "end_date")
    private String endDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

    // == 연관관계 메서드 == //
    public void setMember(Member member){
        this.member = member;
        if (member != null && !member.getCareers().contains(this)) {
            member.getCareers().add(this);
        }
    }
}