package com.garamgaebi.GaramgaebiServer.domain.apply.entitiy;

import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.apply.entitiy.vo.ApplyStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;


@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@DynamicInsert
@Table(name = "Apply")
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column
    private String bank;

    @Column
    private String account;

    @Enumerated(EnumType.STRING)
    private ApplyStatus status;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Apply(Member member,
                 Program program,
                 String name,
                 String nickname,
                 String phone,
                 String bank,
                 String account,
                 ApplyStatus status,
                 LocalDateTime createdAt,
                 LocalDateTime updatedAt) {
        this.member = member;
        this.program = program;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.bank = bank;
        this.account = account;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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