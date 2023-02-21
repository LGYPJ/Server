package com.garamgaebi.GaramgaebiServer.domain.notification.entitiy;

import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.notification.dto.response.GetNotificationDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Table(name = "MemberNotification")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Getter
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

    @Builder
    public MemberNotification(Member member, Notification notification) {
        this.setMember(member);
        this.setNotification(notification);
        this.isRead = false;
        this.createdAt = LocalDateTime.now();
    }

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

    // Dto 변환 메서드
    public GetNotificationDto toGetNotificationDto() {
        return GetNotificationDto.builder()
                .notificationIdx(this.getIdx())
                .content(this.getNotification().getContent())
                .notificationType(this.getNotification().getNotificationType())
                .resourceIdx(this.getNotification().getResourceIdx())
                .resourceType(this.getNotification().getResourceType())
                .isRead(this.getIsRead())
                .build();
    }

    // 비즈니스 로직
    public void read() {
        this.isRead = true;
    }
}
