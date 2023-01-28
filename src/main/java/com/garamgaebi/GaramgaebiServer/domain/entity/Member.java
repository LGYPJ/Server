package com.garamgaebi.GaramgaebiServer.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "Member")
public class Member extends BaseTimeEntity {
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

    @Column(name = "belong")
    private  String belong;

    @Column(name = "profile_url",nullable = false)
    private String profileUrl;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Education> educations = new ArrayList<Education>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Career> careers = new ArrayList<Career>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<SNS> SNSs = new ArrayList<SNS>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<QnA> QnAs = new ArrayList<QnA>();

    @Builder
    public Member(String nickname,
                  String profileEmail,
                  String socialEmail,
                  String uniEmail,
                  String content,
                  String profileUrl,
                  MemberStatus status) {
        this.nickname = nickname;
        this.profileEmail = profileEmail;
        this.socialEmail = socialEmail;
        this.uniEmail = uniEmail;
        this.content = content;
        this.profileUrl = profileUrl;
        this.status = status;
    }

    public void inactivedMember() {
        this.status = MemberStatus.INACTIVE;
    }

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
        if (qna.getMember() != this) {
            qna.setMember(this);
        }
    }
}
