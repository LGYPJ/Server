package com.garamgaebi.GaramgaebiServer.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "MemberFcm")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@DynamicInsert
@DynamicUpdate
@Getter @Setter
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
        if(!member.getMemberFcms().contains(this)) {
            member.getMemberFcms().add(this);
        }
        this.member = member;
    }

}
