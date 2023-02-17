package com.garamgaebi.GaramgaebiServer.domain.entity;

import com.garamgaebi.GaramgaebiServer.domain.entity.status.member.MemberStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "Member")
public class Member extends BaseTimeEntity implements UserDetails {
    @Id
    @Column(name = "member_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberIdx;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "profile_email", nullable = false)
    private String profileEmail;

    @Column(name = "social_email", nullable = false)
    private String socialEmail;

    @Column(name = "uni_email", nullable = false)
    private String uniEmail;

    @Column(nullable = false)
    private String content;

    @Column(name = "profile_url", nullable = true)
    private String profileUrl;

    @Column(name = "belong")
    private  String belong;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();


    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Education> educations = new ArrayList<Education>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Career> careers = new ArrayList<Career>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<SNS> SNSs = new ArrayList<SNS>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QnA> QnAs = new ArrayList<QnA>();

    // 알림 관련 엔티티 연결
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MemberNotification> memberNotifications = new ArrayList<MemberNotification>();

    // fcm 토큰 엔티티 연결
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberFcm> memberFcms = new ArrayList<MemberFcm>();

    @Builder
    public Member(String nickname,
                  String profileEmail,
                  String socialEmail,
                  String uniEmail,
                  String content,
                  String profileUrl,
                  String belong,
                  MemberStatus status) {
        this.nickname = nickname;
        this.profileEmail = profileEmail;
        this.socialEmail = socialEmail;
        this.uniEmail = uniEmail;
        this.content = content;
        this.profileUrl = profileUrl;
        this.belong = belong;
        this.status = status;
    }

    public void inactivedMember() {
        this.nickname = "-";
        this.profileEmail = "-";
        this.socialEmail = "-";
        this.uniEmail = "-";
        this.content = "-";
        this.profileUrl = null;
        this.belong = null;
        this.status = MemberStatus.INACTIVE;
        this.careers.removeAll(this.careers);
        this.educations.removeAll(this.educations);
        this.SNSs.removeAll(this.SNSs);
        this.memberFcms.removeAll(this.memberFcms);
        for(QnA qnA : this.QnAs) {
            qnA.setEmail("-");
        }
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    public void addMemberNotifications(MemberNotification memberNotification) {
        if(!this.memberNotifications.contains(memberNotification)) {
            this.memberNotifications.add(memberNotification);
        }
        if(memberNotification.getMember() != this) {
            memberNotification.setMember(this);
        }
    }

    public void addMemberFcms(MemberFcm memberFcm) {
        if(!this.memberFcms.contains(memberFcm)) {
            this.memberFcms.add(memberFcm);
        }
        if(memberFcm.getMember() != this) {
            memberFcm.setMember(this);
        }
    }
}
