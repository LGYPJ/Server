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
    @Column(nullable = false)
    private NetworkingStatus status;


    // == 연관관계 편의 메서드 == //
   // public void setMember(Member member) {
    // this.member = member;
    // member.getApply().add(this);
    // }
    //
    // public void setProgram(Program program){
    // this.program = program;
    // program.getApply().add(this);
    // }

}
