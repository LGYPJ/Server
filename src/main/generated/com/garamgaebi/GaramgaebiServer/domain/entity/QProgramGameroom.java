package com.garamgaebi.GaramgaebiServer.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProgramGameroom is a Querydsl query type for ProgramGameroom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProgramGameroom extends EntityPathBase<ProgramGameroom> {

    private static final long serialVersionUID = -1371675066L;

    public static final QProgramGameroom programGameroom = new QProgramGameroom("programGameroom");

    public final NumberPath<Long> programGameRoomIdx = createNumber("programGameRoomIdx", Long.class);

    public final NumberPath<Long> programIdx = createNumber("programIdx", Long.class);

    public final StringPath roomId = createString("roomId");

    public QProgramGameroom(String variable) {
        super(ProgramGameroom.class, forVariable(variable));
    }

    public QProgramGameroom(Path<? extends ProgramGameroom> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProgramGameroom(PathMetadata metadata) {
        super(ProgramGameroom.class, metadata);
    }

}

