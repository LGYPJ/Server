package com.garamgaebi.GaramgaebiServer.domain.profile.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEducation is a Querydsl query type for Education
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEducation extends EntityPathBase<Education> {

    private static final long serialVersionUID = -1162112798L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEducation education = new QEducation("education");

    public final NumberPath<Long> educationIdx = createNumber("educationIdx", Long.class);

    public final StringPath endDate = createString("endDate");

    public final StringPath institution = createString("institution");

    public final EnumPath<com.garamgaebi.GaramgaebiServer.domain.profile.entity.vo.IsLearning> isLearning = createEnum("isLearning", com.garamgaebi.GaramgaebiServer.domain.profile.entity.vo.IsLearning.class);

    public final StringPath major = createString("major");

    public final com.garamgaebi.GaramgaebiServer.domain.member.entity.QMember member;

    public final StringPath startDate = createString("startDate");

    public QEducation(String variable) {
        this(Education.class, forVariable(variable), INITS);
    }

    public QEducation(Path<? extends Education> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEducation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEducation(PathMetadata metadata, PathInits inits) {
        this(Education.class, metadata, inits);
    }

    public QEducation(Class<? extends Education> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.garamgaebi.GaramgaebiServer.domain.member.entity.QMember(forProperty("member")) : null;
    }

}

