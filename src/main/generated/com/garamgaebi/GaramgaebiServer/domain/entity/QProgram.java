package com.garamgaebi.GaramgaebiServer.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProgram is a Querydsl query type for Program
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProgram extends EntityPathBase<Program> {

    private static final long serialVersionUID = 609576793L;

    public static final QProgram program = new QProgram("program");

    public final ListPath<Apply, QApply> applies = this.<Apply, QApply>createList("applies", Apply.class, QApply.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> date = createDateTime("date", java.time.LocalDateTime.class);

    public final NumberPath<Integer> fee = createNumber("fee", Integer.class);

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final StringPath location = createString("location");

    public final ListPath<Presentation, QPresentation> presentations = this.<Presentation, QPresentation>createList("presentations", Presentation.class, QPresentation.class, PathInits.DIRECT2);

    public final EnumPath<com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramType> programType = createEnum("programType", com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramType.class);

    public final EnumPath<com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramStatus> status = createEnum("status", com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramStatus.class);

    public final StringPath title = createString("title");

    public QProgram(String variable) {
        super(Program.class, forVariable(variable));
    }

    public QProgram(Path<? extends Program> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProgram(PathMetadata metadata) {
        super(Program.class, metadata);
    }

}

