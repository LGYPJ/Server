package com.garamgaebi.GaramgaebiServer.domain.profile.entity;

import com.garamgaebi.GaramgaebiServer.domain.profile.entity.vo.IsLearning;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "Education")
public class Education{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_idx")
    private Long educationIdx;

    @Column(name = "institution", nullable = false)
    @Setter
    private String institution;

    @Column(name = "major", nullable = false)
    @Setter
    private String major;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_learning", nullable = false)
    @Setter
    private IsLearning isLearning;

    @Column(name = "start_date", nullable = false)
    @Setter
    private String startDate;

    @Column(name = "end_date")
    @Setter
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
