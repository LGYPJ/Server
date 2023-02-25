package com.garamgaebi.GaramgaebiServer.domain.profile.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberQuit is a Querydsl query type for MemberQuit
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberQuit extends EntityPathBase<MemberQuit> {

    private static final long serialVersionUID = 1711489551L;

    public static final QMemberQuit memberQuit = new QMemberQuit("memberQuit");

    public final EnumPath<com.garamgaebi.GaramgaebiServer.domain.profile.entity.vo.MemberQuitStatus> category = createEnum("category", com.garamgaebi.GaramgaebiServer.domain.profile.entity.vo.MemberQuitStatus.class);

    public final StringPath content = createString("content");

    public final NumberPath<Long> member_quit_id = createNumber("member_quit_id", Long.class);

    public final NumberPath<Long> memberIdx = createNumber("memberIdx", Long.class);

    public QMemberQuit(String variable) {
        super(MemberQuit.class, forVariable(variable));
    }

    public QMemberQuit(Path<? extends MemberQuit> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberQuit(PathMetadata metadata) {
        super(MemberQuit.class, metadata);
    }

}

