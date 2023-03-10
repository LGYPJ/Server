package com.garamgaebi.GaramgaebiServer.domain.notification.entitiy;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberNotification is a Querydsl query type for MemberNotification
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberNotification extends EntityPathBase<MemberNotification> {

    private static final long serialVersionUID = 907713424L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberNotification memberNotification = new QMemberNotification("memberNotification");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final BooleanPath isRead = createBoolean("isRead");

    public final com.garamgaebi.GaramgaebiServer.domain.member.entity.QMember member;

    public final QNotification notification;

    public QMemberNotification(String variable) {
        this(MemberNotification.class, forVariable(variable), INITS);
    }

    public QMemberNotification(Path<? extends MemberNotification> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberNotification(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberNotification(PathMetadata metadata, PathInits inits) {
        this(MemberNotification.class, metadata, inits);
    }

    public QMemberNotification(Class<? extends MemberNotification> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.garamgaebi.GaramgaebiServer.domain.member.entity.QMember(forProperty("member")) : null;
        this.notification = inits.isInitialized("notification") ? new QNotification(forProperty("notification")) : null;
    }

}

