package com.garamgaebi.GaramgaebiServer.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberQuit is a Querydsl query type for MemberQuit
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberQuit extends EntityPathBase<MemberQuit> {

    private static final long serialVersionUID = -593244396L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberQuit memberQuit = new QMemberQuit("memberQuit");

    public final StringPath category = createString("category");

    public final StringPath content = createString("content");

    public final QMember member;

    public final NumberPath<Long> member_quit_id = createNumber("member_quit_id", Long.class);

    public QMemberQuit(String variable) {
        this(MemberQuit.class, forVariable(variable), INITS);
    }

    public QMemberQuit(Path<? extends MemberQuit> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberQuit(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberQuit(PathMetadata metadata, PathInits inits) {
        this(MemberQuit.class, metadata, inits);
    }

    public QMemberQuit(Class<? extends MemberQuit> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

