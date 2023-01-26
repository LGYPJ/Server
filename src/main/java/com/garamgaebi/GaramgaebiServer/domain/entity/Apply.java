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

    @Builder
    public Apply(Member member,
                 Program program,
                 String name,
                 String nickname,
                 String phone,
                 String bank,
                 String account,
                 ApplyStatus status) {
        this.member = member;
        this.program = program;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.bank = bank;
        this.account = account;
        this.status = status;
    }

    //계좌 암호화하기

    public static Apply of(Member member) {
        Apply apply = new Apply();
        apply.member = member;
        return apply;
    }


    public void attach(Program program) {
        this.program = program;
    }

    public void detachProgram() {
        this.program = null;
    }

}