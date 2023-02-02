package com.garamgaebi.GaramgaebiServer.domain.entity;

import com.garamgaebi.GaramgaebiServer.domain.notification.dto.NotificationDto;
import jakarta.persistence.*;
import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Notification")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter @Setter
@Builder(builderMethodName = "NotificationBuilder")
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_idx")
    private Long idx;
    @Column(name = "notification_type")
    private NotificationType notificationType;
    private String content;
    @Column(name = "resource_idx")
    private Long resourceIdx;
    @Column(name = "resource_type")
    private ProgramType resourceType;
    private NotificationStatus status;

    @OneToMany(mappedBy = "notification")
    private List<MemberNotification> memberNotifications = new ArrayList<MemberNotification>();

    public static NotificationBuilder builder(NotificationDto notificationDto) {
        return NotificationBuilder()
                .notificationType(notificationDto.getNotificationType())
                .content(notificationDto.getContent())
                .resourceIdx(notificationDto.getResourceIdx())
                .resourceType(notificationDto.getResourceType());
    }

    // 연관관계 메서드

    public void addMemberNotifications(MemberNotification memberNotification) {
        this.memberNotifications.add(memberNotification);
        if(memberNotification.getNotification() != this) {
            memberNotification.setNotification(this);
        }
    }
}
