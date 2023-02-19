package com.garamgaebi.GaramgaebiServer.domain.profile.entity;

import com.garamgaebi.GaramgaebiServer.domain.profile.entity.vo.IsLearning;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "Education")
public class Education{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String startDate;

    @Column(name = "end_date")
    private String endDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

    //== 연관관계 메서드 == //
    public void setMember(Member member){

        this.member = member;

        if (member != null && !member.getEducations().contains(this)) {
            member.getEducations().add(this);
        }
    }
}
