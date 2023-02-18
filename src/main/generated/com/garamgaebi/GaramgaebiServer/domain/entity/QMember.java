package com.garamgaebi.GaramgaebiServer.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -78294235L;

    public static final QMember member = new QMember("member1");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final StringPath belong = createString("belong");

    public final ListPath<Career, QCareer> careers = this.<Career, QCareer>createList("careers", Career.class, QCareer.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created_at = _super.created_at;

    public final ListPath<Education, QEducation> educations = this.<Education, QEducation>createList("educations", Education.class, QEducation.class, PathInits.DIRECT2);

    public final ListPath<MemberFcm, QMemberFcm> memberFcms = this.<MemberFcm, QMemberFcm>createList("memberFcms", MemberFcm.class, QMemberFcm.class, PathInits.DIRECT2);

    public final NumberPath<Long> memberIdx = createNumber("memberIdx", Long.class);

    public final ListPath<MemberNotification, QMemberNotification> memberNotifications = this.<MemberNotification, QMemberNotification>createList("memberNotifications", MemberNotification.class, QMemberNotification.class, PathInits.DIRECT2);

    public final StringPath nickname = createString("nickname");

    public final StringPath profileEmail = createString("profileEmail");

    public final StringPath profileUrl = createString("profileUrl");

    public final ListPath<QnA, QQnA> QnAs = this.<QnA, QQnA>createList("QnAs", QnA.class, QQnA.class, PathInits.DIRECT2);

    public final ListPath<String, StringPath> roles = this.<String, StringPath>createList("roles", String.class, StringPath.class, PathInits.DIRECT2);

    public final ListPath<SNS, QSNS> SNSs = this.<SNS, QSNS>createList("SNSs", SNS.class, QSNS.class, PathInits.DIRECT2);

    public final StringPath socialEmail = createString("socialEmail");

    public final EnumPath<com.garamgaebi.GaramgaebiServer.domain.entity.status.member.MemberStatus> status = createEnum("status", com.garamgaebi.GaramgaebiServer.domain.entity.status.member.MemberStatus.class);

    public final StringPath uniEmail = createString("uniEmail");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated_at = _super.updated_at;

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

