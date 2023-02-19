package com.garamgaebi.GaramgaebiServer.domain.program.entity;

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

    private static final long serialVersionUID = 943327235L;

    public static final QProgram program = new QProgram("program");

    public final ListPath<com.garamgaebi.GaramgaebiServer.domain.apply.entitiy.Apply, com.garamgaebi.GaramgaebiServer.domain.apply.entitiy.QApply> applies = this.<com.garamgaebi.GaramgaebiServer.domain.apply.entitiy.Apply, com.garamgaebi.GaramgaebiServer.domain.apply.entitiy.QApply>createList("applies", com.garamgaebi.GaramgaebiServer.domain.apply.entitiy.Apply.class, com.garamgaebi.GaramgaebiServer.domain.apply.entitiy.QApply.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> date = createDateTime("date", java.time.LocalDateTime.class);

    public final NumberPath<Integer> fee = createNumber("fee", Integer.class);

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final StringPath location = createString("location");

    public final ListPath<Presentation, QPresentation> presentations = this.<Presentation, QPresentation>createList("presentations", Presentation.class, QPresentation.class, PathInits.DIRECT2);

    public final EnumPath<com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramType> programType = createEnum("programType", com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramType.class);

    public final EnumPath<com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramStatus> status = createEnum("status", com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramStatus.class);

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

