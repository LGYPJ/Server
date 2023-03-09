package com.garamgaebi.GaramgaebiServer.domain.profile.entity;

import com.garamgaebi.GaramgaebiServer.domain.profile.entity.vo.IsWorking;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "Career")
public class Career{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "career_idx")
    private Long careerIdx;

    @Column(name = "company", nullable = false)
    @Setter
    private String company;

    @Column(name = "position", nullable = false)
    @Setter
    private String position;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_working")
    @Setter
    private IsWorking isWorking;

    @Column(name = "start_date", nullable = false)
    @Setter
    private String startDate;

    @Column(name = "end_date")
    @Setter
    private String endDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

//    @Builder
//    public Career(String company, String position, IsWorking isWorking, String startDate, String endDate, Member member) {
//        this.company = company;
//        this.position = position;
//        this.isWorking = isWorking;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.member = member;
//    }


    // == 연관관계 메서드 == //
    public void setMember(Member member){
        this.member = member;
        if (member != null && !member.getCareers().contains(this)) {
            member.getCareers().add(this);
        }
    }
}