package com.garamgaebi.GaramgaebiServer.domain.profile.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCareer is a Querydsl query type for Career
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCareer extends EntityPathBase<Career> {

    private static final long serialVersionUID = -537069436L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCareer career = new QCareer("career");

    public final NumberPath<Long> careerIdx = createNumber("careerIdx", Long.class);

    public final StringPath company = createString("company");

    public final StringPath endDate = createString("endDate");

    public final EnumPath<com.garamgaebi.GaramgaebiServer.domain.profile.entity.vo.IsWorking> isWorking = createEnum("isWorking", com.garamgaebi.GaramgaebiServer.domain.profile.entity.vo.IsWorking.class);

    public final com.garamgaebi.GaramgaebiServer.domain.member.entity.QMember member;

    public final StringPath position = createString("position");

    public final StringPath startDate = createString("startDate");

    public QCareer(String variable) {
        this(Career.class, forVariable(variable), INITS);
    }

    public QCareer(Path<? extends Career> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCareer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCareer(PathMetadata metadata, PathInits inits) {
        this(Career.class, metadata, inits);
    }

    public QCareer(Class<? extends Career> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.garamgaebi.GaramgaebiServer.domain.member.entity.QMember(forProperty("member")) : null;
    }

}

