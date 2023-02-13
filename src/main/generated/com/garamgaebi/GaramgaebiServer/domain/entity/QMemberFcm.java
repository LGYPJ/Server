package com.garamgaebi.GaramgaebiServer.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberFcm is a Querydsl query type for MemberFcm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberFcm extends EntityPathBase<MemberFcm> {

    private static final long serialVersionUID = -296242709L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberFcm memberFcm = new QMemberFcm("memberFcm");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath fcmToken = createString("fcmToken");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final QMember member;

    public QMemberFcm(String variable) {
        this(MemberFcm.class, forVariable(variable), INITS);
    }

    public QMemberFcm(Path<? extends MemberFcm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberFcm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberFcm(PathMetadata metadata, PathInits inits) {
        this(MemberFcm.class, metadata, inits);
    }

    public QMemberFcm(Class<? extends MemberFcm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

