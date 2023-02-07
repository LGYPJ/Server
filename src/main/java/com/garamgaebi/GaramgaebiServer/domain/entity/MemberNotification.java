package com.garamgaebi.GaramgaebiServer.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Table(name = "MemberNotification")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Getter @Setter
public class MemberNotification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_notification_idx")
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_idx")
    private Notification notification;

    @Column(name = "is_read")
    private Boolean isRead;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    // 연관관계 메서드
    public void setMember(Member member) {
        this.member = member;

        if(!member.getMemberNotifications().contains(this)) {
            member.addMemberNotifications(this);
        }
    }

    public void setNotification(Notification notification) {
        this.notification = notification;

        if(!notification.getMemberNotifications().contains(this)) {
            notification.addMemberNotifications(this);
        }
    }

    // 비즈니스 로직
    public void read() {
        this.isRead = true;
    }
}
