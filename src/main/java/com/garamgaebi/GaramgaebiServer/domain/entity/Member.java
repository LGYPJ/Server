package com.garamgaebi.GaramgaebiServer.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_idx;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String profile_email;

    @Column(nullable = false)
    private String social_email;

    @Column(nullable = false)
    private String uni_email;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String profile_url;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date created_at;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date updated_at;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;
}
