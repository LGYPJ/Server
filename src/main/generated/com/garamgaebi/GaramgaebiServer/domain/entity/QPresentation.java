package com.garamgaebi.GaramgaebiServer.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPresentation is a Querydsl query type for Presentation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPresentation extends EntityPathBase<Presentation> {

    private static final long serialVersionUID = -2031488155L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPresentation presentation = new QPresentation("presentation");

    public final StringPath content = createString("content");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath organization = createString("organization");

    public final StringPath presentationUrl = createString("presentationUrl");

    public final StringPath profileImg = createString("profileImg");

    public final QProgram program;

    public final StringPath title = createString("title");

    public QPresentation(String variable) {
        this(Presentation.class, forVariable(variable), INITS);
    }

    public QPresentation(Path<? extends Presentation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPresentation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPresentation(PathMetadata metadata, PathInits inits) {
        this(Presentation.class, metadata, inits);
    }

    public QPresentation(Class<? extends Presentation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.program = inits.isInitialized("program") ? new QProgram(forProperty("program")) : null;
    }

}

