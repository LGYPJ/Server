package com.garamgaebi.GaramgaebiServer.domain.notification.entitiy;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNotification is a Querydsl query type for Notification
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNotification extends EntityPathBase<Notification> {

    private static final long serialVersionUID = -879026154L;

    public static final QNotification notification = new QNotification("notification");

    public final StringPath content = createString("content");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final ListPath<MemberNotification, QMemberNotification> memberNotifications = this.<MemberNotification, QMemberNotification>createList("memberNotifications", MemberNotification.class, QMemberNotification.class, PathInits.DIRECT2);

    public final EnumPath<com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.vo.NotificationType> notificationType = createEnum("notificationType", com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.vo.NotificationType.class);

    public final NumberPath<Long> resourceIdx = createNumber("resourceIdx", Long.class);

    public final EnumPath<com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramType> resourceType = createEnum("resourceType", com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramType.class);

    public final EnumPath<com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.vo.NotificationStatus> status = createEnum("status", com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.vo.NotificationStatus.class);

    public QNotification(String variable) {
        super(Notification.class, forVariable(variable));
    }

    public QNotification(Path<? extends Notification> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNotification(PathMetadata metadata) {
        super(Notification.class, metadata);
    }

}

