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
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@DynamicInsert
@DynamicUpdate
@Getter @Setter
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

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    @OneToMany(mappedBy = "notification", fetch = FetchType.LAZY)
    private List<MemberNotification> memberNotifications = new ArrayList<MemberNotification>();

    @Builder
    private Notification(Long idx, NotificationType notificationType, String content, Long resourceIdx, ProgramType resourceType, NotificationStatus status) {
        this.idx = idx;
        this.notificationType =notificationType;
        this.content = content;
        this.resourceIdx = resourceIdx;
        this.resourceType = resourceType;
        this.status = status;
    }

    // 연관관계 메서드

    public void addMemberNotifications(MemberNotification memberNotification) {
        this.memberNotifications.add(memberNotification);
        if(memberNotification.getNotification() != this) {
            memberNotification.setNotification(this);
        }
    }
}
