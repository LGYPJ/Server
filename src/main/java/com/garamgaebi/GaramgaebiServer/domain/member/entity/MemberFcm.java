package com.garamgaebi.GaramgaebiServer.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;


@Table(name = "MemberFcm")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Getter
@Entity
public class MemberFcm {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_fcm_idx")
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

    @Column(name = "fcm_token")
    private String fcmToken;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // == 연관관계 메서드 == //
    public void setMember(Member member) {
        if(member != null && !member.getMemberFcms().contains(this)) {
            member.getMemberFcms().add(this);
        }
        this.member = member;
    }

    @Builder
    public MemberFcm(Member member, String fcmToken) {
        this.member = member;
        this.fcmToken = fcmToken;
    }
}
