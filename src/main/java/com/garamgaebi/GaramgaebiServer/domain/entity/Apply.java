package com.garamgaebi.GaramgaebiServer.domain.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Apply")
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long apply_idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_idx")
    private Program program;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String bank;

    @Column(nullable = false)
    private String account;

    @Enumerated(EnumType.STRING)
    private ApplyStatus status;

    public static Apply of(String account) {
        Apply apply = new Apply();
        apply.account = account;
        return apply;
    } //Program 신청 가능 상황일 때 연동 메서드 생성되면 수정하기. 일단은 계좌만 연동(isAbleToAcceptWaitingApply?)
}