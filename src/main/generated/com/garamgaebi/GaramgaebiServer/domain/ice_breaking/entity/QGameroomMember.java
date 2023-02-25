package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGameroomMember is a Querydsl query type for GameroomMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGameroomMember extends EntityPathBase<GameroomMember> {

    private static final long serialVersionUID = -325246629L;

    public static final QGameroomMember gameroomMember = new QGameroomMember("gameroomMember");

    public final NumberPath<Long> gameRoomMemberIdx = createNumber("gameRoomMemberIdx", Long.class);

    public final NumberPath<Long> memberIdx = createNumber("memberIdx", Long.class);

    public final StringPath roomId = createString("roomId");

    public QGameroomMember(String variable) {
        super(GameroomMember.class, forVariable(variable));
    }

    public QGameroomMember(Path<? extends GameroomMember> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGameroomMember(PathMetadata metadata) {
        super(GameroomMember.class, metadata);
    }

}

