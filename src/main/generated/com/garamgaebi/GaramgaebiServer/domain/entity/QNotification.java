package com.garamgaebi.GaramgaebiServer.domain.entity;

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

    private static final long serialVersionUID = -2133230282L;

    public static final QNotification notification = new QNotification("notification");

    public final StringPath content = createString("content");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final ListPath<MemberNotification, QMemberNotification> memberNotifications = this.<MemberNotification, QMemberNotification>createList("memberNotifications", MemberNotification.class, QMemberNotification.class, PathInits.DIRECT2);

    public final EnumPath<com.garamgaebi.GaramgaebiServer.domain.entity.status.notification.NotificationType> notificationType = createEnum("notificationType", com.garamgaebi.GaramgaebiServer.domain.entity.status.notification.NotificationType.class);

    public final NumberPath<Long> resourceIdx = createNumber("resourceIdx", Long.class);

    public final EnumPath<com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramType> resourceType = createEnum("resourceType", com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramType.class);

    public final EnumPath<com.garamgaebi.GaramgaebiServer.domain.entity.status.notification.NotificationStatus> status = createEnum("status", com.garamgaebi.GaramgaebiServer.domain.entity.status.notification.NotificationStatus.class);

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

