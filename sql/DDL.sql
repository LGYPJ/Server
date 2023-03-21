create table Admin
(
    admin_idx bigint auto_increment
        primary key,
    id        varchar(255) not null unique,
    password  varchar(255) not null
);

create table IceBreakingImages
(
    ice_breaking_images_idx bigint auto_increment
        primary key,
    url                     text not null
);

create table Member
(
    member_idx    bigint auto_increment
        primary key,
    nickname      varchar(255) not null,
    profile_email varchar(255) not null,
    identifier    varchar(255) not null unique,
    uni_email     varchar(255) not null unique,
    content       varchar(255) null,
    profile_url   text         null,
    belong        varchar(255) null,
    status        varchar(255) not null default 'ACTIVE',
    created_at    datetime     null default current_timestamp,
    updated_at    datetime     null default current_timestamp
);

create table Career
(
    career_idx bigint auto_increment
        primary key,
    company    varchar(255) not null,
    position   varchar(255) not null,
    is_working varchar(255) not null,
    member_idx bigint       not null,
    start_date varchar(255) not null,
    end_date   varchar(255) null,
    constraint fk_Career_member1
        foreign key (member_idx) references Member (member_idx)
            on delete cascade
            on update cascade
);

create index fk_Career_member1_idx
    on Career (member_idx);

create table Education
(
    education_idx bigint auto_increment
        primary key,
    institution   varchar(255) not null,
    major         varchar(255) not null,
    is_learning   varchar(255) not null,
    member_idx    bigint       not null,
    start_date    varchar(255) not null,
    end_date      varchar(255) null,
    constraint fk_Education_member1
        foreign key (member_idx) references Member (member_idx)
            on delete cascade
            on update cascade
);

create index fk_Education_member1_idx
    on Education (member_idx);

create table GameroomMember
(
    game_room_member_idx bigint auto_increment
        primary key,
    room_id              varchar(255) not null,
    member_idx           bigint       not null,
    constraint fk_GameroomMember_member
        foreign key (member_idx) references Member (member_idx)
            on delete cascade
            on update cascade
);

create index fk_GameroomMember_member_idx
    on GameroomMember (member_idx);

create table MemberFcm
(
    member_fcm_idx bigint auto_increment
        primary key,
    member_idx     bigint                             not null,
    fcm_token      text                               not null,
    created_at     datetime default CURRENT_TIMESTAMP not null,
    constraint MemberFcm_ibfk_1
        foreign key (member_idx) references Member (member_idx)
            on delete cascade
            on update cascade
);

create index member_idx
    on MemberFcm (member_idx);

create table MemberQuit
(
    member_quit_idx bigint auto_increment
        primary key,
    member_idx      bigint       not null,
    content         text         null,
    category        varchar(255) not null,
    constraint fk_MemberQuit_member1
        foreign key (member_idx) references Member (member_idx)
            on delete cascade
            on update cascade
);

create index fk_MemberQuit_member1_idx
    on MemberQuit (member_idx);

create table Member_roles
(
    member_roles_idx  bigint auto_increment
        primary key,
    member_member_idx bigint       not null,
    roles             varchar(255) not null,
    constraint fk_Member_roles_member
        foreign key (member_member_idx) references Member (member_idx)
            on delete cascade
            on update cascade
);

create index fk_Member_roles_member_idx
    on Member_roles (member_member_idx);

create table Notification
(
    notification_idx  bigint auto_increment
        primary key,
    notification_type varchar(255)                  not null,
    content           varchar(255)                  not null,
    resource_idx      bigint                        not null,
    resource_type     varchar(255)                  not null,
    status            varchar(255) default 'ACTIVE' not null
);

create table MemberNotification
(
    member_notification_idx bigint auto_increment
        primary key,
    member_idx              bigint                               not null,
    notification_idx        bigint                               not null,
    is_read                 tinyint(1) default 0                 not null,
    created_at              datetime   default CURRENT_TIMESTAMP not null,
    constraint MemberNotification_ibfk_1
        foreign key (member_idx) references Member (member_idx)
            on delete cascade
            on update cascade,
    constraint MemberNotification_ibfk_2
        foreign key (notification_idx) references Notification (notification_idx)
            on delete cascade
            on update cascade
);

create index member_idx
    on MemberNotification (member_idx);

create index notification_idx
    on MemberNotification (notification_idx);

create table Program
(
    program_idx  bigint auto_increment
        primary key,
    title        varchar(255) not null,
    date         datetime     not null,
    location     varchar(255) not null,
    fee          varchar(255) not null,
    status       varchar(255) not null default 'READY_TO_OPEN',
    program_type varchar(255) not null
);

create table Apply
(
    apply_idx   bigint auto_increment
        primary key,
    member_idx  bigint                             not null,
    program_idx bigint                             not null,
    name        varchar(255)                       not null,
    nickname    varchar(255)                       not null,
    phone       varchar(255)                       not null,
    bank        varchar(255)                       null,
    account     varchar(255)                       null,
    status      varchar(255)                       not null default 'APPLY',
    created_at  datetime default CURRENT_TIMESTAMP not null,
    updated_at  datetime default CURRENT_TIMESTAMP not null,
    constraint fk_Apply_Program1
        foreign key (program_idx) references Program (program_idx)
            on delete cascade
            on update cascade,
    constraint fk_Apply_member
        foreign key (member_idx) references Member (member_idx)
            on delete cascade
            on update cascade
);

create index fk_Apply_Program1_idx
    on Apply (program_idx);

create index fk_Apply_member_idx
    on Apply (member_idx);

create table Presentation
(
    presentation_idx bigint auto_increment
        primary key,
    program_idx      bigint       not null,
    title            varchar(255) not null,
    nickname         varchar(255) not null,
    organization     varchar(255) not null,
    content          varchar(255) not null,
    presentation_url varchar(255) not null,
    profile_img      text         null,
    constraint fk_Presentation_Program1
        foreign key (program_idx) references Program (program_idx)
            on delete cascade
            on update cascade
);

create index fk_Presentation_Program1_idx
    on Presentation (program_idx);

create table ProgramGameroom
(
    program_game_room_idx bigint auto_increment
        primary key,
    program_idx           bigint        not null,
    room_id               varchar(255)  not null,
    current_img_idx       bigint default 0 not null,
    constraint fk_GameRoom_program
        foreign key (program_idx) references Program (program_idx)
            on delete cascade
            on update cascade
);

create index fk_GameRoom_program_idx
    on ProgramGameroom (program_idx);

create table QnA
(
    qna_idx    bigint auto_increment
        primary key,
    member_idx bigint       not null,
    email      varchar(255) not null,
    category   varchar(255) not null,
    content    text         not null,
    constraint fk_QnA_member1
        foreign key (member_idx) references Member (member_idx)
            on delete cascade
            on update cascade
);

create index fk_QnA_member1_idx
    on QnA (member_idx);

create table SNS
(
    sns_idx    bigint auto_increment
        primary key,
    address    varchar(255) not null,
    member_idx bigint       not null,
    type       varchar(225) not null,
    constraint fk_SNS_member1
        foreign key (member_idx) references Member (member_idx)
            on delete cascade
            on update cascade
);

create index fk_SNS_member1_idx
    on SNS (member_idx);