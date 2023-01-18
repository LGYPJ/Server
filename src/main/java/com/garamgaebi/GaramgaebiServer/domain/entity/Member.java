package com.garamgaebi.GaramgaebiServer.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Member")
public class Member {
    @Id
    @Column(name = "member_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberIdx;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "profile_email",nullable = false)
    private String profileEmail;

    @Column(name = "social_email",nullable = false)
    private String socialEmail;

    @Column(name = "uni_email",nullable = false)
    private String uniEmail;

    @Column(nullable = false)
    private String content;

    @Column(name = "profile_url",nullable = false)
    private String profileUrl;

    @Column(name="created_at",nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Column(name="updated_at",nullable = false)
    @Temporal(TemporalType.DATE)
    private Date updatedAt;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;
    @OneToMany(mappedBy = "member")
    private List<Education> educations = new ArrayList<Education>();

    @OneToMany(mappedBy = "member")
    private List<Career> careers = new ArrayList<Career>();

    @OneToMany(mappedBy = "member")
    private List<SNS> SNSs = new ArrayList<SNS>();

    @OneToMany(mappedBy = "member")
    private List<QnA> QnAs = new ArrayList<QnA>();

    // == 연관관계 메서드 -- //
    public void addEducation(Education education) {
        this.educations.add(education);
        // 중복 루프 방지
        if (education.getMember() != this) {
            education.setMember(this);
        }
    }

    public void addCareer(Career career) {
        this.careers.add(career);
        // 중복 루프 방지
        if (career.getMember() != this) {
            career.setMember(this);
        }
    }

    public void addSNS(SNS sns) {
        this.SNSs.add(sns);
        // 중복 루프 방지
        if (sns.getMember() != this) {
            sns.setMember(this);
        }
    }

    public void addQnA(QnA qna) {
        this.QnAs.add(qna);
        // 중복 루프 방지
        if (qna.getMember() != this) {
            qna.setMember(this);
        }
    }
}
