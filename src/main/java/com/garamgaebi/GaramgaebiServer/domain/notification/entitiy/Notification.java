package com.garamgaebi.GaramgaebiServer.domain.notification.entitiy;

import com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.vo.NotificationStatus;
import com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.vo.NotificationType;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Notification")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Getter
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_idx")
    private Long idx;

    @Column(name = "notification_type")
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    private String content;
    @Column(name = "resource_idx")
    private Long resourceIdx;

    @Column(name = "resource_type")
    @Enumerated(EnumType.STRING)
    private ProgramType resourceType;

    @Setter
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    @OneToMany(mappedBy = "notification", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberNotification> memberNotifications = new ArrayList<MemberNotification>();

    @Builder
    private Notification(NotificationType notificationType, String content, Long resourceIdx, ProgramType resourceType) {
        this.notificationType =notificationType;
        this.content = content;
        this.resourceIdx = resourceIdx;
        this.resourceType = resourceType;
        this.status = NotificationStatus.ACTIVE;
    }

    // 연관관계 메서드

    public void addMemberNotifications(MemberNotification memberNotification) {
        this.memberNotifications.add(memberNotification);
        if(memberNotification.getNotification() != this) {
            memberNotification.setNotification(this);
        }
    }

    // 비즈니스 메서드
    public void deleteNotificaiton() {
        this.setStatus(NotificationStatus.DELETE);
        // 연관된 memberNotification 모두 삭제
        this.memberNotifications.clear();
    }
}
