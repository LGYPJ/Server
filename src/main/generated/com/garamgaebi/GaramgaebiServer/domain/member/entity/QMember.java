package com.garamgaebi.GaramgaebiServer.domain.member.entity;

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

    private static final long serialVersionUID = 1149313745L;

    public static final QMember member = new QMember("member1");

    public final com.garamgaebi.GaramgaebiServer.global.entity.QBaseTimeEntity _super = new com.garamgaebi.GaramgaebiServer.global.entity.QBaseTimeEntity(this);

    public final StringPath belong = createString("belong");

    public final ListPath<com.garamgaebi.GaramgaebiServer.domain.profile.entity.Career, com.garamgaebi.GaramgaebiServer.domain.profile.entity.QCareer> careers = this.<com.garamgaebi.GaramgaebiServer.domain.profile.entity.Career, com.garamgaebi.GaramgaebiServer.domain.profile.entity.QCareer>createList("careers", com.garamgaebi.GaramgaebiServer.domain.profile.entity.Career.class, com.garamgaebi.GaramgaebiServer.domain.profile.entity.QCareer.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created_at = _super.created_at;

    public final ListPath<com.garamgaebi.GaramgaebiServer.domain.profile.entity.Education, com.garamgaebi.GaramgaebiServer.domain.profile.entity.QEducation> educations = this.<com.garamgaebi.GaramgaebiServer.domain.profile.entity.Education, com.garamgaebi.GaramgaebiServer.domain.profile.entity.QEducation>createList("educations", com.garamgaebi.GaramgaebiServer.domain.profile.entity.Education.class, com.garamgaebi.GaramgaebiServer.domain.profile.entity.QEducation.class, PathInits.DIRECT2);

    public final ListPath<MemberFcm, QMemberFcm> memberFcms = this.<MemberFcm, QMemberFcm>createList("memberFcms", MemberFcm.class, QMemberFcm.class, PathInits.DIRECT2);

    public final NumberPath<Long> memberIdx = createNumber("memberIdx", Long.class);

    public final ListPath<com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.MemberNotification, com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.QMemberNotification> memberNotifications = this.<com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.MemberNotification, com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.QMemberNotification>createList("memberNotifications", com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.MemberNotification.class, com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.QMemberNotification.class, PathInits.DIRECT2);

    public final StringPath nickname = createString("nickname");

    public final StringPath profileEmail = createString("profileEmail");

    public final StringPath profileUrl = createString("profileUrl");

    public final ListPath<com.garamgaebi.GaramgaebiServer.domain.profile.entity.QnA, com.garamgaebi.GaramgaebiServer.domain.profile.entity.QQnA> QnAs = this.<com.garamgaebi.GaramgaebiServer.domain.profile.entity.QnA, com.garamgaebi.GaramgaebiServer.domain.profile.entity.QQnA>createList("QnAs", com.garamgaebi.GaramgaebiServer.domain.profile.entity.QnA.class, com.garamgaebi.GaramgaebiServer.domain.profile.entity.QQnA.class, PathInits.DIRECT2);

    public final ListPath<String, StringPath> roles = this.<String, StringPath>createList("roles", String.class, StringPath.class, PathInits.DIRECT2);

    public final ListPath<com.garamgaebi.GaramgaebiServer.domain.profile.entity.SNS, com.garamgaebi.GaramgaebiServer.domain.profile.entity.QSNS> SNSs = this.<com.garamgaebi.GaramgaebiServer.domain.profile.entity.SNS, com.garamgaebi.GaramgaebiServer.domain.profile.entity.QSNS>createList("SNSs", com.garamgaebi.GaramgaebiServer.domain.profile.entity.SNS.class, com.garamgaebi.GaramgaebiServer.domain.profile.entity.QSNS.class, PathInits.DIRECT2);

    public final StringPath socialEmail = createString("socialEmail");

    public final EnumPath<com.garamgaebi.GaramgaebiServer.domain.member.entity.vo.MemberStatus> status = createEnum("status", com.garamgaebi.GaramgaebiServer.domain.member.entity.vo.MemberStatus.class);

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

