package com.garamgaebi.GaramgaebiServer.domain.apply;


import com.garamgaebi.GaramgaebiServer.domain.entity.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ApplyDto {
    private Member member;
    private Program program;
    private String name;
    private String nickname;
    private String phone;
    private String bank;
    private String account;
    private ApplyStatus status;

    @Builder
    public ApplyDto(Member member,
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

    public Apply toEntity() {
        return Apply.builder()
                .member(member)
                .program(program)
                .name(name)
                .nickname(nickname)
                .phone(phone)
                .bank(bank)
                .account(account)
                .status(status)
                .build();
    }

}

