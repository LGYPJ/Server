package com.garamgaebi.GaramgaebiServer.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberRoles is a Querydsl query type for MemberRoles
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberRoles extends EntityPathBase<MemberRoles> {

    private static final long serialVersionUID = -1209959784L;

    public static final QMemberRoles memberRoles = new QMemberRoles("memberRoles");

    public final NumberPath<Long> memberIdx = createNumber("memberIdx", Long.class);

    public final NumberPath<Long> memberRolesIdx = createNumber("memberRolesIdx", Long.class);

    public final StringPath roles = createString("roles");

    public QMemberRoles(String variable) {
        super(MemberRoles.class, forVariable(variable));
    }

    public QMemberRoles(Path<? extends MemberRoles> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberRoles(PathMetadata metadata) {
        super(MemberRoles.class, metadata);
    }

}

